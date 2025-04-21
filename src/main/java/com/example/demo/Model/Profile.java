package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    private String email; // Primary Key

    private String department;

    @Column(unique = true)
    private String rollno; // Unique Roll Number

    private double tenthPercent;
    private double twelvePercent;
    private double cgpa;
    private int arrears;
    private String arrearHistory;
    private String preferredRole;
    private String skills;
}
