package com.wj.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

//@Component
public class HttpClients {

    public static String getStringClient(String url) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        return response.getBody();
    }

    public static Object getObjectClient(String url) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        return response.getBody();
    }

    public static Object getObjectClient1(String url, MultiValueMap<String, String> params) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        return response.getBody();
    }

    public static Object getObjectClientAndHeaders(String url, HttpHeaders requestHeaders) {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }


    public static Object postObjectClientJsonHeaders(String url, String accessToken, Map<String, Object> requestParam) {
        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity(requestParam, requestHeaders);
//        ResponseEntity<String> response1 = template.postForEntity(url, requestEntity, String.class);
//        System.out.println(response1.getBody());
        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println(response);
        return response.getBody();
    }

    public static Object putObjectClientJsonHeaders(String url, String accessToken, Map<String, Object> requestParam) {
        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity(requestParam, requestHeaders);
//        ResponseEntity<String> response1 = template.postForEntity(url, requestEntity, String.class);
//        System.out.println(response1.getBody());
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        System.out.println(response);
        return response.getBody();
    }


}
