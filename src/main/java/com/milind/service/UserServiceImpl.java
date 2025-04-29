package com.milind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.milind.config.JwtProvider;
import com.milind.domain.VerificationType;
import com.milind.modal.User;
import com.milind.repository.UserRepository;

@RestController
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("user not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("user not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new Exception("user not found");
        }
        return user.get(); 
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo,User user){
            TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
            twoFactorAuth.setEnabled(true);
            twoFactorAuth.setSendTo(verificationType);
            
            user.setTwoFactorAuth(twoFactorAuth);

            return userRepository.save(user);
        }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
    
}
