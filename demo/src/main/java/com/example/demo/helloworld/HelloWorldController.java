package com.example.demo.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    // 어노테이션을 통한 의존성 주입.
    // 현재 스프링프레임워크에 설정되어있는 빈들 중, 같은 타입을 갖고있는
    // 빈을 자동으로 이쪽으로 주입하게 됨.
    @Autowired
    private MessageSource messageSource;

    // GET
    // /hello-world (endpoint) 클라이언트에서 요청한 정보를 서버측에서 처리,
    //                         응답메시지 생성 - 다시 클라이언트에 전달.
    // @RequestMapping(method=RequestMethod.GET, path="hello-world")
    //   <<예전방식
    // 최근엔 GetMapping을 통해 어떤 메소드를 사용할 것인지 바로 지정할 수 있음.
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    // alt + enter
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
        // String.format(문자열 포맷, 문자열)
    }

    // 다국어 처리를 위한 메소드
    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required = false) Locale locale){
        // Accept-Language 값이 없을경우(false) 자동적으로 설정했던 디폴트값(한국어)로 인식하도록 설정.

        return messageSource.getMessage("greeting.message",null,locale);
    }
}
