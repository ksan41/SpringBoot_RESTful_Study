package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

// 데이터베이스에 관련된 형태의 빈.
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // JpaRepository 클래스를 상속받아야 하며, 타입지정이 필요.
    // User 클래스를 사용하고, 그 PK인 id의 자료형인 Integer를 지정해 주었다.


}
