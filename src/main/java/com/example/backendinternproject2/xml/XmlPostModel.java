package com.example.backendinternproject2.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement(name = "row")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class XmlPostModel {
  @JsonProperty("Id")
  private Integer id;
  @JsonProperty("Title")
  private String title;
  @JsonProperty("Body")
  private String body;
  @JsonProperty("CreationDate")
  private LocalDateTime creationDate;
  @JsonProperty("AcceptedAnswerId")
  private Integer acceptedAnswerId;
  @JsonProperty("OwnerUserId")
  private Integer userId;
  @JsonProperty("PostTypeId")
  private Integer postType;
  @JsonProperty("Score")
  private Integer score;
  @JsonProperty("Tags")
  private String tags;
}
