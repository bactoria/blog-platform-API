package me.bacto.blog.account.domain;

import me.bacto.blog.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findTop1ByAccountAppId(String id);

}
