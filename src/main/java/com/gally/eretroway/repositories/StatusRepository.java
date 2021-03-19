package com.gally.eretroway.repositories;


import com.gally.eretroway.models.EStatus;
import com.gally.eretroway.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(EStatus name);
}
