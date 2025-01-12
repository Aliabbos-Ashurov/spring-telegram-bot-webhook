package com.abbos.financetrackerbot.repository;

import com.abbos.financetrackerbot.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:23
 **/
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPhoneNumber(String phoneNumber);

    List<Client> findByServiceType(String serviceType);
}
