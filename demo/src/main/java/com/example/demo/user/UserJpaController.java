package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @Autowired
    private PostRepository postRepository;

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

    // 유저정보 삭제용
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    // 사용자 생성용
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        // 새 유저 생성
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // /jpa/users/900001/posts
    // 사용자가 작성한 전체 게시글을 조회
    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        // 우선은 해당 사용자가 존재하는지에 대한 조건검사를 한다.

        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){ // 유저 정보가 존재하지 않을 때
            throw new UserNotFoundException(String.format("ID{%s} not found",id));
        }

        // 유저 데이터가 존재할 경우에만 getPosts()메소드가 실행되도록 한다.
        return user.get().getPosts();
    }


    // 게시글 생성용
    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){
        // 새 게시글 생성
        // userId가 같이 포함되어야 하기 때문에 사용자 정보를 검색해 id값을 지정해야 함.
        // 우선은 해당 사용자가 존재하는지에 대한 조건검사를 한다.
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){ // 유저 정보가 존재하지 않을 때
            throw new UserNotFoundException(String.format("ID{%s} not found",id));
        }

        // 검색해 온 사용자 정보를 post객체에 저장.
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
