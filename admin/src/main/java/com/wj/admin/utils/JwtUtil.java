package com.wj.admin.utils;

import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.util.number.NumberUtil;
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

    public static String generateToken(SysUserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put("userId", userInfo.getId());
        claims.put("userName", userInfo.getUserName());
        claims.put("createDate", new Date());
        return createJwt(claims);
    }

    /**
     * 定义泛型方法，方便传入任何类型入参对象
     * @param claims
     * @return String
     */
    public static String createJwt(Map<String, Object> claims) {
        return "Bearer " + Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 120 * 1000L))  //过期时间
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
            throw new ServiceException("token失效", ErrorCode.FORBIDDEN);
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

    public static Integer getUserIdFromToken(String token) {
        Integer userId;
        try {
            final Claims claims = parseJwt(token);
            userId = NumberUtil.toIntObject(claims.get("userId").toString());
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }


}