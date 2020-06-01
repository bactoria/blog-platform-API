package me.bacto.blog.post.command.usecase;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.AccountRepository;
import me.bacto.blog.post.command.domain.Post;
import me.bacto.blog.post.command.domain.PostRepository;
import me.bacto.blog.post.command.domain.dto.CreatePostDto;
import me.bacto.blog.post.command.usecase.dto.PostSaveRequestDto;
import me.bacto.blog.post.command.usecase.dto.PostUpdateRequestDto;
import me.bacto.blog.post.command.usecase.dto.PostUpdateResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    public Long addPost(PostSaveRequestDto requestDto) {
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .writer(requestDto.getWriter())
                .build();

        Post post = Post.of(createPostDto);
        postRepository.save(post);
        return post.getPostId();
    }

    public void removePost(Long postId) {
        final Long ACCOUNT_ID = fetchAccountId();

        Post post = fetchPost(postId);
        post.delete(ACCOUNT_ID);
    }

    private Post fetchPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다. postId: " + postId));
    }

    public PostUpdateResponseDto updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        final Long POST_ID = postUpdateRequestDto.getPostId();
        final String NEW_TITLE = postUpdateRequestDto.getNewTitle();
        final String NEW_CONTENT = postUpdateRequestDto.getNewContent();
        final Long ACCOUNT_ID = fetchAccountId();

        Post post = fetchPost(POST_ID);
        post.update(NEW_TITLE, NEW_CONTENT,ACCOUNT_ID);

        return PostUpdateResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    private Long fetchAccountId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
