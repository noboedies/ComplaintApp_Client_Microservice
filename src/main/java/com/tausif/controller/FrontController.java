package com.tausif.controller;

import com.tausif.beans.User;
import com.tausif.service.AdminService;
import com.tausif.service.ComplaintService;
import com.tausif.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ComplaintService complaintService;


    @RequestMapping(value = {"/", "/home", "/index"})
    public String home(){
        return "index";
    }

    @PostMapping("/userRegister")
    public String userRegister(@ModelAttribute User user, Model m){
        boolean result = userService.createUser(user);
        if(result){
            m.addAttribute("msg", "User Registered Successfully! ✌️");
        }else{
            m.addAttribute("msg", "User Already Exist! 😒");
        }
        return "signup";
    }

    @GetMapping("/useerHome")
    public String userhome(){
        return "userHome";
    }

}
