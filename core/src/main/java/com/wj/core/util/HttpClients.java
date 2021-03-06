package com.wj.core.util;

import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

//@Component
public class HttpClients {


    public static Object getObjectClient(String url) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        return response.getBody();
    }


    public static String getObjectClientAndHeaders(String url, HttpHeaders requestHeaders) {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity(null, requestHeaders);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }
    public static byte[] getBytes(String url, HttpHeaders requestHeaders) {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity(null, requestHeaders);
        ResponseEntity<byte[]> response = template.exchange(url, HttpMethod.GET, requestEntity, byte[].class);
        return response.getBody();
    }


    public static String postObjectClientJsonHeaders(String url, String accessToken, Map<String, Object> requestParam) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity(requestParam, requestHeaders);
        System.out.println(requestEntity);
        ResponseEntity<String> response = null;
        try {
            response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            return e.getMessage();
        }
        return response.getBody();
    }

    public static String putObjectClientJsonHeaders(String url, String accessToken, Map<String, Object> requestParam) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity(requestParam, requestHeaders);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        System.out.println(response);
        return response.getBody();
    }

    //??????post
    public static String postObjectClient(String url, HttpHeaders requestHeaders, Map<String, Object> requestParam) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpEntity<String> requestEntity = new HttpEntity(requestParam, requestHeaders);
        System.out.println(requestEntity);
        ResponseEntity<String> response = null;
        try {
            response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            return e.getMessage();
        }
        return response.getBody();
    }


}
