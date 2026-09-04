package com.tausif.controller;


import com.tausif.beans.Admin;
import com.tausif.beans.Complaint;
import com.tausif.service.AdminService;
import com.tausif.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;


    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @PostMapping("/adminRegister")
    public String adminRegister(@ModelAttribute Admin admin, Model m){
        Admin a = adminService.createAdmin(admin);
        if(a == null){
            m.addAttribute("msg", "User Already Registered!");
        }else{
            m.addAttribute("msg", "User Registered Successfully!");
        }
        return "login";
    }

    @GetMapping("/allComplaints")
    public String allComplaints(Model m){
        List<Complaint> complaint = complaintService.getAllComplaints();
        m.addAttribute("complaints", complaint);
        return "allComplaints";
    }


}
