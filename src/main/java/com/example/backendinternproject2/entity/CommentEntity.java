package com.example.backendinternproject2.entity;

import com.example.backendinternproject2.xml.XmlCommentModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentEntity {

  @Id
  private Integer id;
  @Column(length = Integer.MAX_VALUE)
  private String text;

  @ManyToOne
  private UserEntity user;
  @ManyToOne
  private QuestionEntity question;

  public static CommentEntity of(XmlCommentModel commentModel, UserEntity user, QuestionEntity question) {
    return new CommentEntity(
        commentModel.getId(),
        commentModel.getText(),
        user,
        question
    );
  }
}
