package com.sda.auction.controller;

import com.sda.auction.dto.UserDto;
import com.sda.auction.model.User;
import com.sda.auction.service.UserService;
import com.sda.auction.validator.UserDtoValidator;
import javax.jws.soap.SOAPBinding.Use;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class Registration {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDtoValidator userDtoValidator;

    @GetMapping
    public ResponseEntity<String> get() {
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> post(@Valid @RequestBody UserDto userDto) {

        userDtoValidator.validate(userDto);

        UserDto userDtoResult = userService.addUser(userDto);

        return new ResponseEntity<>(userDtoResult, HttpStatus.OK);
    }

}
