package com.abbos.financetrackerbot.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:20
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "service_types")
public class ServiceType extends BaseEntity {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
}
