package com.project.BankTransactionApp.account;

import com.project.BankTransactionApp.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name="accounts")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy = "account",cascade=CascadeType.ALL)
    private List<AccountMapping> accountMappings=new ArrayList<>();


}
