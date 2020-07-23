package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/jpa")
public class UserJpaController {
    // 의존성 주입
    @Autowired
    private UserRepository userRepository;

    // http://localhost:8088/jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        // findAll()? UserRepository가 상속받고있는 JpaRepository에 정의되어있는 메소드.
        return userRepository.findAll();
    }
}
