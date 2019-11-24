package com.sda.auction.validator;

import com.sda.auction.dto.UserDto;
import com.sda.auction.model.User;
import com.sda.auction.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoValidator {

    @Autowired
    private UserService userService;

    public boolean validate(UserDto userDto) {
        if (passwordsDontMatch(userDto)) {
            throw new RuntimeException("Passwords do not match!");
        }

        if (emailAlreadyRegistered(userDto.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        return true;
    }

    private boolean passwordsDontMatch(UserDto userDto) {
        return userDto.getPassword().compareTo(userDto.getConfirmPassword()) != 0;
    }

    private boolean emailAlreadyRegistered(String email) {
        User user = userService.findByEmail(email);
        return user != null;
    }

}
