package me.bacto.blog.post.ui;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.domain.Account;
import me.bacto.blog.account.usecase.AccountService;
import me.bacto.blog.post.command.usecase.PostService;
import me.bacto.blog.post.command.usecase.dto.PostSaveRequestDto;
import me.bacto.blog.post.command.usecase.dto.PostUpdateRequestDto;
import me.bacto.blog.post.command.usecase.dto.PostUpdateResponseDto;
import me.bacto.blog.post.query.dto.PostDetailResponse;
import me.bacto.blog.post.query.usecase.PostDetailService;
import me.bacto.blog.post.ui.dto.AddPostRequestDto;
import me.bacto.blog.post.ui.dto.UpdatePostRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;
    private final AccountService accountService;
    private final PostDetailService postDetailService;

    @PostMapping
    public ResponseEntity addPost(@RequestBody AddPostRequestDto requestDto, Principal principal) {
        Account writer = accountService.getAccount(Long.valueOf(principal.getName()));

        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .writer(writer)
                .build();

        Long postId = postService.addPost(postSaveRequestDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/post/" + postId)
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> fetchPost(@PathVariable Long postId) {
        PostDetailResponse body = postDetailService.fetchPost(postId);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity removePost(@PathVariable Long postId) {
        postService.removePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequestDto updatePostRequestDto) {
        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .postId(postId)
                .newContent(updatePostRequestDto.getContent())
                .newTitle(updatePostRequestDto.getTitle())
                .build();

        PostUpdateResponseDto body = postService.updatePost(postUpdateRequestDto);
        return ResponseEntity.ok(body);
    }

}
