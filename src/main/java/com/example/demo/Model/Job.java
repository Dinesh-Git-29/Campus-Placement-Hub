package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String role;
    private double tenthPercent;
    private double twelvePercent;
    private double cgpa;
    private int arrears;
    private String arrearHistory;
    private String skills;
    private String jobLink;
}
