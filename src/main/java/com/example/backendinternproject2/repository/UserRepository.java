package com.example.backendinternproject2.repository;

import com.example.backendinternproject2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
