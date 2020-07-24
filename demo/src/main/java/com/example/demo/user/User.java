package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password"})
@NoArgsConstructor
//@JsonFilter("UserInfo")
@ApiModel(description="사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {
    // Entity, Id 어노테이션만 작성해준다면 JPA에서 이 클래스의 정보를
    // 데이터베이스 테이블로 생성해준다.


    // PK 컬럼으로 지정.
    // 값이 자동으로 생성될 수 있도록 @GeneratedValue 추가.
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes="사용자 이름을 입력해 주세요.")
    private String name;



    // 추가할 회원데이터의 가입일이 미래를 가리킬 수 없도록 해줌.
   @Past
   @ApiModelProperty(notes="사용자 등록일을 입력해 주세요.")
    private Date joinDate;

    // 해당 데이터값을 무시하도록 요청하는 어노테이션.
//    @JsonIgnore
    @ApiModelProperty(notes="사용자 비밀번호를 입력해 주세요.")
    private String password; //비밀번호
//    @JsonIgnore

    @ApiModelProperty(notes="사용자 주민번호를 입력해 주세요.")
    private String ssn; //주민등록번호
}
