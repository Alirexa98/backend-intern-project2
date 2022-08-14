package com.example.backendinternproject2.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRequestModel {

  @JsonProperty("kw")
  private String keywords = "";
  @JsonProperty("from_date")
  private LocalDateTime fromDate = LocalDateTime.parse("1990-09-01T19:34:48.000");
  @JsonProperty("t_names")
  private List<String> tags = new ArrayList<>();
  @JsonProperty("min_score")
  private Integer minScore = Integer.MIN_VALUE;
  @JsonProperty("cnt_in_page")
  private Integer pageCount = 10;
  @JsonProperty("page_no")
  private Integer pageNumber = 0;

}