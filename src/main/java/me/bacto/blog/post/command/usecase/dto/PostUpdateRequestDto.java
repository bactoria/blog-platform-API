package me.bacto.blog.post.command.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class PostUpdateRequestDto {
    private Long postId;
    private String newTitle;
    private String newContent;
}
