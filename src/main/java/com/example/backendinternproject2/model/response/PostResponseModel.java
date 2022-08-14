package com.example.backendinternproject2.model.response;

import com.example.backendinternproject2.model.common.PostModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponseModel {
  @JsonProperty("posts_list")
  private List<PostModel> posts;
}

