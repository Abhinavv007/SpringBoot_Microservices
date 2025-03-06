package com.abhinav.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.model.User;


public interface UserRepository extends JpaRepository<User,Long> {
    
    User findByEmail(String email);
}
