package com.rental.service.user;

import com.rental.entity.User.Role;
import com.rental.entity.User.User;
import com.rental.entity.User.dto.userDto;
import com.rental.exception.DuplicateMemberException;
import com.rental.repository.user.UserRepository;
import com.rental.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(userDto.create userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .role(Role.BUYER)
                .build();

        return userRepository.save(user);
    }

    public Optional<User> getUserWithAuthorities(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getMyUserWithAuthorities() { // 현재 로그인 한 유저
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);
    }

    public User getMyInfo(UserDetails currentUser) {

        return userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
    }
}