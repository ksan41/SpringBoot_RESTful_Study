package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


// 게시글용 엔티티
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // @JsonIgnore : 외부에 데이터를 노출하지 않도록하는 어노테이션.
    // User : Post -> 1: (0~N) (1 대 다의 속성을 가진다.)
    // User - Main데이터, Post - Sub데이터.
    // => 이런 관계를 주 테이블(기본)과 참조테이블. 즉, 부모: 자식 관계라고 한다.
    // 위와 같은 일대 다의 관계를 설정하기 위해 @ManyToOne추가.
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user; // 게시글을 작성한 사용자 정보용 필드


}
