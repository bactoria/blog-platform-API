package me.bacto.blog.account.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountSaveRequestDto {
    private String userId;
    private String imageUrl;
    private String password;
    private String username;
}
