package com.techie.springconnect.repository;

import com.techie.springconnect.model.Subpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubPostRepository extends JpaRepository<Subpost, Long> {
}
