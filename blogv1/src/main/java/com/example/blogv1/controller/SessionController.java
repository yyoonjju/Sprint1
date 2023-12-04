package com.example.blogv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;
import com.example.blogv1.model.Member;
import com.example.blogv1.repository.MemberRepository;

@Controller
public class SessionController {
  @Autowired
  MemberRepository memberRepository;

  @GetMapping("/login")
  public String login() {
    return "html/login";
  }

  @PostMapping("/login")
  public String CheckAccount(
      @RequestParam String memberId,
      @RequestParam String memberPw,
      HttpSession session,
      Model model) {
    Member member = memberRepository.findByMemberIdAndMemberPw(memberId, memberPw); // 로그인시 id, pw 매치

    if (member != null) {
      session.setAttribute("member", member);
      return "redirect:/main";

    } else {
      model.addAttribute("NoneAccount", "정보가 틀립니다. 기억을 더듬어 보세요.");
      return "/html/login";
    }

  }

  @GetMapping("/main")
  // 작동을 잘 안함..
  public String main(Model model, HttpSession session) {
    Member member = (Member) session.getAttribute("member");

    if (member != null) {
      String welcomeMessage = member.getMemberName() + "님 반갑습니다.";
      model.addAttribute("welcome", welcomeMessage);
    } else {
      model.addAttribute("bull", "회원 정보가 없네요");
    }
    return "/html/main";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }

  @GetMapping("/register")
  public String register(HttpSession session) {
    Member member = new Member();
    member.setMemberId("admin");
    session.setAttribute("member", member);
    return "/html/login";
  }

  @PostMapping("/register")
  public String registerPost(@RequestParam("memberId") String memberId,
      @RequestParam("memberPw") String memberPw,
      @RequestParam("memberName") String memberName,
      HttpSession session) {
    Member existingMember = memberRepository.findByMemberId(memberId);
    if (existingMember != null) {
      return "html/register";
    }
    Member member = new Member();
    member.setMemberId(memberId);
    member.setMemberName(memberName);
    member.setMemberPw(memberPw);
    session.setAttribute("member", member);
    memberRepository.save(member);
    return "redirect:/main";
  }

}
