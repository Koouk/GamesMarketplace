package com.example.tiwpr.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Getter
@Setter
public class AccountDto {

    private Long id;

    @NotBlank
    private String nick;

    @NotBlank
    @Email(groups = {AccountPatch.class, Default.class}, regexp =  "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    private BigDecimal accountBalance;

}
