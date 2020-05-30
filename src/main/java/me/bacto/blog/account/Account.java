package me.bacto.blog.account;

import lombok.*;
import me.bacto.blog.account.usecase.dto.AccountSaveRequestDto;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "accountId")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String accountAppId;

    private String password;

    private String username;

    private String roles;

    public static Account join(AccountSaveRequestDto requestDto, PasswordEncoder passwordEncoder) {
        Account a = new Account();
        a.accountAppId = requestDto.getUserId();
        a.password = passwordEncoder.encode(requestDto.getPassword());
        a.username = requestDto.getUsername();
        a.roles = "ADMIN";
        return a;
    }

}
