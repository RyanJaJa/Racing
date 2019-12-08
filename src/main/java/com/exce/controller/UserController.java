package com.exce.controller;

import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.dto.ResponsePayload;
import com.exce.model.User;
import com.exce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponsePayload<List<User>> getUsers() {
        logger.info("getUsers request ");
        List<User> users;
        try {
            users = userService.findAll();
            return new ResponsePayload<>(users, HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("getUsers error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getUsers error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }

    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponsePayload<User> getUserInfo() {
        logger.info("getUserInfo request ");

        User user;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new ResponsePayload<>(user, HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("me error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("me error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }
}
