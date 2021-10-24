package com.alon.InterFun.repositories;

import com.alon.InterFun.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, int id);

    Optional<User> findByEmailAndPassword(String email, String password);

}
