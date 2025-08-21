package com.project.BankTransactionApp.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.BankTransactionApp.user.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "account",cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<AccountMapping> accountMappings=new ArrayList<>();


}
