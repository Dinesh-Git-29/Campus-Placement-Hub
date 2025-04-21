package com.example.demo.Controller;

import com.example.demo.Model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.example.demo.Service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    JobService jobService;

    @PostMapping("/save")
    public String addJob(@RequestBody Job job) {
        return jobService.addJob(job);
    }

    @PutMapping("/save")
    public String updateJob(@RequestBody Job job) {
        return jobService.updateJob(job);
    }

    @GetMapping("/list")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public Optional<Job> getJob(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        return jobService.deleteJob(id);
    }
}