package me.bacto.blog.auth.jwt.matcher;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchConditions {

    private List<MatchCondition> conditions;

    public MatchConditions() {
        conditions = new ArrayList<>();
    }

    public void add(String path) {
        conditions.add(MatchCondition.of(path));
    }

    public void add(String path, HttpMethod method) {
        conditions.add(MatchCondition.of(path, method));
    }

    public List<RequestMatcher> toRequestMatcherList() {
        List<RequestMatcher> requestMatcherList = conditions.stream()
                .map(condition -> {
                    if (condition.getMethod().isEmpty()) {
                        return new AntPathRequestMatcher(condition.getPath());
                    } else {
                        return new AntPathRequestMatcher(condition.getPath(), condition.getMethodName());
                    }
                })
                .collect(Collectors.toList());
        return requestMatcherList;
    }
}