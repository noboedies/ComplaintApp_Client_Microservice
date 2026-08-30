package com.tausif.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminService {

    @Value("${microWebservice.admin}")
    private String adminUrl;

    private RestTemplate restTemplate = new RestTemplate();
}
