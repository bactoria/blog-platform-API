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

@Component
@RequiredArgsConstructor
public class userRunner implements ApplicationRunner {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AccountSaveRequestDto requestDto = new AccountSaveRequestDto("aaaa", "bbbb", "cccc");
        accountService.createAccount(requestDto);

        Account writer = accountRepository.findById(1L)
                .orElseThrow();
        PostSaveRequestDto requestDto1 = PostSaveRequestDto.builder()
                .title("앱러너 제목")
                .content("앱러너 내용")
                .writer(writer)
                .build();
        postService.addPost(requestDto1);
    }
}
