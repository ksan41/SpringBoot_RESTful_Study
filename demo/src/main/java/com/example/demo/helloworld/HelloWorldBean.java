package com.example.demo.helloworld;

//lombok

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {

    private String message;

    // 기본적으로 만들어야 하는 메소드지만, 롬복을 사용하므로 생략가능해짐.
//    public String getMessage(){
//        return this.message;
//    }
//
//    public void setMessage(String message){
//
//    }

}
