package com.example.demo.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

//사용자 목록조회 등의 로직처리용. 서비스.
//현재로선 DB연동작업 하지 않을 것이기에(dao가 할 일이 없으므로)
//Service + Dao 통합형태로 작성했음

// Service Bean으로 등록.
@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    // 초기 데이터의 개수만큼, 사용자 수 변수 정의
    private static int usersCount = 3;

    // 임의의 샘플데이터 만들기
    static {
        users.add(new User( 1,"Kenneth", new Date(),"pass1","701010-111111"));
        users.add(new User(2,"Alice",new Date(),"pass1","701010-111111"));
        users.add(new User(3,"Elena",new Date(),"pass1","701010-111111"));
    }

    // 사용자 전체 목록 조회
    public List<User> findAll(){
        return users;
    }

    // 사용자를 추가
    public User save(User user){
        // 기본 키인 id값이 존재하지 않을 때
        // id값을 셋팅해줌.
        if(user.getId() == null){
            // 기존 사용자 수에 1 추가
            user.setId(++usersCount);
        }

        // 기존 데이터(List)에 새로 추가
        users.add(user);
        return user;
    }

    //하나의 사용자 데이터 조회
    public User findOne(int id){
        for(User user:users){
            // 매개변수로 전달된 id값이 현재 id값과 일치하다면
            // 유저정보 반환하도록 함.
            if(user.getId() == id){
                return user;
            }
        }
        // 반복문을 전부 돌 동안 일치하는 id값이 없다면 null반환
        return null;
    }

    // 입력한 아이디에 해당하는 유저를 삭제하는 메소드.
    public User deleteById(int id){
        // 배열이나 리스트 형태의 객체에 순차적으로 접근할 때 사용
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();

            // 등록된 유저 정보에 하나하나 접근, 아이디가 일치하는 객체를 찾는다.
            if(user.getId() == id){
                // 일치하는 유저 정보를 삭제.
                iterator.remove();
                // 삭제된 유저 정보를 반환.
                return user;
            }
        }

        // 일치하는 유저 정보가 없었을 경우, null 반환하도록 함.
        return null;
    }
}
