package me.bacto.blog.post.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String content;
    private String writerId;
    private String writerImageUrl;
    private String writerUsername;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
