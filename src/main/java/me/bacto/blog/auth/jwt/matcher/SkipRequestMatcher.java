package me.bacto.blog.auth.jwt.matcher;


import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SkipRequestMatcher implements RequestMatcher {

    private OrRequestMatcher skipRequestMatcher;

    public SkipRequestMatcher(MatchConditions matchConditions) {
            List<RequestMatcher> requestMatcherList = matchConditions.toRequestMatcherList();
            skipRequestMatcher = new OrRequestMatcher(requestMatcherList);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return !skipRequestMatcher.matches(request);
    }
}