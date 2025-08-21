package com.project.BankTransactionApp.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.BankTransactionApp.account.entity.Account;
import com.project.BankTransactionApp.user.Role;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$",
            message = "Username must be 4-16 characters, alphanumeric or underscore.")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    private String password;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @Column(unique = true, nullable = false)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> accounts=new ArrayList<>();
}
