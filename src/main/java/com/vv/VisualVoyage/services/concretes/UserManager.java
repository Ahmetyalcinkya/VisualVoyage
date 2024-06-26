package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.UserUpdateDto;
import com.vv.VisualVoyage.dtos.responses.PostResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.exceptions.VisualVoyageExceptions;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .profilePicture(user.getProfilePicture())
                .username(user.getUsername())
                .followers(user.getFollowers())
                .followings(user.getFollowings())
                .build()).collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserById(long id) {
        User user = userRepository.findById(id)
               .orElseThrow(() -> new VisualVoyageExceptions("User not found with the given id", HttpStatus.NOT_FOUND));

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .profilePicture(user.getProfilePicture())
                .coverPicture(user.getCoverPicture())
                .username(user.getUsername())
                .biography(user.getBiography())
                .followers(user.getFollowers())
                .followings(user.getFollowings())
                .savedPosts(user.getSavedPost().stream().map(post -> PostResponse.builder()
                        .id(post.getId())
                        .caption(post.getCaption())
                        .image(post.getImage())
                        .video(post.getVideo())
                        .userId(post.getUser().getId())
                        .createdAt(post.getCreatedAt())
                        .build()).toList())
                .build();
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found with the given email!", HttpStatus.NOT_FOUND));

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .profilePicture(user.getProfilePicture())
                .coverPicture(user.getCoverPicture())
                .username(user.getUsername())
                .biography(user.getBiography())
                .followers(user.getFollowers())
                .followings(user.getFollowings())
                .savedPosts(user.getSavedPost().stream().map(post -> PostResponse.builder()
                        .id(post.getId())
                        .caption(post.getCaption())
                        .image(post.getImage())
                        .video(post.getVideo())
                        .userId(post.getUser().getId())
                        .createdAt(post.getCreatedAt())
                        .build()).toList())
                .build();
    }

    @Transactional
    @Override
    public String followUser(long reqUserId, long followUserId) { //TODO CHECK IF THE USER ALREADY FOLLOWING ? REMOVE : ADD

        User reqUser = userRepository.findById(reqUserId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found with the given id", HttpStatus.NOT_FOUND));
         User followUser = userRepository.findById(followUserId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found with the given id", HttpStatus.NOT_FOUND));

        if(reqUser.getFollowings().contains(followUser.getId())){
            reqUser.getFollowings().remove(followUser.getId());
            followUser.getFollowers().remove(reqUser.getId());
            userRepository.save(reqUser);
            userRepository.save(followUser);
            return "User successfully unfollowed!";
        } else {
            reqUser.getFollowings().add(followUser.getId());
            followUser.getFollowers().add(reqUser.getId());
            userRepository.save(reqUser);
            userRepository.save(followUser);
            return "User successfully followed!";
        }
    }

    @Override
    public List<UserResponse> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);

        return users.stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePicture(user.getProfilePicture())
                .username(user.getUsername())
                .build()).toList();
    }

    @Transactional
    @Override
    public UserResponse updateUser(UserUpdateDto userUpdateDto, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found with the given id", HttpStatus.NOT_FOUND));
        if(userUpdateDto.getFirstName() != null && !userUpdateDto.getFirstName().isEmpty()) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if(userUpdateDto.getLastName() != null && !userUpdateDto.getLastName().isEmpty()) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if(userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isEmpty()) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if(userUpdateDto.getProfilePicture() != null && !userUpdateDto.getProfilePicture().isEmpty()) {
            user.setProfilePicture(userUpdateDto.getProfilePicture());
        }
        if(userUpdateDto.getCoverPicture() != null && !userUpdateDto.getCoverPicture().isEmpty()) {
            user.setCoverPicture(userUpdateDto.getCoverPicture());
        }
        if(userUpdateDto.getBiography() != null && !userUpdateDto.getBiography().isEmpty()) {
            user.setBiography(userUpdateDto.getBiography());
        }
        User updated = userRepository.save(user);
        System.out.println(updated.getFirstName());
        System.out.println(updated.getLastName());

        return UserResponse.builder()
                .id(updated.getId())
                .firstName(updated.getFirstName())
                .lastName(updated.getLastName())
                .email(updated.getEmail())
                .gender(updated.getGender())
                .profilePicture(updated.getProfilePicture())
                .coverPicture(updated.getCoverPicture())
                .username(updated.getUsername())
                .biography(updated.getBiography())
                .followers(updated.getFollowers())
                .followings(updated.getFollowings())
                .build();
    }
}