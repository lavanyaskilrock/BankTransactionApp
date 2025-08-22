package com.project.BankTransactionApp.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.BankTransactionApp.account.entity.Account;
import com.project.BankTransactionApp.user.enums.Role;
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

    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    private String password;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String mobileNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> accounts=new ArrayList<>();
}
