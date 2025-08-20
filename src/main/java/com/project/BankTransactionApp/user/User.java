package com.project.BankTransactionApp.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.BankTransactionApp.account.Account;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
