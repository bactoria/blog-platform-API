package me.bacto.blog.post.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddPostRequestDto {

    private String title;
    private String content;

}
