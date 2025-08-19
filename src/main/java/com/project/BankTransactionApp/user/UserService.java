package com.project.BankTransactionApp.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public void registerUser(UserEntity userEntity){

        userRepository.save(userEntity);
    }
}
