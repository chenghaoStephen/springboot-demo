package com.abc.token.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    // 秘钥
    public static final String JWT_KEY = "secret";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            // token为空
            throw new RuntimeException("token为空");
        }
        // 解析jwt
        Algorithm algorithm = Algorithm.HMAC256(LoginInterceptor.JWT_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
            request.setAttribute("username", jwt.getClaim("username").asString());
        } catch (TokenExpiredException e) {
            // token过期
        } catch (JWTDecodeException e) {
            // 解码失败
        }
        return true;
    }
}
