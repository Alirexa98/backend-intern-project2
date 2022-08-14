package com.example.backendinternproject2.model.common;

import com.example.backendinternproject2.entity.AnswerEntity;
import com.example.backendinternproject2.entity.QuestionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class PostModel {
  @JsonProperty("orig_post_id")
  private Integer postId;
  @JsonProperty("orig_post_title")
  private String postTitle;
  @JsonProperty("orig_post_body")
  private String postBody;
  @JsonProperty("orig_post_score")
  private Integer postScore;
  @JsonProperty("orig_post_comments")
  private List<CommentModel> postComments;
  @JsonProperty("best_ans_id")
  private Integer bestAnswerId;
  @JsonProperty("best_ans_body")
  private String bestAnswerBody;
  @JsonProperty("best_ans_score")
  private Integer bestAnswerScore;

  public static PostModel of(QuestionEntity questionEntity, AnswerEntity bestAnswer) {
    return new PostModel(
        questionEntity.getId(),
        questionEntity.getTitle(),
        questionEntity.getText(),
        questionEntity.getScore(),
        questionEntity.getComments().stream().map( (item) ->
            new CommentModel(
                item.getUser().getName(),
                item.getText()
            )
        ).collect(Collectors.toList()),
        bestAnswer.getId(),
        bestAnswer.getText(),
        bestAnswer.getScore()
    );
  }
  public static PostModel of(QuestionEntity questionEntity) {
    return new PostModel(
        questionEntity.getId(),
        questionEntity.getTitle(),
        questionEntity.getText(),
        questionEntity.getScore(),
        questionEntity.getComments().stream().map( (item) ->
            new CommentModel(
                item.getUser().getName(),
                item.getText()
            )
        ).collect(Collectors.toList()),
        null,
        null,
        null
    );
  }
}
