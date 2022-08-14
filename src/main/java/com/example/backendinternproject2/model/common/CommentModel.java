package com.example.backendinternproject2.model.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {
  @JsonProperty("u_name")
  private String userName;
  @JsonProperty("body")
  private String body;
}