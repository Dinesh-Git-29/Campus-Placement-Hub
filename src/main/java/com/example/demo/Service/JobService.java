package com.example.demo.Service;

import com.example.demo.Model.Job;
import com.example.demo.Model.Profile;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private NotificationService notificationService;


    public String addJob(Job job) {
        jobRepository.save(job);
        notifyEligibleStudents(job);
        return "Job added successfully!";
    }

    public String updateJob(Job job) {
        jobRepository.save(job);
        notifyEligibleStudents(job);
        return "Job updated successfully!";
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public String deleteJob(Long id) {
        jobRepository.deleteById(id);
        return "Job deleted successfully!";
    }

    private void notifyEligibleStudents(Job job) {
        List<String> students =profileRepository.findAll()
                .stream()
                .map(Profile::getEmail)
                .collect(Collectors.toList());

        String subject1 = "New Job Opportunity: " + job.getCompanyName();
        String body1 = "Dear Student,\n\nA new job opportunity is available:\n" +
                "Company: " + job.getCompanyName() + "\n" +
                "Role: " + job.getRole() + "\n" +
                "Apply Here: " + job.getJobLink() + "\n\nBest of luck!\nPlacement Team";
        String message1="New Job Available"+"/n"+"Company Name:"+job.getCompanyName().toUpperCase()+"/n"+"Role:"+job.getRole();
        emailService.sendEmail(students, subject1, body1);
        notificationService.createNotification(students,message1);

        List<Profile> eligibleStudents = profileRepository.findAll().stream()
                .filter(profile ->
                        profile.getTenthPercent() >= job.getTenthPercent() &&
                                profile.getTwelvePercent() >= job.getTwelvePercent() &&
                                profile.getCgpa() >= job.getCgpa() &&
                                profile.getArrears() <= job.getArrears() &&
                                ("No".equals(job.getArrearHistory()) ? "Yes".equals(profile.getArrearHistory()) : true) &&
                                (job.getSkills() != null && !job.getSkills().isEmpty() &&
                                        Arrays.stream(job.getSkills().split(","))
                                                .map(String::trim)
                                                .anyMatch(skill -> profile.getSkills().toLowerCase().contains(skill.toLowerCase()))) &&
                                job.getRole().toLowerCase().contains(profile.getPreferredRole().toLowerCase())

                )
                .collect(Collectors.toList());
        System.out.println(eligibleStudents);
        List<String> emails = eligibleStudents.stream()
                .map(Profile::getEmail)
                .collect(Collectors.toList());

        String subject = "New Job Opportunity: " + job.getCompanyName();
        String body = "Dear Student,\n\nA new job opportunity is available:\n" +
                "Company: " + job.getCompanyName() + "\n" +
                "Role: " + job.getRole() + "\n" +
                "Apply Here: " + job.getJobLink() + "\n\nBest of luck!\nPlacement Team";
        String message="New Job Available"+"/n"+"Company Name:"+job.getCompanyName().toUpperCase()+"/n"+"Role:"+job.getRole();
        emailService.sendEmail(emails, subject, body);
        notificationService.createNotification(emails,message);
    }

}
