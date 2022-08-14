package com.example.backendinternproject2.entity;

import com.example.backendinternproject2.xml.XmlUserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
  @Id
  private Integer id;
  private String name;

  public static UserEntity of(XmlUserModel userModel) {
    return new UserEntity(
        userModel.getId(),
        userModel.getName()
    );
  }
}
