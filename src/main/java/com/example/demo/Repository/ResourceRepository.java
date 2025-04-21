package com.example.demo.Repository;

import com.example.demo.Model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByCategory(String category);
}
