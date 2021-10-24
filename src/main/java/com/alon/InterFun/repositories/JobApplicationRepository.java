package com.alon.InterFun.repositories;

import com.alon.InterFun.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findByUserId(int userId);

    Optional<JobApplication> findByIdAndUserId(int interviewId, int userId);

    boolean existsByIdAndUserId(int interviewId, int userId);

    boolean existsByUserIdAndSentTime(int userId, LocalDateTime sentTime);
}
