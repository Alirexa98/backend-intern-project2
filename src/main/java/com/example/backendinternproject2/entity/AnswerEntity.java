package com.example.backendinternproject2.entity;

import com.example.backendinternproject2.xml.XmlPostModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "answer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerEntity {

  @Id
  private Integer id;
  @Column(length = Integer.MAX_VALUE)
  private String title;
  @Column(length = Integer.MAX_VALUE)
  private String text;
  private Integer score;

  public static AnswerEntity of(XmlPostModel postModel) {
    return new AnswerEntity(
        postModel.getId(),
        postModel.getTitle(),
        postModel.getBody(),
        postModel.getScore()
    );
  }
}
