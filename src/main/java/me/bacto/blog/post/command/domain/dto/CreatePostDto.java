package me.bacto.blog.post.command.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bacto.blog.account.Account;

@Getter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    private String title;
    private String content;
    private Account writer;
}
