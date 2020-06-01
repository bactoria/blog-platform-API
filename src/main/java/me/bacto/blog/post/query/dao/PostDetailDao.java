package me.bacto.blog.post.query.dao;

import me.bacto.blog.post.query.dto.PostDetailResponse;

import java.util.Optional;

public interface PostDetailDao {
    Optional<PostDetailResponse> fetchDetail(Long postId);
}
