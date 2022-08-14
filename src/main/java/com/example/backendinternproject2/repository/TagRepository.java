package com.example.backendinternproject2.repository;

import com.example.backendinternproject2.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {

  @Query("select t from TagEntity t where t.name = ?1 ")
  Optional<TagEntity> getTagByName(String tagName);

}
