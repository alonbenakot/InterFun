package com.alon.InterFun.controllers;

import com.alon.InterFun.entities.*;
import com.alon.InterFun.exceptions.InterFunException;
import com.alon.InterFun.services.UserService;
import com.alon.InterFun.utilities.QuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) throws InterFunException {
        service.addUserToDB(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable int userId, @RequestBody User user) throws InterFunException {
         service.updateUser(user);
    }

    @PostMapping("/job-applications")
    @ResponseStatus(HttpStatus.CREATED)
    public void addJobApplication(@RequestBody JobApplication jobApplication) throws InterFunException {
        service.addJobApplication(jobApplication);
    }

    @PutMapping("/job-applications")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateJobApplication(@RequestBody JobApplication jobApplication, @RequestParam int jobApplicationId, @RequestParam int userId) throws InterFunException {
        service.updateJobApplication(jobApplication, userId);
    }

    @GetMapping("/job-applications")
    @ResponseStatus(HttpStatus.OK)
    public JobApplication getJobApplication(@RequestParam int jobApplicationId, @RequestParam int userId) throws InterFunException {
        return service.getJobApplication(jobApplicationId, userId);
    }

    @GetMapping("/{id}/job-applications")
    @ResponseStatus(HttpStatus.OK)
    public List<JobApplication> getJobApplications(@PathVariable int id) {
        return service.getJobApplications(id);
    }

    @DeleteMapping("/job-applications")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobApplication(@RequestParam int jobApplicationId, @RequestParam int userId) throws InterFunException {
        service.deleteJobApplication(jobApplicationId, userId);
    }

    @PostMapping("/interviews")
    @ResponseStatus(HttpStatus.CREATED)
    public void addInterview(@RequestBody Interview interview, @RequestParam int userId, @RequestParam int jobAppId) throws InterFunException {
        service.addInterview(interview, userId, jobAppId);
    }

    @GetMapping("/interviews")
    @ResponseStatus(HttpStatus.OK)
    public Interview getInterview(@RequestParam int interviewId, @RequestParam int userId) throws InterFunException {
        return service.getInterview(interviewId, userId);
    }

    @PutMapping("/interviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInterview(@RequestBody Interview interview, @PathVariable(name = "id") int userId) throws InterFunException {
        service.updateInterview(interview, userId);
    }

    @DeleteMapping("/interviews")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterview(@RequestParam int interviewId, @RequestParam int userId) throws InterFunException {
        service.deleteInterview(interviewId, userId);
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestBody Company company) throws InterFunException {
        service.addCompany(company);
    }

    @PostMapping("/companies/{id}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompanyQuestion(@PathVariable int compId, @RequestBody Question question) throws InterFunException {
        service.addCompanyQuestion(compId, question);
    }

    @PostMapping("/positions/{id}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPositionQuestion(int positionId, @RequestBody Question question) throws InterFunException {
        service.addPositionQuestion(positionId, question);
    }

    @GetMapping("/questions")
    public List<Question> getQuestions(@RequestParam(required = false) String topic, @RequestParam(required = false) QuestionType type) {
        if (topic != null && type != null) {
            return service.getQuestions(type, topic);
        }
        if (topic != null && type == null) {
            return service.getQuestions(topic);
        }
        if (topic == null && type != null) {
            return service.getQuestions(type);
        }
        else {
            return new ArrayList<>();
        }
    }

    @PatchMapping("/reminder")
    @ResponseStatus(HttpStatus.OK)
    public boolean toggleReminder(@RequestParam int interviewId, @RequestParam int userId) throws InterFunException {
        return service.toggleReminder(interviewId, userId);
    }

    @PatchMapping("/success")
    @ResponseStatus(HttpStatus.OK)
    public boolean toggleSuccess(@RequestParam int interviewId, @RequestParam int userId) throws InterFunException {
        return service.toggleSuccess(interviewId, userId);
    }



}
