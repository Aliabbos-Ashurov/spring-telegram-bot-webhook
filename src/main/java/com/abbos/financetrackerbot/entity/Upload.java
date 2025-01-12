package com.abbos.financetrackerbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  14:12
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Upload extends BaseEntity {

    @NotBlank
    @Column(name = "original_name", nullable = false, updatable = false)
    private String originalName;

    @NotBlank
    @Column(name = "generated_name", nullable = false, unique = true, updatable = false)
    private String generatedName;

    @PositiveOrZero(message = "File size must be zero or positive")
    @Column(nullable = false)
    private Long size;

    @NotBlank
    @Column(name = "file_type", nullable = false, length = 4)
    private String fileType;

    @NotBlank
    @Column(nullable = false, updatable = false)
    private String url;
}
