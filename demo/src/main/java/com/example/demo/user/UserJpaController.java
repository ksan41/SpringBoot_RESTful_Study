package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/jpa")
public class UserJpaController {
    // 의존성 주입
    @Autowired
    private UserRepository userRepository;

    // 유저정보 전체조회
    // http://localhost:8088/jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        // findAll()? UserRepository가 상속받고있는 JpaRepository에 정의되어있는 메소드.
        return userRepository.findAll();
    }

    // 유저정보 개별조회
    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        //findById 의 반환값이 Optional<T>로 선언되어 있다.
        //이유? 데이터가 검색어에 따라 존재할 수도, 존재하지 않을 수도 있기 때문에
        //     선택적인 데이터값을 반환시켜 주는 것.

        if(!user.isPresent()){ // 유저 정보가 존재하지 않을 때
            throw new UserNotFoundException(String.format("ID{%s} not found",id));
        }

        // User 객체를 HATEOAS에서 전달할 수 있는 Resource 형태로 만든다.
        Resource<User> resource = new Resource<>(user.get());
        // link기능을 사용해 이 메소드가 실행됐을 때 retrieveAllUsers메소드도 같이 실행되도록 한다.
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }
}
