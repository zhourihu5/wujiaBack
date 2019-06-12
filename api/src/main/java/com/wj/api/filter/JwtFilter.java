package com.wj.api.filter;

import com.wj.api.utils.JwtUtil;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
//    Jwtconfig中配置的filter 用于Jwt token的验证工作 配置时可以指定对应的路径
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authHeader = request.getHeader("Authorization");

        //规避探测性质的 OPTIONS请求
        String optionsString = "OPTIONS";
        String bearerString = "Bearer ";
        if (optionsString.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println(authHeader);
            //验证token
            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(bearerString)) {
//                throw new ServletException(new TokenException(-1, "Missing or invalid Authorization header"));
                throw new ServiceException("Missing or invalid Authorization header", ErrorCode.BAD_REQUEST);
            } else {
                String token = authHeader.substring(bearerString.length());
                try {
                    //使用jwt paser来验证签名
                    Claims claims = Jwts.parser().setSigningKey(JwtUtil.base64Secret).parseClaimsJws(token).getBody();
                } catch (ExpiredJwtException e) {
                    throw new ServiceException("token expired", ErrorCode.BAD_REQUEST);
//                    throw new ServletException(new TokenException(-2, "token expired"));
                } catch (SignatureException e) {
                    throw new ServiceException("token invalid", ErrorCode.BAD_REQUEST);
//                    throw new ServletException(new TokenException(-3, "token invalid"));
                } catch (Exception e) {
                    throw new ServiceException("error", ErrorCode.BAD_REQUEST);
//                    throw new ServletException(new TokenException(-4, "error"));
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

}