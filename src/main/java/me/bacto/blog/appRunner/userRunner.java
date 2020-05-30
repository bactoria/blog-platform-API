package me.bacto.blog.appRunner;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.Account;
import me.bacto.blog.account.AccountRepository;
import me.bacto.blog.account.usecase.AccountService;
import me.bacto.blog.account.usecase.dto.AccountSaveRequestDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class userRunner implements ApplicationRunner {

    private final AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AccountSaveRequestDto requestDto = new AccountSaveRequestDto("aaaa", "bbbb", "cccc");
        accountService.createAccount(requestDto);
    }
}
