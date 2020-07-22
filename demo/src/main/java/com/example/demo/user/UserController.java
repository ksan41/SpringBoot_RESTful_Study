package com.example.demo.user;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

// 컨트롤러로 사용할 클래스
// 컨트롤 빈.

@RestController
public class UserController {

    private UserDaoService service;

    // 생성자로 의존성 주입
    public UserController(UserDaoService service){
        this.service = service;
    }


    // 전체 사용자를 조회하는 서비스 요청
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //사용자 한명 반환하는 서비스 요청(상세조회)
    // GET / users/1 or users/10 -> String 형태로 전달됨.
    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id){
                        // String으로 제시하지 않아도 자동으로 int형임을 인식
        User user = service.findOne(id);

        if(user == null){
            // 예외처리, 예외 발생 시 메시지를 지정
            // 예외처리용 클래스를 별도로 생성할 것임.
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        // HATEOAS
        Resource<User> resource = new Resource<>(user);
//        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
//                ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));
        // => 지정한 메소드들을 '연결'시켜준 것.
        //    이 메소드를 실행한다면 'retrieveAllUsers()도 같이 실행되게 된다는 것.

        return resource;
    }

    // 사용자를 추가하기 위한 메소드 (POST)
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        // RrequestBody?
        // 전달받으려는 데이터가 Json이나 xml등의 형식임을 명시

        //ResponseEntity?
        //HttpEntity를 상속받음으로써 HttpHeader와 body를 가질 수 있기 때문에
        //그런 정보를 담고자 할 때 사용함.


        User savedUser = service.save(user);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // 사용자 삭제요청
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        // 입력한 아이디에 해당하는 유저 정보를 바로 삭제.
        User user = service.deleteById(id);

        // 삭제된 데이터가 존재하지 않을 경우
        if(user == null){
            //만들어둔 예외처리 클래스 실행. 메시지 전달
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }
}
