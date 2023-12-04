package com.example.blogv1.service;

import org.springframework.stereotype.Service;

import com.example.blogv1.model.Member;
import com.example.blogv1.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    
    }

    public Member findMemberByCurrentSessionId(HttpServletRequest request) {

        // memberId를 사용하여 데이터베이스에서 해당 멤버 찾기 근데 되는지 안되는지 모르겠음. 
        Member member = memberRepository.findByMemberId("MemberId");

        return member;
    }
}
