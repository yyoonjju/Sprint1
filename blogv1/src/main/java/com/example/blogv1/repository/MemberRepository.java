package com.example.blogv1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.blogv1.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Member findByMemberIdAndMemberPw(String memberId, String memberPw);

  Member findBySeq(Long seq);

  List<Member> findByMemberPwAndMemberId(String memberPw, String memberId);

  Member findByMemberId(String memberId);

}
