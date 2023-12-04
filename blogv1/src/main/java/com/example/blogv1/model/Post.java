package com.example.blogv1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "seq")
  private Member user; // Member와의 연관관계 설정

  @Transient
  private String memberId; // memberId만 나중에 글쓴이로 보여지기 위해 연결. 하지만 뭔가 작동을 하지 않는다. 

}
