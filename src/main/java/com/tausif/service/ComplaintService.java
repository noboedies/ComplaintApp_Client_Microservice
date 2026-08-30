package com.tausif.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ComplaintService {

    @Value("${microWebservice.complaint}")
    private String complaintUrl;

    private RestTemplate restTemplate = new RestTemplate();
}
