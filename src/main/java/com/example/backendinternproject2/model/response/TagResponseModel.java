package com.example.backendinternproject2.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagResponseModel {
  @JsonProperty("t_id")
  private Integer tagId;
  @JsonProperty("t_name")
  private String tagName;
  @JsonProperty("t_cnt")
  private Integer tagCount;
}
