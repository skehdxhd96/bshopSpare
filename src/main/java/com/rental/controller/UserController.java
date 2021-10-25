package com.rental.controller;

import com.rental.entity.User.User;
import com.rental.entity.User.dto.userDto;
import com.rental.jwt.JwtFIlter;
import com.rental.jwt.TokenProvider;
import com.rental.security.CustomUserDetails;
import com.rental.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/api/user/create")
    public ResponseEntity<User> signup(@Valid @RequestBody userDto.create userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/api/user/anyone")
    public User getMyUserInfo(@AuthenticationPrincipal UserDetails currentUser) {
        return userService.getMyInfo(currentUser);
    }

    @PostMapping("/api/login")
    public ResponseEntity<userDto.token> authorize(@Valid @RequestBody userDto.login loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFIlter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new userDto.token(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("api/user/test")
    public void testAPI() {

    }
}
