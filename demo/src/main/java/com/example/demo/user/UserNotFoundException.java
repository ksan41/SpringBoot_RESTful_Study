package com.example.demo.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status Code 를 통해 요청처리의 결과 상태를 확인.
// 2xx -> OK (정상처리)
// 4xx -> Client (클라이언트의 잘못)
// 5xx -> Server (프로그램, 서버리소스 연결 문제 등 서버의 문제.)

// @ResponseStatus 어노테이션 붙여줌. (여러 메소드를 사용가능)
// 앞으로 이 클래스를 이용해 예외처리 했을 때, 500이 아닌, 404 오류로 클라이언트에게 전달함.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        // 부모클래스에서 전달된 메시지를 반환.
        super(message);
    }
}
