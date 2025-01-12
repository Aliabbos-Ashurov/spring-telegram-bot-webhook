package com.abbos.financetrackerbot.repository;

import com.abbos.financetrackerbot.domain.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:28
 **/
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    Optional<ServiceType> findByName(String name);
}
