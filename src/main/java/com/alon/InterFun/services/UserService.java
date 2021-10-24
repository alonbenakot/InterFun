package com.alon.InterFun.services;

import com.alon.InterFun.entities.*;
import com.alon.InterFun.exceptions.InterFunException;
import com.alon.InterFun.repositories.*;
import com.alon.InterFun.utilities.QuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final CompanyRepository companyRepo;
    private final JobApplicationRepository jobAppRepo;
    private final InterviewRepository interviewRepo;
    private final PositionRepository positionRepo;
    private final QuestionRepository questionRepo;

    /**
     * Searches the database for a user with the same email and password and returns it.
     * @param email
     * @param password
     * @return
     * @throws InterFunException if no user with given email and password is found.
     */
    public User login(String email, String password) throws InterFunException {
        try {
            Optional<User> optionalUser = userRepo.findByEmailAndPassword(email, password);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
            throw new InterFunException("User not found");
        } catch (Exception e) {
            throw new InterFunException("loginFailed: " + e.getMessage());
        }
    }

    /**
     * Adds a user into the database.
     *
     * @param user
     * @throws InterFunException if a user with the same email already exists.
     */
    public void addUserToDB(User user) throws InterFunException {
        try {

            if (userRepo.existsByEmail(user.getEmail())) {
                throw new InterFunException("A user with the email already exists.");
            }
            userRepo.save(user);
            System.out.println("User " + user.getFirstName() + " " + user.getLastName() + " has been added.");

        } catch (InterFunException e) {
            throw new InterFunException("addUserToDB failed: " + e.getMessage());
        }
    }

    /**
     * Updates a user from the database. If some user fields are null, they will not be updated.
     *
     * @param user to update
     * @return user post update
     * @throws InterFunException if user is not found or if the email is already in use by a different user.
     */
    public User updateUser(User user) throws InterFunException {
        try {
            Optional<User> optionalUser = userRepo.findById(user.getId());
            if (optionalUser.isEmpty()) {
                throw new InterFunException("User not found.");
            }
            if (userRepo.existsByEmailAndIdIsNot(user.getEmail(), user.getId())) {
                throw new InterFunException("That email is already being used by another user.");
            }
            User userFromDB = optionalUser.get();

            userFromDB = updateUserWithSetters(userFromDB, user);

            userRepo.saveAndFlush(userFromDB);
            System.out.println("User with id"  + user.getId() + " has been added.");

            return userFromDB;

        } catch (Exception e) {
            throw new InterFunException("updateUser failed: " + e.getMessage());

        }
    }

    /**
     * Internal method used in updateUser method.
     *
     * @param userFromDB
     * @param newUser
     * @return
     */
    private User updateUserWithSetters(User userFromDB, User newUser) {
        if (newUser.getFirstName() != null) {
            userFromDB.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            userFromDB.setLastName(newUser.getLastName());
        }
        if (newUser.getEmail() != null) {
            userFromDB.setEmail(newUser.getEmail());
        }
        if (newUser.getPassword() != null) {
            userFromDB.setPassword(newUser.getPassword());
        }
        return userFromDB;
    }

    /**
     * Adds a jobApplication to the current user.
     *
     * @param jobApplication
     * @throws InterFunException
     */
    public void addJobApplication(JobApplication jobApplication) throws InterFunException {
        try {
            Optional<User> optionalUser = userRepo.findById(jobApplication.getUser().getId());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.addJobApplication(jobApplication);
                System.out.println("jobApplication " + jobApplication.getJobTitle() + " has been added.");
            } else {
                throw new InterFunException("User not found.");
            }
        } catch (InterFunException e) {
            throw new InterFunException("addJobApplication failed: " + e.getMessage());
        }
    }

    /**
     * Updates a jobApplication owned by the user. If some fields in the jobApplication are null, they will not be changed.
     *
     * @param jobApplication
     * @return
     * @throws InterFunException if the jobApplication is not found
     *                           or if the company/user are different from the jobApplication company in the database.
     */
    public JobApplication updateJobApplication(JobApplication jobApplication, int userId) throws InterFunException {
        try {
            if (jobApplication.getUser().getId() != userId) {
                throw new InterFunException("User id does not match the user owning the jobApplication.");
            }
            Optional<JobApplication> optionalJobApplication = jobAppRepo.findById(jobApplication.getId());
            if (optionalJobApplication.isEmpty()) {
                throw new InterFunException("jobApplication not found.");
            }
            JobApplication jobApplicationFromDB = optionalJobApplication.get();
            if (jobApplication.getCompany() != null && jobApplicationFromDB.getCompany() != jobApplication.getCompany()) {
                throw new InterFunException("Company cannot be changed.");
            }
            if (jobApplication.getUser() != null && jobApplicationFromDB.getUser() != jobApplication.getUser()) {
                throw new InterFunException("User cannot be changed");
            }

            jobApplicationFromDB = updateJobApplicationWithSetters(jobApplicationFromDB, jobApplication);

            jobAppRepo.saveAndFlush(jobApplicationFromDB);

            return jobApplicationFromDB;

        } catch (Exception e) {
            throw new InterFunException("updateJobApplication failed: " + e.getMessage());
        }

    }

    /**
     * Internal method used in updateJobApplication method.
     *
     * @param jobApplicationFromDB
     * @param newJobApplication
     * @return
     */
    private JobApplication updateJobApplicationWithSetters(JobApplication jobApplicationFromDB, JobApplication newJobApplication) {

        if (newJobApplication.getJobTitle() != null) {
            jobApplicationFromDB.setJobTitle(newJobApplication.getJobTitle());
        }
        if (newJobApplication.getPosition() != null) {
            jobApplicationFromDB.setPosition(newJobApplication.getPosition());
        }
        return jobApplicationFromDB;
    }

    /**
     * Returns a jobApplication from the database with the same id.
     *
     * @param jobApplicationId
     * @return
     * @throws InterFunException if jobApplication was not found.
     */
    public JobApplication getJobApplication(int jobApplicationId, int userId) throws InterFunException {
        try {
            Optional<JobApplication> optionalJobApplication = jobAppRepo.findByIdAndUserId(jobApplicationId, userId);
            if (optionalJobApplication.isPresent()) {
                return optionalJobApplication.get();
            } else {
                throw new InterFunException("jobApplication not found.");
            }
        } catch (InterFunException e) {
            throw new InterFunException("getJobApplication failed: " + e.getMessage());
        }
    }

    /**
     * Returns all user jobApplications.
     *
     * @param userId
     * @return a list of jobApplications.
     */
    public List<JobApplication> getJobApplications(int userId) {
        return jobAppRepo.findByUserId(userId);
    }

    /**
     * Deletes a jobApplication.
     *
     * @param jobApplicationId
     */
    public void deleteJobApplication(int jobApplicationId, int userId) throws InterFunException {
        try {
            if (jobAppRepo.existsByIdAndUserId(jobApplicationId, userId)) {
                jobAppRepo.deleteById(jobApplicationId);
                System.out.println("jobApplication with id: " + jobApplicationId + " has been deleted.");
            } else {
                throw new InterFunException("jobApplication not found.");
            }
        } catch (Exception e) {
            throw new InterFunException("deleteJbApplication failed: " + e.getMessage());
        }
    }

    /**
     * Add an interview to an existing Job Application.
     *
     * @param interview
     * @param userId    of owning user
     * @param jobAppId  of owning job application
     * @throws InterFunException if user already has an interview at the same time.
     */
    public void addInterview(Interview interview, int userId, int jobAppId) throws InterFunException {
        try {
            validateInterviewTime(interview, userId);
            Optional<JobApplication> optionalJobApplication = jobAppRepo.findById(jobAppId);
            if (optionalJobApplication.isPresent()) {
                optionalJobApplication.get().addInterview(interview);
                System.out.println("Interview added to user " + userId);
            } else {
                throw new InterFunException("Job Application not found.");
            }
        } catch (Exception e) {
            throw new InterFunException("addInterview failed: " + e.getMessage());
        }
    }

    /**
     * Return an interview with matching id.
     * @param interviewId
     * @param userId
     * @return
     * @throws InterFunException if interview was not found.
     */
    public Interview getInterview(int interviewId, int userId) throws InterFunException {
        try {
            Optional<Interview> optionalInterview = interviewRepo.findByIdAndJobApplicationUserId(interviewId, userId);
            if (optionalInterview.isPresent()) {
                return optionalInterview.get();
            }
            throw new InterFunException("Interview not found.");
        } catch (Exception e) {
            throw new InterFunException("getInterview failed: " + e.getMessage());
        }
    }

    /**
     * Updates an interview from the database.
     *
     * @param interview
     * @param userId
     * @throws InterFunException if interview was not found, the user isn't the owner of the interview
     *                           or the interview time is not validated.
     */
    public void updateInterview(Interview interview, int userId) throws InterFunException {
        try {
            if (interview.getJobApplication().getUser().getId() != userId) {
                throw new InterFunException("User id does not match the user owning the interview.");
            }
            validateInterviewTime(interview, userId);
            Optional<Interview> optionalInterview = interviewRepo.findById(interview.getId());
            if (optionalInterview.isEmpty()) {
                throw new InterFunException("Interview not found.");
            }
            Interview interviewFromDB = optionalInterview.get();
            interviewFromDB = updateInterviewWithSetters(interview, interviewFromDB);
            System.out.println("Interview with id " + interview.getId() + " has been updated.");
            interviewRepo.saveAndFlush(interviewFromDB);

        } catch (Exception e) {
            throw new InterFunException("updateInterview failed: " + e.getMessage());
        }

    }

    /**
     * Internal method used in updateInterview method.
     *
     * @param newInterview
     * @param interviewFromDB
     * @return
     */
    private Interview updateInterviewWithSetters(Interview newInterview, Interview interviewFromDB) {
        if (newInterview.getInterviewerName() != null) {
            interviewFromDB.setInterviewerName(newInterview.getInterviewerName());
        }
        if (newInterview.getInterviewTime() != null) {
            interviewFromDB.setInterviewTime(newInterview.getInterviewTime());
        }
        if (newInterview.getAddress() != null) {
            interviewFromDB.setAddress(newInterview.getAddress());
        }
        if (newInterview.getOtherComments() != null) {
            interviewFromDB.setOtherComments(newInterview.getOtherComments());
        }
        return interviewFromDB;
    }

    /**
     * Internal method that makes sure that nor interview exists at a time close to given interview.
     *
     * @param interview
     * @param userId
     * @return
     * @throws InterFunException if an interview exists 90 minutes before or after interview.
     */
    private void validateInterviewTime(Interview interview, int userId) throws InterFunException {
        try {
            LocalDateTime beforeInterview = interview.getInterviewTime().minusMinutes(90);
            LocalDateTime afterInterview = interview.getInterviewTime().plusMinutes(90);
            Optional<Interview> optionalInterview = interviewRepo.findByJobApplicationUserIdAndInterviewTimeBetween(userId, beforeInterview, afterInterview);
            if (optionalInterview.isPresent()) {
                Interview existingInterview = optionalInterview.get();
                throw new InterFunException("You already have an interview for " + existingInterview.getJobApplication().getCompany().getName()
                        + " at " + existingInterview.getInterviewTime() + "! It is too close, better call and reschedule.");
            }
        } catch (Exception e) {
            throw new InterFunException("Time is not valid: " + e.getMessage());
        }
    }

    /**
     * Deletes an interview.
     *
     * @param interviewId
     * @throws InterFunException if interview was not found.
     */
    public void deleteInterview(int interviewId, int userId) throws InterFunException {
        try {
            if (interviewRepo.existsByIdAndJobApplicationUserId(interviewId, userId)) {
                interviewRepo.deleteById(interviewId);
                System.out.println("Interview with id: " + interviewId + " has been deleted.");
            } else {
                throw new InterFunException("Interview not found.");
            }
        } catch (Exception e) {
            throw new InterFunException("deleteInterview failed: " + e.getMessage());
        }

    }

    /**
     * Adds a company to the database.
     *
     * @param company
     * @throws InterFunException if company with the same name already exists.
     */
    public void addCompany(Company company) throws InterFunException {
        try {
            if (!companyRepo.existsByNameIgnoreCase(company.getName())) {
                companyRepo.save(company);
                System.out.println("Company " + company.getName() + " has been added.");
            } else {
                throw new InterFunException("Company with same name already exists.");
            }
        } catch (Exception e) {
            throw new InterFunException("addCompany failed: " + e.getMessage());
        }
    }

    /**
     * Adds a question to an existing company.
     *
     * @param companyId
     * @param question
     * @throws InterFunException if company was not found.
     */
    public void addCompanyQuestion(int companyId, Question question) throws InterFunException {
        try {
            Optional<Company> optionalCompany = companyRepo.findById(companyId);
            if (optionalCompany.isPresent()) {
                optionalCompany.get().addQuestion(question);
                System.out.println("Question added to company " + optionalCompany.get().getName());
            } else {
                throw new InterFunException("Company not found.");
            }
        } catch (InterFunException e) {
            throw new InterFunException("addCompanyQuestion failed: " + e.getMessage());
        }

    }

    /**
     * Add a question to an existing position.
     *
     * @param positionId
     * @param question
     * @throws InterFunException if position was not found.
     */
    public void addPositionQuestion(int positionId, Question question) throws InterFunException {
        try {
            Optional<Position> optionalPosition = positionRepo.findById(positionId);
            if (optionalPosition.isPresent()) {
                optionalPosition.get().addQuestion(question);
                System.out.println("Question added to position " + optionalPosition.get().getName());
            } else {
                throw new InterFunException("Company not found.");
            }
        } catch (InterFunException e) {
            throw new InterFunException("addPositionQuestion failed: " + e.getMessage());
        }

    }

    /**
     * @param type
     * @return all questions with same type
     */
    public List<Question> getQuestions(QuestionType type) {
        return questionRepo.findByType(type);
    }

    /**
     * @param topic
     * @return all questions with same topic.
     */
    public List<Question> getQuestions(String topic) {
        return questionRepo.findByTopic(topic);

    }

    /**
     * @param type  (Company or Position)
     * @param topic
     * @return all questions by topic and type(Company or Position).
     */
    public List<Question> getQuestions(QuestionType type, String topic) {
        return questionRepo.findByTopicAndType(type, topic);

    }

    /**
     * Toggle the reminder from true to false or from false to true.
     *
     * @return
     * @throws InterFunException if interview was not found.
     */
    public boolean toggleReminder(int interviewId, int userId) throws InterFunException {
        try {
            Optional<Interview> optionalInterview = interviewRepo.findByIdAndJobApplicationUserId(interviewId, userId);
            if (optionalInterview.isPresent()) {
                Interview interviewFromDB = optionalInterview.get();

                boolean reminder = !interviewFromDB.isReminder();

                interviewFromDB.setReminder(reminder);

                return reminder;
            }
            throw new InterFunException("Interview doesn't exist.");

        } catch (InterFunException e) {
            throw new InterFunException("toggleReminder failed: " + e.getMessage());
        }

    }

    /**
     * Toggle the success from true to false or from false to true.
     *
     * @param interviewId
     * @param userId
     * @throws InterFunException if interview was not found.
     */
    public boolean toggleSuccess(int interviewId, int userId) throws InterFunException {
        try {
            Optional<Interview> optionalInterview = interviewRepo.findByIdAndJobApplicationUserId(interviewId, userId);
            if (optionalInterview.isPresent()) {
                Interview interviewFromDB = optionalInterview.get();

                boolean success = !interviewFromDB.isSuccess();

                interviewFromDB.setSuccess(success);

                return success;
            }
            throw new InterFunException("Interview doesn't exist.");

        } catch (InterFunException e) {
            throw new InterFunException("toggleSuccess failed: " + e.getMessage());
        }

    }


}
