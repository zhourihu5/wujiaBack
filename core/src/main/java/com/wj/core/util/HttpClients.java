package com.wj.core.util;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

}
