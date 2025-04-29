
package com.milind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milind.config.JwtProvider;
import com.milind.modal.TwoFactorOTP;
import com.milind.modal.User;
import com.milind.repository.UserRepository;
import com.milind.response.AuthResponse;
import com.milind.service.CustomerUserDetailsService;
import com.milind.service.EmailService;
import com.milind.service.TwoFactorOtpService;
import com.milind.utils.OtpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        AuthResponse res = new AuthResponse();
        try {
            User isEmailExist = userRepository.findByEmail(user.getEmail());

            if (isEmailExist != null) {
                res.setStatus(false);
                res.setMessage("Email is already used with another account");
                res.setJwt(null);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setFullName(user.getFullName());

            User savedUser = userRepository.save(newUser);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    savedUser.getEmail(),
                    savedUser.getPassword()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwt = JwtProvider.generateToken(auth);

            res.setJwt(jwt);
            res.setStatus(true);
            res.setMessage("Registration successful");

            return new ResponseEntity<>(res, HttpStatus.CREATED);

        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Something went wrong: " + e.getMessage());
            res.setJwt(null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
        AuthResponse res = new AuthResponse();
        try {
            String userName = user.getEmail();
            String password = user.getPassword();

            Authentication auth = authenticate(userName, password);

            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwt = JwtProvider.generateToken(auth);

            User authUser = userRepository.findByEmail(userName);

            if(user.getTwoFactorAuth().isEnabled()){
                res.setMessage("Two factor auth is enabled");
                res.setTwoFactorAuthEnabled(true);
                String otp=OtpUtils.generateOTP();

                TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.FindByUser(authUser.getId());
                if(oldTwoFactorOTP != null){
                    twoFactorOtpService.deleteTwoFactor0tp(oldTwoFactorOTP);
                }
                TwoFactorOTP newTwoFactorOTP=twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

                emailService.senderVerificationOtpEmail(userName, otp);

                res.setSession(newTwoFactorOTP.getId());
                return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
            }
 
            res.setJwt(jwt);
            res.setStatus(true);
            res.setMessage("Login success");

            return new ResponseEntity<>(res, HttpStatus.OK);

        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Something went wrong: " + e.getMessage());
            res.setJwt(null);
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigningOtp(
        @PathVariable String otp,
        @RequestParam String id){

            TwoFactorOTP twoFactorOTP=twoFactorOtpService.FindById(id); 

            if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP,otp)){ 
                AuthResponse res = new AuthResponse(); 
                res.setMessage("Two factor authentication verified"); 
                res.setTwoFactorAuthEnabled(true); 
                res.setJwt(twoFactorOTP.getJwt()); 
                return new ResponseEntity<>(res, HttpStatus.OK); 
            } 
            throw new Exception("invalid otp");

            
    }
    
}
