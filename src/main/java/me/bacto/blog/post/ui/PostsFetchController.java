package me.bacto.blog.post.ui;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.post.query.dto.PostPageResponse;
import me.bacto.blog.post.query.usecase.PostPageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostsFetchController {

    private static final int PAGE_SIZE = 10;

    private final PostPageService postPageService;

    @GetMapping
    public ResponseEntity<Page<PostPageResponse>> fetchPost(@PageableDefault(sort = "postId", direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable) {
        Page<PostPageResponse> body = postPageService.fetchPost(pageable);
        return ResponseEntity.ok().body(body);
    }

}