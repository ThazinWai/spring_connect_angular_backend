package com.techie.springconnect.repository;

import com.techie.springconnect.model.Subpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubPostRepository extends JpaRepository<Subpost, Long> {
    Optional<Subpost> findByName(String subpostName);
}
