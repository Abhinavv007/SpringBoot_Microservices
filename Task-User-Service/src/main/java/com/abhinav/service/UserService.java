package com.abhinav.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.config.JwtProvider;
import com.abhinav.model.User;
import com.abhinav.repository.UserRepository;

@Service
public class UserService {

    @Autowired
     private UserRepository userRepository;

     public User getUserProfile(String jwt){
        String email = JwtProvider.getEmailFromToken(jwt);
        return userRepository.findByEmail(email);
     }

     public List<User> getAllUsers(){
         return userRepository.findAll();
     }
}
