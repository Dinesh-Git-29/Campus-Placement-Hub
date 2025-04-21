package com.example.demo.Repository;

import com.example.demo.Model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    @Query("SELECT s FROM Profile s WHERE s.tenthPercent >= :minTenth AND s.twelvePercent >= :minTwelfth AND s.cgpa >= :minCGPA AND s.arrears <= :maxArrears AND s.arrearHistory = :arrearHistory")
    List<Profile> findEligibleProfile(@Param("minTenth") double minTenth,
                                       @Param("minTwelfth") double minTwelfth,
                                       @Param("minCGPA") double minCGPA,
                                       @Param("maxArrears") int maxArrears,
                                       @Param("arrearHistory") String arrearHistory);
}
