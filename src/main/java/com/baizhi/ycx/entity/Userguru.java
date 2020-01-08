package com.baizhi.ycx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Userguru {

  @Id
  private String uid;
  private String gid;

}
