package com.rental.entity.User;

import com.rental.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    @NotNull
    private String password;
    @NotNull
    private String nickname; // 닉네임
    @NotNull
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;
    private String name; // 실제 이름
    private String userIntro;

    public void changeRole() {
        this.role = (this.role == Role.BUYER) ? Role.SELLER : Role.BUYER;
    }
}
