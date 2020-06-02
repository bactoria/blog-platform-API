package me.bacto.blog.post.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostPageResponse {
    private Long postId;
    private String title;
}
