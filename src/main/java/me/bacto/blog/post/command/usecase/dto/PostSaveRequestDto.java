package me.bacto.blog.post.command.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.bacto.blog.account.domain.Account;

@Getter
@Builder
@AllArgsConstructor
public class PostSaveRequestDto {
    private String postId;
    private String title;
    private String content;
    private Account writer;
}
