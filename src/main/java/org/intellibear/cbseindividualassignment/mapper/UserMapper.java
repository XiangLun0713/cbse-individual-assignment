package org.intellibear.cbseindividualassignment.mapper;


import org.intellibear.cbseindividualassignment.model.dto.UserDTO;
import org.intellibear.cbseindividualassignment.model.entity.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        return user;
    }
}
