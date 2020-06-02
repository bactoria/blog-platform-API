package me.bacto.blog.post.query.usecase;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.post.query.dao.PostPageDao;
import me.bacto.blog.post.query.dto.PostPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostPageService {

    private final PostPageDao postPageDao;

    public Page<PostPageResponse> fetchPost(Pageable pageable) {
        Page<PostPageResponse> postPageResponse = postPageDao.fetchPage(pageable);
        return postPageResponse;
    }

}
