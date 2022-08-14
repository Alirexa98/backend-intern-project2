package com.example.backendinternproject2.entity;

import com.example.backendinternproject2.xml.XmlPostModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionEntity {

  @Id
  private Integer id;
  @Column(length = Integer.MAX_VALUE)
  private String title;
  @Column(length = Integer.MAX_VALUE)
  private String text;
  private Integer score;
  @Column(length = Integer.MAX_VALUE)
  private String tags;
  @Column(name = "creation_date")
  private LocalDateTime creationDate;
  @Column(name = "best_answer_id")
  private Integer bestAnswerId;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
  private List<CommentEntity> comments;

  public static QuestionEntity of(XmlPostModel postModel) {
    return new QuestionEntity(
        postModel.getId(),
        postModel.getTitle(),
        postModel.getBody(),
        postModel.getScore(),
        postModel.getTags(),
        postModel.getCreationDate(),
        postModel.getAcceptedAnswerId(),
        null
    );
  }
}
