package com.tausif.service;


import com.tausif.beans.Admin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminService {

    @Value("${microWebservice.admin}")
    private String adminUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public Admin createAdmin(Admin admin) {
        Admin a = restTemplate.postForObject(adminUrl+"/adminRegister", admin, Admin.class);
        return a;
    }
}
