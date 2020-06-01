package me.bacto.blog.auth.ajax;

import lombok.RequiredArgsConstructor;
import me.bacto.blog.account.domain.Account;
import me.bacto.blog.account.domain.AccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
        final String USERNAME = String.valueOf(account.getAccountId());
        final String PASSWORD = account.getPassword();

        return AccountDetails.builder()
                .authorities(AuthorityUtils.createAuthorityList(account.getRole().getRoleValue()))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

}
