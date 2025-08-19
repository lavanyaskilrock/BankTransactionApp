package com.project.BankTransactionApp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.BankTransactionApp.user.UserEntity;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

}
