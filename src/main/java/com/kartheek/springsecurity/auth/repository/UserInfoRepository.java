package com.kartheek.springsecurity.auth.repository;

import com.kartheek.springsecurity.auth.entity.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<RegisterUser, Long> {
    Optional<RegisterUser> findByUserId(int userName);
}
