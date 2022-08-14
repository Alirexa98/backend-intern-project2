package com.example.backendinternproject2.elasticsearch;

import com.example.backendinternproject2.elasticsearch.mapping.QuestionMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;

public interface ElasticQuestionRepository extends ElasticsearchRepository<QuestionMapping, Integer> {

  @Query(value = "{\n" +
      "    \"bool\": {\n" +
      "      \"should\": [\n" +
      "        {\n" +
      "          \"multi_match\": {\n" +
      "            \"query\": \"?0\",\n" +
      "            \"fields\": [\"title\", \"text\"]\n" +
      "          }\n" +
      "        },\n" +
      "        {\n" +
      "          \"match\": {\n" +
      "            \"tags\": \"?1\"\n" +
      "          }\n" +
      "        }\n" +
      "      ],\n" +
      "      \"filter\": [\n" +
      "        {\n" +
      "          \"range\": {\n" +
      "            \"creationDate\": {\n" +
      "              \"gte\": \"?2\"\n" +
      "            }\n" +
      "          }\n" +
      "        },\n" +
      "        {\n" +
      "          \"range\": {\n" +
      "            \"score\": {\n" +
      "              \"gte\": ?3\n" +
      "            }\n" +
      "          }\n" +
      "        }\n" +
      "      ]\n" +
      "    }\n" +
      "  }")
  Page<QuestionMapping> findQuestions(
      String keywords,
      String tags,
      LocalDateTime fromDate,
      Integer minScore,
      Pageable pageable
  );
}
