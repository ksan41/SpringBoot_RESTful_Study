package com.example.demo.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 현재 프로젝트 내에서 예외 발생 시, 이 클래스를 참조할 수 있도록 할것.
// 'AOP' 클래스에서 공통적으로 실행되어야 하는 로직이나, 처리해줘야 될 메소드를
// 추가할 때 사용하게 될것.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
