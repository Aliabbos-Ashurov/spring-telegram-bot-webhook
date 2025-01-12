package com.abbos.financetrackerbot.mapper;

import com.abbos.financetrackerbot.core.config.security.CustomUserDetails;
import com.abbos.financetrackerbot.domain.dto.user.UserCreateDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserResponseDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:46
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper
        extends GenericMapper<User, UserResponseDTO, UserCreateDTO, UserUpdateDTO> {

    CustomUserDetails toCustomUserDetails(User dto);
}
