package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private String eventDate; // Store date as a string (yyyy-MM-dd)
}
