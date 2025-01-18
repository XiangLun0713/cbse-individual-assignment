package org.intellibear.cbseindividualassignment.service;

import org.intellibear.cbseindividualassignment.mapper.UserMapper;
import org.intellibear.cbseindividualassignment.model.dto.UserDTO;
import org.intellibear.cbseindividualassignment.model.entity.User;
import org.intellibear.cbseindividualassignment.repository.UserRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        user.setHashedPassword(null);
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map((user) -> {
                    user.setHashedPassword(null);
                    return UserMapper.toDTO(user);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setHashedPassword(hashPassword(userDTO.getPassword())); // Hash the password
        user.setCreatedAt(LocalDateTime.now());
        userRepo.save(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setName(updatedUserDTO.getName());
        if (updatedUserDTO.getPassword() != null) {
            user.setHashedPassword(hashPassword(updatedUserDTO.getPassword())); // Update hashed password if provided
        }
        User updatedUser = userRepo.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        userRepo.deleteById(userId);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
