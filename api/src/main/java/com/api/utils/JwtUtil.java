package com.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt生成和解析token
 *
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
     * @param claims
     * @return String
     */
    public static String createJwt(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000L))  //过期时间
                .signWith(SignatureAlgorithm.HS512, base64Secret).compact();  //加密方式
    }

    /**
     * 解析jwt
     * @param jwtToken
     * @return Claims
     */
    public static Claims parseJwt(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jwtToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取jwtToken
     *
     * @param
     * @return String
     */
    public static String getJwtToken() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String authHeader = request.getHeader("Authorization");
            String bearerString = "Bearer ";
            String token = authHeader.substring(bearerString.length());
            return token;
        } catch (Exception ex) {
            return null;
        }
    }


}