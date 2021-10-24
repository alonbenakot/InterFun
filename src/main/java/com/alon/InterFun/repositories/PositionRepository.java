package com.alon.InterFun.repositories;

import com.alon.InterFun.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Position> findByNameIgnoreCase(String name);
}
