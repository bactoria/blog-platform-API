package me.bacto.blog.auth.jwt.matcher;

import org.springframework.http.HttpMethod;

import java.util.Optional;

public class MatchCondition {

    private String path;
    private Optional<HttpMethod> method;

    private MatchCondition(String path) {
        this.path = path;
        this.method = Optional.empty();
    }

    private MatchCondition(String path, HttpMethod method) {
        this.path = path;
        this.method = Optional.of(method);
    }

    public static MatchCondition of(String path) {
        return new MatchCondition(path);
    }

    public static MatchCondition of(String path, HttpMethod method) {
        return new MatchCondition(path, method);
    }

    public String getPath() {
        return path;
    }

    public Optional<HttpMethod> getMethod() {
        return method;
    }

    public String getMethodName() {
        HttpMethod httpMethod = method.orElseThrow(RuntimeException::new);
        return httpMethod.name();
    }
}