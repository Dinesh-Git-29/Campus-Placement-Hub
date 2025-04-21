package com.example.demo.Repository;

import com.example.demo.Model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCompanyAndRole(String company, String role);
}
