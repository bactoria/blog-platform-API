package me.bacto.blog.post.command.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class PostUpdateResponseDto {
    private Long postId;
    private String title;
    private String content;
}
