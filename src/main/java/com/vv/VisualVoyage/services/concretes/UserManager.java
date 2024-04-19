package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.UserUpdateDto;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse findUserById(long id) {
        User user = userRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("User not found with the given id")); //TODO VisualVoyageException will be added!

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getFirstName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with the given email!")); //TODO VisualVoyageException will be added!

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getFirstName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }

    @Transactional
    @Override
    public String followUser(long reqUserId, long followUserId) {

        User reqUser = userRepository.findById(reqUserId)
                .orElseThrow(() -> new RuntimeException("User not found with the given id")); //TODO VisualVoyageException will be added!
         User followUser = userRepository.findById(followUserId)
                .orElseThrow(() -> new RuntimeException("User not found with the given id")); //TODO VisualVoyageException will be added!

        followUser.getFollowers().add(reqUser.getId());
        reqUser.getFollowings().add(followUser.getId());

        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "User successfully followed!";
    }

    @Override
    public List<UserResponse> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);

        return users.stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build()).collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(UserUpdateDto userUpdateDto, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with the given id")); //TODO VisualVoyageException will be added!
        if(userUpdateDto.getFirstName() != null && !userUpdateDto.getFirstName().isEmpty()) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if(userUpdateDto.getLastName() != null && !userUpdateDto.getLastName().isEmpty()) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if(userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isEmpty()) {
            user.setEmail(userUpdateDto.getEmail());
        }
        User updated = userRepository.save(user);

        return UserResponse.builder()
                .id(updated.getId())
                .firstName(updated.getFirstName())
                .lastName(updated.getLastName())
                .email(updated.getEmail())
                .gender(updated.getGender())
                .build();
    }
}