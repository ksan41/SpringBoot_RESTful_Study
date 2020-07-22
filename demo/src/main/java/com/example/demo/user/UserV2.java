package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password"})
@NoArgsConstructor
@JsonFilter("UserInfoV2")
public class UserV2 extends User{ // 상속 받아 사용할 경우, 부모클래스의 기본생성자가 있어야함.
   private String grade;
}
