package com.example.blogv1.dto;

import lombok.Data;

@Data
public class SaveDTO {
  private String title;
  private String content;
  private String member_id;

  public String member_id() {
    return null;
  }
}
