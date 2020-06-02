package me.bacto.blog.post.infra;

import me.bacto.blog.post.query.dao.PostPageDao;
import me.bacto.blog.post.query.dto.PostPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PostPageDaoImpl implements PostPageDao {

    @PersistenceContext
    private EntityManager em;

    private static final String DTO_PATH = "me.bacto.blog.post.query.dto.";

    @Override
    public Page<PostPageResponse> fetchPage(Pageable pageable) {
        var selectQuery =
                "select NEW " + DTO_PATH + "PostPageResponse(p.postId, p.title)" +
                        " from Post p " +
                        " order by p.postId Desc"
                ;

        TypedQuery<PostPageResponse> query = em.createQuery(selectQuery, PostPageResponse.class);
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        List<PostPageResponse> result = query.getResultList();
        long totalCount = em.createQuery("select count(p) from Post p", Long.class).getSingleResult();
        return new PageImpl(result, pageable, totalCount);
    }
}
