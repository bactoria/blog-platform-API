package me.bacto.blog.post.infra;

import me.bacto.blog.post.query.dao.PostDetailDao;
import me.bacto.blog.post.query.dto.PostDetailResponse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class PostDetailDaoImpl implements PostDetailDao {

    @PersistenceContext
    private EntityManager em;

    private static final String DTO_PATH = "me.bacto.blog.post.query.dto.";

    @Override
    public Optional<PostDetailResponse> fetchDetail(Long postId) {
        var selectQuery =
                "select NEW "+DTO_PATH+"PostDetailResponse(p.postId, p.title, p.content, a.accountAppId, a.username)" +
                " from Post p inner join Account a on p.writer.accountId=a.accountId " +
                " where p.postId = :postId";
        ;

        TypedQuery<PostDetailResponse> query = em.createQuery(selectQuery, PostDetailResponse.class);
        query.setMaxResults(1);
        query.setParameter("postId", postId);

        return Optional.ofNullable(query.getSingleResult());
    }
}
