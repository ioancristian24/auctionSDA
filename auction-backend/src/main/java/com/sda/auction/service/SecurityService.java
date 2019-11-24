package com.sda.auction.service;

import com.sda.auction.dto.LoginDto;
import com.sda.auction.model.User;

import javax.servlet.ServletRequest;

public interface SecurityService {

    boolean passwordMatch(LoginDto loginDto, User user);

    LoginDto createDtoWithJwt(User user);

    boolean isValid(ServletRequest servletRequest);

    void setEmailOn(ServletRequest servletRequest);
}
