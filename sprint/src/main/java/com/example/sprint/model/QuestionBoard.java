package com.example.sprint.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class QuestionBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSeq;
    private String userId;
    private String title;
    private String content;
    private String answer;
    private String state;
    private String writedate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
