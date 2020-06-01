package me.bacto.blog.post.query.usecase;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.post.query.dao.PostDetailDao;
import me.bacto.blog.post.query.dto.PostDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDetailService {

    private final PostDetailDao postDetailDao;

    public PostDetailResponse fetchPost(Long postId) {
        PostDetailResponse postDetailResponse = postDetailDao.fetchDetail(postId)
                .orElseThrow(RuntimeException::new);
        return postDetailResponse;
    }

}
