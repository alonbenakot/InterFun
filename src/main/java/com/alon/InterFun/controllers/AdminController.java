package com.alon.InterFun.controllers;

import com.alon.InterFun.entities.Company;
import com.alon.InterFun.entities.Position;
import com.alon.InterFun.entities.User;
import com.alon.InterFun.exceptions.InterFunException;
import com.alon.InterFun.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService service;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) throws InterFunException {
        service.AddUser(user);
    }

    @PutMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User user) throws InterFunException {
        service.updateUser(user);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public User getUser(@PathVariable int id) throws InterFunException {
        return service.getUser(id);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.FOUND)
    public List<User> getUsers() throws InterFunException {
        return service.getUsers();

    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) throws InterFunException {
        service.deleteUser(id);
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestBody Company company) throws InterFunException {
        service.addCompany(company);
    }

    @GetMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Company getCompany(@PathVariable int id) throws InterFunException {
        return service.getCompany(id);

    }

    @GetMapping("/companies")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Company> getCompanies() throws InterFunException {
        return service.getCompanies();
    }

    @PutMapping("/companies")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompany(@RequestBody Company company) throws InterFunException {
        service.updateCompany(company);
    }

    @DeleteMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable int id) throws InterFunException {
        service.deleteCompany(id);

    }

    @PostMapping("/positions")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPosition(@RequestBody Position position) throws InterFunException {
        service.addPosition(position);

    }

    @GetMapping("/positions/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Position getPosition(@PathVariable int id) throws InterFunException {
        return service.getPosition(id);

    }
    @GetMapping("/positions")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Position> getPositions() throws InterFunException {
        return service.getPositions();
    }

    @PutMapping("/positions")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePosition(@RequestBody Position position) throws InterFunException {
        service.updatePosition(position);

    }

    @DeleteMapping("/positions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable int id) throws InterFunException {
        service.deletePosition(id);
    }


}
