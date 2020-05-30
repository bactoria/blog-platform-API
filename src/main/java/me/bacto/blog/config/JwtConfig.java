package me.bacto.blog.config;

import com.auth0.jwt.algorithms.Algorithm;

public abstract class JwtConfig {

    public static final String HEADER_NAME = "jwt-header";

    public static final String ISSUER = "dev";

    public static final String TOKEN_KEY = "dev";

    public static final long EXPIRES_LIMIT = 3L;

    public static Algorithm getAlgorithm() {
        try {
            return Algorithm.HMAC256(JwtConfig.TOKEN_KEY);
        } catch (IllegalArgumentException e) {
            return Algorithm.none();
        }
    }
}