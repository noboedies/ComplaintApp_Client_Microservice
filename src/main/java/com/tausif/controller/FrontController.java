package com.tausif.controller;

import com.tausif.service.AdminService;
import com.tausif.service.ComplaintService;
import com.tausif.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ComplaintService complaintService;
}
