package com.tausif.controller;

import com.tausif.beans.Complaint;
import com.tausif.beans.User;
import com.tausif.service.AdminService;
import com.tausif.service.ComplaintService;
import com.tausif.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/complaintRegister")
    public String complaintRegister(
            @ModelAttribute Complaint complaint,
            @RequestPart(required = true)MultipartFile e1,
            @RequestPart(required = false)MultipartFile e2,
            @RequestPart(required = false)MultipartFile e3) throws IOException {

        boolean result = complaintService.createComplaint(complaint, e1, e2, e3);
        if(result){

        }else{

        }
        return "redirect:/userhome";
    }

}
