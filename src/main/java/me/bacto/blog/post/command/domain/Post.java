package me.bacto.blog.post.command.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.bacto.blog.account.domain.Account;
import me.bacto.blog.post.command.domain.dto.CreatePostDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "postId")
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @Column(nullable=false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public static Post of(CreatePostDto createPostDto) {
        Post newPost = new Post();
        newPost.title = createPostDto.getTitle();
        newPost.content = createPostDto.getContent();
        newPost.writer = createPostDto.getWriter();
        return newPost;
    }

    public void update(String new_title, String new_content, Long accountId) {
        validateAlreadyDeletedPost();
        validateAuth(accountId);
        this.title = new_title;
        this.content = new_content;
    }

    private void validateAuth(Long accountId) {
        if (this.writer.getAccountId() != accountId) {
            throw new RuntimeException("권한이 없습니다.");
        }
    }

    public void delete(Long accountId) {
        validateAlreadyDeletedPost();
        validateAuth(accountId);
        this.isDeleted = true;
    }

    private void validateAlreadyDeletedPost() {
        if (this.isDeleted) {
           throw new RuntimeException("삭제된 게시물 입니다.");
        }
    }
}
