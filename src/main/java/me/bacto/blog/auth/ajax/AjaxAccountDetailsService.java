package me.bacto.blog.auth.ajax;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.Account;
import me.bacto.blog.account.AccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AjaxAccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findTop1ByAccountAppId(s)
                .orElseThrow(() -> new UsernameNotFoundException(s+ " 사용자는 존재하지 않습니다."));
        return accountDetails(account);
    }

    private UserDetails accountDetails(Account account) {
        final String USERNAME = account.getAccountAppId();
        final String PASSWORD = account.getPassword();

        return AccountDetails.builder()
                .authorities(AuthorityUtils.createAuthorityList(account.getRoles()))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

}
