package org.intellibear.cbseindividualassignment.service;


import org.intellibear.cbseindividualassignment.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    void addUser(UserDTO userDTO);

    UserDTO updateUser(Long userId, UserDTO updatedUserDTO);

    void deleteUser(Long userId);
}
