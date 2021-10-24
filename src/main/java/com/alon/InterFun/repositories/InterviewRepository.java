package com.alon.InterFun.repositories;

import com.alon.InterFun.entities.Interview;
import com.alon.InterFun.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer> {


    Optional<Interview> findByIdAndJobApplicationUserId(int interviewId, int userId);

    Optional<Interview> findByJobApplicationUserIdAndInterviewTimeBetween(int userId, LocalDateTime beforeInterview, LocalDateTime afterInterview);

    boolean existsByIdAndJobApplicationUserId(int interviewId, int userId);
}
