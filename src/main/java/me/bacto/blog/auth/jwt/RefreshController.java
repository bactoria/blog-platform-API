package me.bacto.blog.auth.jwt;

import me.bacto.blog.auth.UserDetailsImpl;
import me.bacto.blog.config.JwtConfig;
import me.bacto.blog.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/refresh")
public class RefreshController {

    @GetMapping
    public ResponseEntity<String> refreshToken(Authentication authentication) {
        UserDetails userDetails = new UserDetailsImpl(authentication.getName(), new ArrayList<>(authentication.getAuthorities()));

        String token = JwtUtil.refreshToken(userDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtConfig.HEADER_NAME, token);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
}