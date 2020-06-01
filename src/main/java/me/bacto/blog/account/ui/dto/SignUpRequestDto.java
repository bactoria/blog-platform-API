package me.bacto.blog.account.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String userId;
    private String password;
    private String username;
}
