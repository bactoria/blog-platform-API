package me.bacto.blog.account.usecase;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.Account;
import me.bacto.blog.account.AccountRepository;
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

    public void createAccount(AccountSaveRequestDto accountSaveRequestDto) {
        Account account = Account.join(accountSaveRequestDto, passwordEncoder);
        accountRepository.save(account);
    }

    public Account getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                            .orElseThrow(RuntimeException::new);
        return account;
    }

}
