package com.example.users.repositories;

import com.example.coreapi.users.queries.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserInfo,String> {


    public Optional<UserInfo> findByUsername(String username);

    public Optional<UserInfo> findByEmail(String email);

}
