package me.bacto.blog.post.query.dao;

import me.bacto.blog.post.query.dto.PostDetailResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@SpringBootTest
class PostDetailDaoTest {

    @Autowired
    PostDetailDao postDetailDao;

    @Test
    public void test1() {
        PostDetailResponse result = postDetailDao.fetchDetail(1L)
                .orElseThrow();
        System.out.println(result);
    }

}