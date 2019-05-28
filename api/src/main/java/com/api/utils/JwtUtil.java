package com.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt生成和解析token
 * @author thz
 */
public class JwtUtil {
    public static String base64Secret = "WJJWTBASE64";

    public static String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put("userName", userName);
        claims.put("createDate", new Date());
        return createJwt(claims);
    }

    /**
     * 定义泛型方法，方便传入任何类型入参对象
     */
    public static String createJwt(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000L))  //过期时间
                .signWith(SignatureAlgorithm.HS512, base64Secret).compact();  //加密方式
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//        long nowMillis = System.currentTimeMillis();
//        //设置过期时间
//        final long ttlMillis = 30 * 1000L;
//        final long expMillis = nowMillis + ttlMillis;
//
//        final Date now = new Date(nowMillis);
//        final Date exp = new Date(expMillis);
//        System.out.println(now);
//        System.out.println(exp);

        //生成签名密钥
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//        final Map<String, Object> headerMap = new HashMap<String, Object>();
//        headerMap.put("alg", "HS256");
//        headerMap.put("type", "JWT");

//        JwtBuilder builder = Jwts.builder()
//                .setHeaderParams(headerMap)
//                .setExpiration(exp)
//                .setIssuer("THZ")
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, base64Secret);
        //添加Token过期时间
//        if (jwtObj.getExpires() >= 0) {
//            long expMillis = nowMillis + jwtObj.getExpires();
//            System.out.println(expMillis);
//            Date exp = new Date(expMillis);
//            System.out.println(exp);
//            builder.setExpiration(exp).setNotBefore(now);
//        }
        //生成jwt
//        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param jsonWebToken
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
            // 过期时间
//            Date expiration = claims.getExpiration();
//            long expirationTime = expiration.getTime();
//            long currentTimeMillis = System.currentTimeMillis();
//            if (expirationTime - currentTimeMillis <= 300) {
//                try {
//                    token = flushToken(claims);
//                    //把token设置到响应头中去
//                    response.addHeader(HEADER_STRING, token);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//            if (StringUtils.isEmpty(username)) {
//                //return new UsernamePasswordAuthenticationToken(username, null, authorities);
//                throw new UsernameNotFoundException("该账号已过期,请重新登陆");
//            }
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }


}