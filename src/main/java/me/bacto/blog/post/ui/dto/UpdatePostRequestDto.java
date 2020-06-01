package me.bacto.blog.post.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePostRequestDto {

    private String title;
    private String content;

}
