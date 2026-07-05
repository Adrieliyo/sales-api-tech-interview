package com.macronnect.sales_api.repository;

import com.macronnect.sales_api.model.entity.Client;
import com.macronnect.sales_api.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Client> findAllByStatus(Status status, Pageable pageable);
}
