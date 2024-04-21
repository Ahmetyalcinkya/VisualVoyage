package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.LoginRequest;
import com.vv.VisualVoyage.dtos.requests.UserSaveDto;
import com.vv.VisualVoyage.dtos.responses.LoginResponse;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
        if(isExist.isPresent()) throw new RuntimeException("This email already used with another account!"); //TODO Throw exception!

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

    private Authentication authenticate(String email, String password){
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

        if(userDetails == null) throw new BadCredentialsException("Invalid username!");
        if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Wrong password!");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
