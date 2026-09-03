package com.tausif.controller;


import com.tausif.beans.Complaint;
import com.tausif.service.AdminService;
import com.tausif.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/allComplaints")
    public String allComplaints(Model m){
        List<Complaint> complaint = complaintService.getAllComplaints();
        m.addAttribute("complaints", complaint);
        return "allComplaints";
    }


}
