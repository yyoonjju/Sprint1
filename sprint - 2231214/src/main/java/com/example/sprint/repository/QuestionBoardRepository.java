package com.example.sprint.repository;

import org.springframework.stereotype.Repository;

import com.example.sprint.model.QuestionBoard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface QuestionBoardRepository extends JpaRepository<QuestionBoard,Long> {
    List<QuestionBoard> findByUserIdOrderByBoardSeqDesc(String userId);
    List<QuestionBoard> findAllByOrderByBoardSeqDesc();
    QuestionBoard findByBoardSeq(Long boardSeq);

    Long countBy(); //pagination
}
