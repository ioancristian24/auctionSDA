package com.sda.auction.service.impl;

import com.sda.auction.dto.UserDto;
import com.sda.auction.mapper.UserMapper;
import com.sda.auction.model.User;
import com.sda.auction.repository.UserRepository;
import com.sda.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        //convertim dto in entity
        User user = userMapper.convert(userDto);

        encodePassword(user);

        //persistam in baza de date
        User savedUser = userRepository.save(user);

        //convertim entitatea persistata inapoi in dto pentru a o intoarce catre requester
        return userMapper.convert(savedUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void encodePassword(User user) {
        //encode user's password and put it in passwordEncoded varioble
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        //set the encoded password to the user entity
        user.setPassword(passwordEncoded);
    }
}
