package me.bacto.blog.account.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bacto.blog.account.Account;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSaveRequestDto {
    private String userId;
    private String password;
    private String username;
}
