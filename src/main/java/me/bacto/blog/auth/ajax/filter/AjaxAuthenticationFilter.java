package me.bacto.blog.auth.ajax.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.bacto.blog.auth.ajax.LoginRequestDto;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public AjaxAuthenticationFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper) {
        super(requestMatcher);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isJsonMediaType(request)) {
            throw new AccessDeniedException("올바르지 않은 Content Type입니다. " + "(" + request.getContentType() + ")");
        }

        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginRequestDto.getUserId(), loginRequestDto.getPassword());
        return getAuthenticationManager().authenticate(authentication);
    }

    private boolean isJsonMediaType(HttpServletRequest request) {
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType());
    }
}
