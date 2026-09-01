package com.tausif.service;

import com.tausif.beans.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Value("${microWebService.user}")
    private String userUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public boolean createUser(User user) {
        Boolean result = restTemplate.postForObject(userUrl+ "/register", user, Boolean.class);
        return result;
    }
}
