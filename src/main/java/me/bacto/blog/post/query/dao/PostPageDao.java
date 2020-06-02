package me.bacto.blog.post.query.dao;

import me.bacto.blog.post.query.dto.PostPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostPageDao {
    Page<PostPageResponse> fetchPage(Pageable pageable);
}
