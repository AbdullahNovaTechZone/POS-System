package com.novatechzone.pos.domain.security.repos;

import com.novatechzone.pos.domain.security.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByUsername(String username);

    Optional<Owner> findByUsernameAndPassword(String username, String password);
}
