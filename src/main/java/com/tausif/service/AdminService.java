package com.tausif.service;


import com.tausif.beans.Admin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Value("${microWebservice.admin}")
    private String adminUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public Admin createAdmin(Admin admin) {
        Admin a = restTemplate.postForObject(adminUrl+"/adminRegister", admin, Admin.class);
        return a;
    }

    public Admin checkLogin(String email, String password) {
        Map<String, Object> cred = new HashMap<>();
        cred.put("email", email);
        cred.put("password", password);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(cred);
        Admin a = restTemplate.postForObject(adminUrl+"/login", requestEntity, Admin.class);
        return a;
    }
}
