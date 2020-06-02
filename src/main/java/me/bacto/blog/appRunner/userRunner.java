package me.bacto.blog.appRunner;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.domain.Account;
import me.bacto.blog.account.domain.AccountRepository;
import me.bacto.blog.account.usecase.AccountService;
import me.bacto.blog.account.usecase.dto.AccountSaveRequestDto;
import me.bacto.blog.post.command.domain.PostRepository;
import me.bacto.blog.post.command.usecase.PostService;
import me.bacto.blog.post.command.usecase.dto.PostSaveRequestDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class userRunner implements ApplicationRunner {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AccountSaveRequestDto requestDto = new AccountSaveRequestDto("aaaa", "bbbb", "cccc");
        accountService.signUp(requestDto);

        Account writer = accountRepository.findById(1L)
                .orElseThrow();

        IntStream.rangeClosed(1, 20).forEach(x ->
                postService.addPost(PostSaveRequestDto.builder()
                        .title("앱러너 제목 " + x)
                        .content("앱러너 내용 " + x)
                        .writer(writer)
                        .build()
                )
        );
    }
}
