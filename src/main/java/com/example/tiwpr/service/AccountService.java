package com.example.tiwpr.service;

import com.example.tiwpr.dto.AccountDto;
import com.example.tiwpr.entity.Account;
import com.example.tiwpr.exception.BadRequestException;
import com.example.tiwpr.exception.ResourceNotFoundException;
import com.example.tiwpr.exception.WrongVersionException;
import com.example.tiwpr.mapper.AccountMapper;
import com.example.tiwpr.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public Page<Account> getAllUsersPageable(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    public Account createNewUser(AccountDto user) {
        Account newAccount = accountMapper.mapAccountDtoToAccount(user);
        accountRepository.save(newAccount);
        return newAccount;
    }

    public Optional<Account> find(Long userId) {
        return accountRepository.findById(userId);
    }

    public void updateUser(Long userId, AccountDto user, String etag) {
        if(!user.getId().equals(userId)) {
            throw new BadRequestException("Wrong user id");
        }

        accountRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Could not find account " + userId));
        Account updatedAccount = accountMapper.mapAccountDtoToAccount(user);
        updatedAccount.setId(userId);
        updatedAccount.setVersion(Long.valueOf(etag));

        accountRepository.save(updatedAccount);
    }

    public void partialUpdateUser(Long userId, AccountDto user, String etag) {
        Account currentAccount = accountRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Could not find account " + userId));
        if(!currentAccount.getVersion().equals(Long.valueOf(etag))) { //todo hibernate session messing up, fix it later
            throw new WrongVersionException("Wrong version");
        }

        Account updatedAccount = accountMapper.mergeAccountDtoWithAccount(currentAccount, user);
        accountRepository.save(updatedAccount);
    }

    public void deleteUser(Long userId) {
        accountRepository.deleteById(userId);
    }

}
