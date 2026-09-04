package com.tausif.controller;


import com.tausif.beans.Admin;
import com.tausif.beans.Complaint;
import com.tausif.service.AdminService;
import com.tausif.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Date;
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

    @PostMapping("/checkLogin")
    public String checkLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes ra){
        Admin a = adminService.checkLogin(email, password);
        if(a == null){
            ra.addFlashAttribute("msg", "Wrong Credentials!");
        }else{
            LocalDateTime time = LocalDateTime.now();
            String greet = "Logged in at " + time;
            ra.addFlashAttribute("msg", greet);
        }
        return "redirect:/adminHome";
    }

    @GetMapping("/allComplaints")
    public String allComplaints(Model m){
        List<Complaint> complaint = complaintService.getAllComplaints();
        m.addAttribute("complaints", complaint);
        return "allComplaints";
    }


}
