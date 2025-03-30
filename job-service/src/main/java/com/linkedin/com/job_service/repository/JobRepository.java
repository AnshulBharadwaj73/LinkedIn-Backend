package com.linkedin.com.job_service.repository;

import com.linkedin.com.job_service.entity.JobListing;
import com.linkedin.com.job_service.enums.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobListing, Long> {

    @Query("SELECT j FROM JobListing j WHERE j.location = :location")
    List<JobListing> findJobsByLocation(@Param("location") Location location);
}
