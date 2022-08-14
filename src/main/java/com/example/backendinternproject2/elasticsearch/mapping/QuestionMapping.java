package com.example.backendinternproject2.elasticsearch.mapping;

import com.example.backendinternproject2.entity.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(indexName = "question")
public class QuestionMapping {
  @Id
  private Integer id;
  @Field(type = FieldType.Text)
  private String title;
  @Field(type = FieldType.Text)
  private String text;
  private Integer score;
  @Field(type = FieldType.Text)
  private String tags;
  @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime creationDate;

  public static QuestionMapping of(QuestionEntity questionEntity) {
    return new QuestionMapping(
        questionEntity.getId(),
        questionEntity.getTitle(),
        questionEntity.getText(),
        questionEntity.getScore(),
        questionEntity.getTags(),
        questionEntity.getCreationDate()
    );
  }
}
