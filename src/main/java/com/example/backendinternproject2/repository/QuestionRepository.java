package com.example.backendinternproject2.repository;

import com.example.backendinternproject2.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
  @Query("select q from QuestionEntity q where q.creationDate > :fromDate and q.score > :minScore and (q.title like %:keywords% or q.text like %:keywords%) and q.tags like %:tags%")
  Page<QuestionEntity> searchQuestions(
      String keywords,
      String tags,
      LocalDateTime fromDate,
      Integer minScore,
      Pageable pageable
  );
}
