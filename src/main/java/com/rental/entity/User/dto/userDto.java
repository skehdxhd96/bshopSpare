package com.rental.entity.User.dto;

import lombok.*;

public class userDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class create {
        private String name;
        private String email;
        private String nickname;
        private String password;
        private String address;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class login {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class token {
        private String token;
    }
}

