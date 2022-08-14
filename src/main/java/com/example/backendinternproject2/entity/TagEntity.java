package com.example.backendinternproject2.entity;

import com.example.backendinternproject2.xml.XmlTagModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagEntity {

  @Id
  private Integer id;
  private String name;
  private Integer count;

  public static TagEntity of(XmlTagModel tagModel) {
    return new TagEntity(
        tagModel.getId(),
        tagModel.getName(),
        tagModel.getCount()
    );
  }
}
