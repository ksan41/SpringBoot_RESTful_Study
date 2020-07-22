package com.example.demo.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// 컨트롤러로 사용할 클래스
// 컨트롤 빈.

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    // 생성자로 의존성 주입
    public AdminUserController(UserDaoService service){

        this.service = service;
    }


    // 전체 사용자를 조회하는 서비스 요청
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){

        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password");

        // 위 필터를 사용가능한 형식으로 바꿔줄 것임.
        // User에 지정해둔 필터를 선택. 이 필터를 사용해 위와 같은 내용을 적용할 것.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    //사용자 한명 반환하는 서비스 요청(상세조회)
    // GET /admin/users/1  -> /admin/v1/users/1 버전을 명시해줌.
    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "/users/{id}/", params="version=1")
    //@GetMapping(value="/users/{id}", headers="X-API-VERSION=1") Header를 이용한 버전관리
    @GetMapping(value= "/users/{id}", produces= "application/vnd.company.appv1+json") //MIME타입을 통한 버전관리
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
                        // String으로 제시하지 않아도 자동으로 int형임을 인식
        User user = service.findOne(id);

        if(user == null){
            // 예외처리, 예외 발생 시 메시지를 지정
            // 예외처리용 클래스를 별도로 생성할 것임.
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","password","ssn");

        // 위 필터를 사용가능한 형식으로 바꿔줄 것임.
        // User에 지정해둔 필터를 선택. 이 필터를 사용해 위와 같은 내용을 적용할 것.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }



    // 새로 추가한 v2버전.
    //사용자 한명 반환하는 서비스 요청(상세조회)
    // GET /admin/users/1  -> /admin/v2/users/1 버전을 명시해줌.
    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/", params="version=2")
    //@GetMapping(value="/users/{id}", headers="X-API-VERSION=2")
    @GetMapping(value= "/users/{id}", produces= "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUseV2(@PathVariable int id){
        // String으로 제시하지 않아도 자동으로 int형임을 인식
        User user = service.findOne(id);

        if(user == null){
            // 예외처리, 예외 발생 시 메시지를 지정
            // 예외처리용 클래스를 별도로 생성할 것임.
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }

        // User -> User2
        // 검색된 값을 UserV2 객체로 옮긴다.
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2); //id, name, joinDate, password, ssn
        userV2.setGrade("VIP"); //V2에 새로 추가된 필드.

        // BeanUtils?
        // 스프링프레임워크에서 제공하는 유틸. Bean들간의 작업들을 도와주는 유틸리티 클래스.
        // 가장 대표적으로 인스턴스를 만들수있고,
        // 메소드를 카피할 수 있음.(공통 필드 프로퍼티가 있을 경우 해당 값을 카피 가능.)

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        // 위 필터를 사용가능한 형식으로 바꿔줄 것임.
        // User에 지정해둔 필터를 선택. 이 필터를 사용해 위와 같은 내용을 적용할 것.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }


}
