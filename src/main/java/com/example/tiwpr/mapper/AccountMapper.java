package com.example.tiwpr.mapper;

import com.example.tiwpr.dto.AccountDto;
import com.example.tiwpr.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    AccountDto mapAccountToAccountDto(Account account);

    @Mapping(target = "id", ignore = true)
    Account mapAccountDtoToAccount(AccountDto accountDto);


    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account mergeAccountDtoWithAccount(@MappingTarget Account currentAccount, AccountDto newAccount);
}
