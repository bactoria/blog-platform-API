package me.bacto.blog.auth;

import me.bacto.blog.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserDetailsImpl extends User {

    public UserDetailsImpl(String id, List<GrantedAuthority> authorities) {
        super(id, "", authorities);
    }

    public UserDetailsImpl(Account account, List<GrantedAuthority> authorities) {
        super(String.valueOf(account.getAccountAppId()), account.getPassword(), authorities);
    }
}