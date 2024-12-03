package com.example.tpchuang.restfulwebservices.jpa;

import com.example.tpchuang.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
