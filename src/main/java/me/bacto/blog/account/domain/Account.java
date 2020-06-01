package me.bacto.blog.account.domain;

import lombok.*;
import me.bacto.blog.account.usecase.dto.AccountSaveRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "accountId")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(unique = true)
    private String accountAppId;

    private String password;

    private String username;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    public static Account join(AccountSaveRequestDto requestDto, PasswordEncoder passwordEncoder) {
        String s = "asd";
        Account account = new Account();
        account.accountAppId = requestDto.getUserId();
        account.password = passwordEncoder.encode(requestDto.getPassword());
        account.username = requestDto.getUsername();
        account.role = AccountRole.ADMIN;
        return account;
    }

}
