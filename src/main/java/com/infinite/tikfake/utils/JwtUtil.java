package com.infinite.tikfake.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.infinite.tikfake.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {


    //指定一个token过期时间（毫秒）
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;  // 7天
    private static final String JWT_TOKEN_SECRET_KEY = "TikFake";//

    public static String createJwtTokenByUser(User user) {

        String secret = JWT_TOKEN_SECRET_KEY;

        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);    //使用密钥进行哈希
        // 附带username信息的token
        return JWT.create()
                .withClaim("username", user.getName())
                .withExpiresAt(date)  //过期时间
                .sign(algorithm);     //签名算法
    }


    /**
     * 校验token是否正确
     */
    public static boolean verifyTokenOfUser(String token) {
        log.info("verifyTokenOfUser");
        String secret = JWT_TOKEN_SECRET_KEY;//

        //根据密钥生成JWT效验器
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", getUsername(token))//从不加密的消息体中取出username
                .build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }
        catch (Exception exception){
            return false;
        }
    }

    /**
     * 在token中获取到username信息
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 判断是否过期
     */
    public static boolean isExpire(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().getTime() < System.currentTimeMillis();
    }
}