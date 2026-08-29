package com.tausif.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Value("${microWebservice.admin}")
    private String adminUrl;
}
