package com.exce.controller;

import com.exce.dto.JwtAuthenticationResponse;
import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.dto.ResponsePayload;
import com.exce.model.User;
import com.exce.service.AuthService;
import com.exce.service.UserService;
import com.exce.util.JwtTokenUtil;
import com.exce.validation.AuthRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new AuthRequestValidator());
    }

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponsePayload<String> createAuthenticationToken(@Valid @RequestBody User authenticationRequest, Device device) throws AuthenticationException {
        logger.info("createAuthenticationToken request {} , {} ", authenticationRequest, device);
        try {
            // Perform the security
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-security so we can generate token
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String token = jwtTokenUtil.generateToken(userDetails, device);
            return new ResponsePayload<>(token, HttpStatus.OK);

        } catch (GoldLuckException e) {
            logger.error("createAuthenticationToken error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("createAuthenticationToken error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }

    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponsePayload<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        logger.info("refreshAndGetAuthenticationToken request {} ", request.getHeader(tokenHeader));
        try {
            String token = request.getHeader(tokenHeader);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User user = (User) userService.loadUserByUsername(username);

            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetTime().getTime())) {
                String refreshedToken = jwtTokenUtil.refreshToken(token);
                return new ResponsePayload<>(new JwtAuthenticationResponse(refreshedToken), HttpStatus.OK);
            } else {
                return new ResponsePayload<>(HttpStatus.NOT_FOUND);
            }

        } catch (GoldLuckException e) {
            logger.error("refreshAndGetAuthenticationToken error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("refreshAndGetAuthenticationToken error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public @ResponseBody
    ResponsePayload<User> register(@Valid @RequestBody User addedUser) {
        logger.info("register request {}", addedUser);

        try {
            return new ResponsePayload<>(authService.register(addedUser), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("register error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("register error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }

    }

}
