package com.fredo.common.jwt;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

// JWT 工具类
public class JwtHelper {

    // token 过期时间
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    // 密钥
    private static String tokenSignKey = "fredo";

    // 根据id和name生成Token
    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                // 分类
                .setSubject("AUTH-USER")
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                // 设置主体信息
                .claim("userId", userId)
                .claim("username", username)
                // 签名
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                // 压缩
                .compressWith(CompressionCodecs.GZIP)
                // 打包
                .compact();
        return token;
    }

    /**
     * 根据生成的Token串获取id
     */
    public static Long getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer) claims.get("userId");
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据生成的Token串获取name
     */
    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "admin");
        System.out.println(token);
//        System.out.println(JwtHelper.getUserId(token));
//        System.out.println(JwtHelper.getUsername(token));
    }
}