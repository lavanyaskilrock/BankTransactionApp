package com.project.BankTransactionApp.user.service;

import com.project.BankTransactionApp.common.exception.InvalidInputException;
import com.project.BankTransactionApp.common.exception.UserNotFoundException;
import com.project.BankTransactionApp.user.entity.User;
import com.project.BankTransactionApp.user.enums.Role;
import com.project.BankTransactionApp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private static final String USERNAME_REGEX = "^[A-Za-z0-9_]{3,20}$";
    private static final String MOBILE_REGEX = "^[7-9][0-9]{9}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    private static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_REGEX);
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        validateUserForRegistration(user);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new InvalidInputException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new InvalidInputException("Email already exists: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User getUserByUsername(String username) {
        validateUsername(username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    private void validateUserForRegistration(User user) {
        if (user == null) {
            throw new InvalidInputException("User cannot be null");
        }

        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateMobile(user.getMobileNumber());
        validateRole(user.getRole().toString());

    }
    private void validateMobile(String mobileNumber){
        if (!StringUtils.hasText(mobileNumber)) {
            throw new InvalidInputException("Mobile number cannot be null or empty");
        }
        if (!MOBILE_PATTERN.matcher(mobileNumber).matches()){
            throw new InvalidInputException("Mobile number must start from 9-7 and must be 10 digits long");
        }
    }

    private void validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new InvalidInputException("Username cannot be null or empty");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new InvalidInputException("Username must be 3-20 characters long and contain only letters, numbers, and underscores");
        }
    }
    private void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new InvalidInputException("Email cannot be null or empty");
        }
        if (email.length() > 254) {
            throw new InvalidInputException("Email is too long (maximum 254 characters)");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidInputException("Invalid email format: " + email);
        }
    }
    private void validateRole(String role){
        try{
            Role.valueOf(role.toUpperCase());
        }catch(Exception e){
            throw new InvalidInputException("Invalid role");
        }
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new InvalidInputException("Password cannot be null or empty");
        }
        if (password.length() < 8) {
            throw new InvalidInputException("Password must be at least 8 characters long");
        }
        if (password.length() > 128) {
            throw new InvalidInputException("Password is too long (maximum 128 characters)");
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidInputException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&)");
        }
    }

    private void validateName(String name, String fieldName) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidInputException(fieldName + " cannot be null or empty");
        }
        if (name.length() > 50) {
            throw new InvalidInputException(fieldName + " is too long (maximum 50 characters)");
        }
        if (!name.matches("^[A-Za-z\\s'-]{1,50}$")) {
            throw new InvalidInputException(fieldName + " can only contain letters, spaces, hyphens, and apostrophes");
        }
    }
}