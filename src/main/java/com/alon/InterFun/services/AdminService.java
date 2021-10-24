package com.alon.InterFun.services;

import com.alon.InterFun.entities.Company;
import com.alon.InterFun.entities.Position;
import com.alon.InterFun.entities.User;
import com.alon.InterFun.exceptions.InterFunException;
import com.alon.InterFun.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepo;
    private final CompanyRepository companyRepo;
    private final JobApplicationRepository jobAppRepo;
    private final InterviewRepository interviewRepo;
    private final PositionRepository positionRepo;
    private final QuestionRepository questionRepo;
    private final UserService userService;
    private String email = "admin@email.com";
    private String password = "minda";

    /**
     * Adds a user to the database.
     *
     * @param user
     * @throws InterFunException
     */
    public void AddUser(User user) throws InterFunException {
        try {
            userService.addUserToDB(user);
            System.out.println("User " + user.getFirstName() + " " + user.getLastName() + " has been added.");
        } catch (Exception e) {
            throw new InterFunException(e.getMessage());
        }
    }

    /**
     * Update a user in the database.
     *
     * @param user
     * @throws InterFunException
     */
    public void updateUser(User user) throws InterFunException {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            throw new InterFunException(e.getMessage());
        }
    }

    /**
     * Returns a user from the database.
     *
     * @param id
     * @return
     * @throws InterFunException if usr is not found.
     */
    public User getUser(int id) throws InterFunException {
        try {
            Optional<User> optionalUser = userRepo.findById(id);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
            throw new InterFunException("User not found.");
        } catch (Exception e) {
            throw new InterFunException("getUser failed: " + e.getMessage());
        }
    }

    /**
     * Return a List of all users in the database.
     *
     * @return
     * @throws InterFunException
     */
    public List<User> getUsers() throws InterFunException {
        try {
            return userRepo.findAll();
        } catch (Exception e) {
            throw new InterFunException("getUsers failed: " + e.getMessage());
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param id
     * @throws InterFunException if user is not found.
     */
    public void deleteUser(int id) throws InterFunException {
        try {
            if (userRepo.existsById(id)) {
                userRepo.deleteById(id);
                System.out.println("User with id " + id);
            }
            throw new InterFunException("User not found.");
        } catch (Exception e) {
            throw new InterFunException("deleteUser failed: " + e.getMessage());
        }
    }

    /**
     * Add a company to the database.
     *
     * @param company
     * @throws InterFunException
     */
    public void addCompany(Company company) throws InterFunException {
        try {
            userService.addCompany(company);
        } catch (Exception e) {
            throw new InterFunException(e.getMessage());
        }
    }

    /**
     * Returns a company from the database.
     *
     * @param id
     * @return
     * @throws InterFunException if company was not found.
     */
    public Company getCompany(int id) throws InterFunException {
        try {
            Optional<Company> optionalCompany = companyRepo.findById(id);
            if (optionalCompany.isPresent()) {
                return optionalCompany.get();
            }
            throw new InterFunException("Company not found.");
        } catch (Exception e) {
            throw new InterFunException("getCompany failed: " + e.getMessage());
        }
    }

    /**
     * Returns all existing companies.
     *
     * @return
     * @throws InterFunException
     */
    public List<Company> getCompanies() throws InterFunException {
        try {
            return companyRepo.findAll();
        } catch (Exception e) {
            throw new InterFunException("getCompanies failed: " + e.getMessage());
        }
    }

    /**
     * Updates a company from the database.
     *
     * @param company
     * @throws InterFunException if company was not found or if a company with matching name exists.
     */
    public void updateCompany(Company company) throws InterFunException {
        try {
            if (!companyRepo.existsByNameIgnoreCase(company.getName())) {
                companyRepo.saveAndFlush(company);
            }
            throw new InterFunException("Company with matching name already exists. There can only be one McDonald's.");
        } catch (Exception e) {
            throw new InterFunException("updateCompany failed: " + e.getMessage());
        }
    }

    /**
     * Deletes a company from the database.
     *
     * @param id
     * @throws InterFunException if company was not found.
     */
    public void deleteCompany(int id) throws InterFunException {
        try {
            if (!companyRepo.existsById(id)) {
                throw new InterFunException("Company not found.");
            }
            companyRepo.deleteById(id);
            System.out.println("Company with id " + id + " has been deleted");
        } catch (Exception e) {
            throw new InterFunException("deleteCompany failed: " + e.getMessage());
        }
    }

    public void addPosition(Position position) throws InterFunException {
        try {
            if (positionRepo.existsByNameIgnoreCase(position.getName())) {
                throw new InterFunException("Position " + position.getName() + " already exists.");
            }
            position.setName(position.getName().toLowerCase());
            positionRepo.save(position);
            System.out.println("Position " + position.getName() + " added successfully");

        } catch (InterFunException e) {
            throw new InterFunException("addPosition failed: " + e.getMessage());
        }

    }

    public Position getPosition(int id) throws InterFunException {
        try {
            Optional<Position> optionalPosition = positionRepo.findById(id);
            if (optionalPosition.isPresent()) {
                return optionalPosition.get();
            }
            throw new InterFunException("Position not found.");
        } catch (Exception e) {
            throw new InterFunException("getPosition failed: " + e.getMessage());
        }
    }

    /**
     * Returns all existing position.
     *
     * @return
     * @throws InterFunException
     */
    public List<Position> getPositions() throws InterFunException {
        try {
            return positionRepo.findAll();
        } catch (Exception e) {
            throw new InterFunException("getPosition failed: " + e.getMessage());
        }
    }

    public void updatePosition(Position position) throws InterFunException {
        try {
            Optional<Position> optionalPosition = positionRepo.findByNameIgnoreCase(position.getName());
            if (optionalPosition.isPresent()) {
                throw new InterFunException("Position " + position.getName() + " already exists.");
            }
            Position positionFromDB = optionalPosition.get();
            positionFromDB.setName(position.getName());
            System.out.println("Position with id " + position.getId() + " has been updated.");
        } catch (InterFunException e) {
            throw new InterFunException("updatePosition failed:" + e.getMessage());
        }
    }

    public void deletePosition(int id) throws InterFunException {
        try {
            if (!positionRepo.existsById(id)) {
                throw new InterFunException("Position not found.");
            }
            System.out.println("Position with id " + id + " has been deleted");
            positionRepo.deleteById(id);
        } catch (InterFunException e) {
            throw new InterFunException("deletePosition failed: " + e.getMessage());
        }
    }
}
