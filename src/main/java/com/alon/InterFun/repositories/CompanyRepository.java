package com.alon.InterFun.repositories;

import com.alon.InterFun.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByNameIgnoreCase(String name);
}
