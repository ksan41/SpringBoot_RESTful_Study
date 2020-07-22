package com.example.demo.exception;

import com.example.demo.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// ControllerAdvice? 모든 컨트롤러가 실행될때, 이 어노테이션을 가지고 있는 빈이
// 실행되게 됨.
// 모든 예외가 발생했을 때, 이 클래스를 참조하도록 지정할 것임.
@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 예외 객체, 어디서 발생한 예외인지를 매개변수로 받아올 것.
    //
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 유저 정보가 없을때 실행시킬 메소드.
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // 부모클래스의 메소드 오버라이딩
    // 어노테이션을 생략해도 좋지만, 오버라이드 어노테이션을 추가했을 경우엔
    // 잘못된 메소드를 입력했을 경우, 오류를 잡아주므로
    // 오버라이딩 시엔 항상 @Override 어노테이션을 붙여주는 습관을 들이자.
    // 잘못된 값이 들어왔을 경우 발생시킬 예외 메소드.
   @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        // **메소드의 매개변수 살펴보기
        // 1 : 예외 객체
        // 2: 예외의 header값
        //3 : status 값
        //4 : 요청 request 값


        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "Validation Failed",ex.getBindingResult().toString());


        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
