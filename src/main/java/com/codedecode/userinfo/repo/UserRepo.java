package com.codedecode.userinfo.repo;

import com.codedecode.userinfo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String userName);
}
