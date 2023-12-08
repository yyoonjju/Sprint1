package com.example.sprint.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.sprint.model.QuestionBoard;
import com.example.sprint.model.Service;
import com.example.sprint.model.User;
import com.example.sprint.repository.QuestionBoardRepository;
import com.example.sprint.repository.ServiceRepository;
import com.example.sprint.repository.UserRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    QuestionBoardRepository questionBoardRepository;

    @GetMapping("/")
    public String index(){
        return "html/index";
    }
    @PostMapping("/resister")
    public String postCreateuser(
    @RequestParam("userId") String userId,
    @RequestParam("userPassword") String userPassword,
    @RequestParam("userNickname") String userNickname,
    @RequestParam("phone") String phone,
    @RequestParam("address") String address,
    HttpSession session
    ){
        User check = userRepository.findByUserId(userId);
        if (check == null){
            User user = new User();
            user.setUserId(userId);
            user.setUserPassword(userPassword);
            user.setUserNickname(userNickname);
            user.setPhone(phone);
            user.setAddress(address);
            userRepository.save(user);
            session.setAttribute("user", user);
            return "redirect:/";
        }else {
            return "html/idalert";
        } 
    }
    
    @GetMapping("/login")
    public String login(){
        return "html/login";
    }
    @PostMapping("/login")
    public String loginPost(
    @RequestParam("userId") String userId, 
    @RequestParam("userPassword") String userPassword,
    HttpSession session
    ){
    
        User user;
        user=userRepository.findByUserIdAndUserPassword(userId, userPassword);
        int count = userRepository.findByUserPasswordAndUserId(userId,userPassword).size();
        if(count<1){
            return "html/alert";
        }else{
            session.setAttribute("user", user);
            System.out.println(user);
            return "redirect:/";
        }
    }
    // 로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/myinfo")
    public String myinfo(){
        return "html/myinfo";
    }
    @PostMapping("/myinfo")
    public String postMyinfo(
    @RequestParam("userId") String userId,
    @RequestParam("userPassword") String userPassword,
    @RequestParam("userNickname") String userNickname,
    @RequestParam("phone") String phone,
    @RequestParam("address") String address
    ){
        User user=new User();
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        user.setUserNickname(userNickname);
        user.setPhone(phone);
        user.setAddress(address);
        userRepository.save(user);
        return "redirect:/login";
    }

    // 구독신청
    @GetMapping("/subscribe")
    public String subscribe(Model model){
        List<Service> serviceList=serviceRepository.findAll();
        model.addAttribute("serviceList", serviceList);
        return "html/subscribe";
    }
    
    // 회원탈퇴
    @GetMapping("/usercheck")
    public String usercheck(){
        return "html/usercheck";
    }
    @GetMapping("deleteuser")
    public String deleteuser(
        HttpSession session
    ){
        String userId=((User) session.getAttribute("user")).getUserId();
        User user = userRepository.findByUserId(userId);
        userRepository.delete(user);
        return "redirect:/";
    }


    @GetMapping("/subinfo")
    public String subinfo(){
        return "html/subinfo";
    }

    @GetMapping("/counsel")
    public String counsel(
        Model model,
        HttpSession session
    ){
        String userId=((User) session.getAttribute("user")).getUserId();

        if ("admin".equals(userId)){
            List<QuestionBoard> qBoardList=questionBoardRepository.findAllByOrderByBoardSeqDesc();
            model.addAttribute("qBoardList", qBoardList);
            System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆");
            System.out.println(qBoardList);
        }else{
            List<QuestionBoard> qBoardList = questionBoardRepository.findByUserIdOrderByBoardSeqDesc(userId);
            model.addAttribute("qBoardList", qBoardList);
            System.out.println("★★★★★★★★★★★★★");
            System.out.println(qBoardList);
            System.out.println(userId);
        }
        return "html/counsel";
    }

    // @GetMapping("/counsel")
    // public String counsel(
    //     @RequestParam(required=false) String userId,
    //     Model model,
    //     HttpSession session
    // ){
    //     if(userId==null){
    //         System.out.println("null이당");
    //         String userId=(String)session.user.userId;
    //     }
    //     else if ("admin".equals(userId)){
    //         List<QuestionBoard> qBoardList=questionBoardRepository.findAllByOrderByBoardSeqDesc();
    //         model.addAttribute("qBoardList", qBoardList);
    //         System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆");
    //         System.out.println(qBoardList);
    //     }else{
    //         List<QuestionBoard> qBoardList = questionBoardRepository.findByUserIdOrderByBoardSeqDesc(userId);
    //         model.addAttribute("qBoardList", qBoardList);
    //         System.out.println("★★★★★★★★★★★★★");
    //         System.out.println(qBoardList);
    //         System.out.println(userId);
    //     }
    //     return "html/counsel";
    // }
    @GetMapping("/writeq")
    public String writequestion(){
        return "html/writeq";
    }
    @PostMapping("/writeq")
    public String postWriteq(
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        @RequestParam("userId") String userId
    ){
        QuestionBoard questionBoard = new QuestionBoard();
        questionBoard.setTitle(title);
        questionBoard.setContent(content);
        questionBoard.setState("대기 상태");
        questionBoard.setUserId(userId);
        questionBoardRepository.save(questionBoard);
        return String.format("redirect:/counsel?userId=%s", userId);
    }
    @GetMapping("/modifyq")
    public String modifyq(
        @RequestParam("boardSeq") Long boardSeq,
        Model model
    ){
        QuestionBoard qBoard=questionBoardRepository.findByBoardSeq(boardSeq);
        model.addAttribute("qBoard",qBoard);
        System.out.println("♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣");
        System.out.println(qBoard);
        return "html/modifyq";
    }
    @GetMapping("/deleteq")
    public String deleteq(
        @RequestParam("boardSeq") Long boardSeq,
        @RequestParam("userId") String userId
    ){
        QuestionBoard qBoard=questionBoardRepository.findByBoardSeq(boardSeq);
        questionBoardRepository.delete(qBoard);
        return String.format("redirect:/counsel?userId=%s", userId);
    }

    // 답변 작성
    @GetMapping("/writea")
    public String wirteAnswer(){
        return "html/writea";
    }
    @PostMapping("/writea")
    public String postWriteAnswer(
        @RequestParam("answer") String answer,
        @RequestParam("boardSeq") Long boardSeq
    ){
        QuestionBoard qBoard=questionBoardRepository.findByBoardSeq(boardSeq);
        qBoard.setAnswer(answer);
        qBoard.setState("답변 완료");
        questionBoardRepository.save(qBoard);
        return "redirect:/counsel";
    }
}
