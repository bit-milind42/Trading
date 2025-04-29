package com.milind.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.milind.domain.VerificationType;
import com.milind.modal.ForgotPasswordToken;
import com.milind.modal.User;
import com.milind.modal.VerificationCode;
import com.milind.request.ForgotPasswordTokenRequest;
import com.milind.request.ResetPasswordRequest;
import com.milind.response.ApiResponse;
import com.milind.response.AuthResponse;
import com.milind.service.EmailService;
import com.milind.service.ForgotPasswordService;
import com.milind.service.UserService;
import com.milind.service.VerificationCodeService;
import com.milind.utils.OtpUtils;

import io.jsonwebtoken.security.Request;

@RestController
public class UserController {
     
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;
    private String jwt;

    @AutoController
    private VerificationCodeService VerificationCodeService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
        @PathVariable VerificationType verificationType) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCodeService verificationCodeService =verificationCodeService.getVerificationCodeByUser(user.getId());

        if(verificationCode == null){
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if(verificationType.equals(VerificationType.Email)){
            emailService.senderVerificationOtpEmail(user.getEmail(),verificationCode.getOtp());
        }

        return new ResponseEntity<>("verification otp sent successfully", HttpStatus.OK);
    } 

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
        @PathVariable String otp,
        @RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode= VerificationCodeService.getVerificationCodeByUser(userId);

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
        verificationCode.getEmail():verificationCode.getMobile();
            
        boolean isVerified = verificationCode.getOtp().equals(otp);
        
        if (isVerified) { 
            User updatedUser=userService.enableTwoFactor Authentication( 
                verificationCode.getVerificationType(), sendTo, user); 
                
            verificationCodeService.deleteVerificationCodeById(verificationCode); 
            return new ResponseEntity<>(updatedUser, HttpStatus.OK); 
        } 
        throw new Exception("wrong otp");

    }


    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest req ) throws Exception{

            User user= userService.findUserByEmail(req.getSendTo()); 
            String otp =OtpUtils.generateOTP(); 
            UUID uuid=UUID.randomUUID(); 
            String id=uuid.toString(); 

            ForgotPasswordToken token=forgotPasswordService.findByUser(user.getId()); 

            if(token==null){ 
                token=forgotPasswordService.createToken(user,id,otp,req.getVerificationType(),req.getSendTo()); 
            }

            if(req.getVerificationType().equals(VerificationType.EMAIL)) { 
                emailService.sendVerificationOtpEmail( 
                user.getEmail(), 
                token.getOtp()); 
            } 
            AuthResponse response = new AuthResponse(); 
            response.setSession(token.getId()); 
            response.setMessage("Password reset otp sent successfully");

        
        return new ResponseEntity<>("forgot password otp sent successfully", HttpStatus.OK);
    } 



    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
        @RequestParam String id,
        @RequestBody ResetPasswordRequest req,
        @RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);

        ForgotPasswordToken forgotPasswordToken= forgotPasswordService.findById(id);

        boolean isVerified =forgotPasswordToken.getOtp().equals(req.getOtp());

        if(isVerified){ 
            UserService.updatePassword(forgotPasswordToken.getUser(), req.getPassword()); 
            ApiResponse res=new ApiResponse(); 
            res.setMessage("password update successfully"); 
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED); 
        } 
        throw new Exception("wrong otp");

    }
     
}