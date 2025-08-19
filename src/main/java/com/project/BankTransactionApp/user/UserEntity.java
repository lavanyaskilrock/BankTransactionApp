package com.project.BankTransactionApp.user;

import com.project.BankTransactionApp.common.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="users")
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String mobile;
    private Role role;
}
