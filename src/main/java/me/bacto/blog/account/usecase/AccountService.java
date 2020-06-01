package me.bacto.blog.account.usecase;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.domain.Account;
import me.bacto.blog.account.domain.AccountRepository;
import me.bacto.blog.account.usecase.dto.AccountSaveRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(AccountSaveRequestDto accountSaveRequestDto) {
        Account account = Account.create(accountSaveRequestDto, passwordEncoder);
        accountRepository.save(account);
    }

    public Account getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                            .orElseThrow(RuntimeException::new);
        return account;
    }

}
