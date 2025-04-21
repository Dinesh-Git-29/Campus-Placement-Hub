package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {
    @GetMapping("/signup")
    public String signupPage(){
        return "/signup";
    }

    @GetMapping("/verify-otp")
    public String verifyotpPage(){
        return "/verify-otp";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/login";
    }

    @GetMapping("/forgot-password")
    public String forgetPasswordPage(){
        return "/forgot-password";
    }

    @GetMapping("/profile")
    public String profilePage(){
        return "/profile";
    }

    @GetMapping("/add-jobs")
    public String addJobsPage(){
        return "/add-jobs";
    }

    @GetMapping("/student-jobs")
    public String studentJobsPage(){
        return "/student-jobs";
    }

    @GetMapping("/student-dashboard")
    public String studentDashboardPage(){
        return "/student-dashboard";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboardPage(){
        return "/admin-dashboard";
    }

    @GetMapping("/experience")
    public String experiencePage(){
        return "/experience";
    }

    @GetMapping("/stu-resources")
    public String studentResourcesPage(){
        return "/stu-resources";
    }

    @GetMapping("/community")
    public String communityPage(){
        return "/community";
    }

    @GetMapping("/chatbot")
    public String  chatbotPage(){
        return "/chatbot";
    }

    @GetMapping("/resources")
    public String  resourcePage(){
        return "/resources";
    }

    @GetMapping("/")
    public String  indexPage(){
        return "/index";
    }

    @GetMapping("/ad-event")
    public String adEventPage(){
        return "/ad-event";
    }

    @GetMapping("/st-event")
    public String  stEventPage(){
        return "/st-event";
    }

    @GetMapping("/st-home")
    public String  stHomePage(){
        return "/student-home";
    }


    @GetMapping("/ad-home")
    public String  adHomePage(){
        return "/ad-home";
    }

    @GetMapping("/logout")
    public String  logoutPage(){
        return "/logout";
    }

    @GetMapping("/analytics")
    public String  analyticsPage(){
        return "/analytics";
    }

}
