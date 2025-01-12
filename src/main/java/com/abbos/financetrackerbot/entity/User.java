package com.abbos.financetrackerbot.entity;

import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.abbos.financetrackerbot.state.AuthenticationState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  13:52
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_username", columnList = "username"),
                @Index(name = "idx_chat_id", columnList = "chatId"),
                @Index(name = "idx_role", columnList = "role"),
                @Index(name = "idx_state", columnList = "state")
        }
)
public class User extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String fullname;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "premium", nullable = false)
    private boolean premium = false;

    @Builder.Default
    @Column(name = "two_factor_enabled", nullable = false)
    private boolean twoFactorEnabled = false;

    @Builder.Default
    @Column(name = "notifications_enabled", nullable = false)
    private boolean notificationsEnabled = true;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(nullable = false, unique = true)
    private Long chatId;

    @Builder.Default
    @Column(nullable = false)
    private String state = AuthenticationState.REQUEST_PASSWORD;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language = Language.ENG;

}
