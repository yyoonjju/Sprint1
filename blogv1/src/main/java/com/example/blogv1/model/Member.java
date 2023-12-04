package com.example.blogv1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long seq; // 회원 번호 역할 . 이생각을 못해서, post에서 memberId 컬럼을 만들고, 여기 memberId와
  // fk로 연결하면 되겠지.. 했는데 안되서 몇시간 동안 헛고생했음. post에서 새로운 컬럼을 만들고 키로 연결하면
  // 무조건 프라이머리 키로 연결된다는걸 나중에 깨닫고.. 회원번호로 서로를 연결시키고, memberId로 연결시키는 것으로 생각을 바꿨으나.
  // 시간이 너무 많이 흘러서 기능 구현을 많이 하지 못했습니다..
  private String memberId;

  private String memberPw;
  
  private String memberName;

}
