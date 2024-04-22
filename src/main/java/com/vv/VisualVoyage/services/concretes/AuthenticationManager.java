package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.LoginRequest;
import com.vv.VisualVoyage.dtos.requests.UserSaveDto;
import com.vv.VisualVoyage.dtos.responses.LoginResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.exceptions.VisualVoyageExceptions;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthenticationManager implements AuthenticationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    public AuthenticationManager(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomerUserDetailsService customerUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Transactional
    @Override
    public LoginResponse register(UserSaveDto userSaveDto) { //TODO CHECK THE SAVED POSTS FOR ALL RESPONSES AFTER UI COMPLETED!

        Optional<User> isExist = userRepository.findByEmail(userSaveDto.getEmail());
        if(isExist.isPresent()) throw new VisualVoyageExceptions("This email already used with another account!", HttpStatus.BAD_REQUEST);

        String encodedPassword = passwordEncoder.encode(userSaveDto.getPassword());

        User user = User.builder()
                .firstName(userSaveDto.getFirstName())
                .lastName(userSaveDto.getLastName())
                .email(userSaveDto.getEmail())
                .password(encodedPassword)
                .gender(userSaveDto.getGender())
                .followers(new HashSet<>())
                .followings(new HashSet<>())
                .savedPost(new ArrayList<>())
                .build();
        User saved = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(saved.getEmail(), saved.getPassword());
        String token = JwtProvider.generateToken(authentication);

        return new LoginResponse(token, "Register success!");
    }

    @Transactional
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);

        return new LoginResponse(token, "Login success!");
    }
    @Override
    public UserResponse findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwt(jwt);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found with the given email!", HttpStatus.NOT_FOUND));
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .followers(user.getFollowers())
                .followings(user.getFollowings())
                .build();
    }
    private Authentication authenticate(String email, String password){
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

        if(userDetails == null) throw new VisualVoyageExceptions("Invalid username!", HttpStatus.BAD_REQUEST);
        if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new VisualVoyageExceptions("Wrong password!", HttpStatus.BAD_REQUEST);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
