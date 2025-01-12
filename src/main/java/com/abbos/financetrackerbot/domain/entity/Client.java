package com.abbos.financetrackerbot.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:19
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(
        name = "clients",
        indexes = {
                @Index(name = "idx_phone_number", columnList = "phoneNumber")
        }
)
public class Client extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @NotBlank
    @Column(nullable = false)
    private String serviceType;
}
