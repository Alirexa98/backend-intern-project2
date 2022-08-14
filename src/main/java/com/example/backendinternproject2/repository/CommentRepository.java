package com.example.backendinternproject2.repository;

import com.example.backendinternproject2.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
