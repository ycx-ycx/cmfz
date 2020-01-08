package com.baizhi.ycx.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

  @Id
  private String id;
  private String title;
  private String img;
  private String content;
  @JSONField(format = "yyyy-MM-DD")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createDate;
  @JSONField(format = "yyyy-MM-DD")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date publishDate;
  private String status;
  private String guruId;

}