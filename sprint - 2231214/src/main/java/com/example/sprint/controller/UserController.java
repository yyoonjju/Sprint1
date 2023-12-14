package com.example.sprint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.sprint.model.QuestionBoard;
import com.example.sprint.model.ServiceHistory;
import com.example.sprint.model.User;
import com.example.sprint.repository.QuestionBoardRepository;
import com.example.sprint.repository.ServiceHistoryRepository;
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
    @Autowired
    ServiceHistoryRepository serviceHistoryRepository;

    // 메인

    @GetMapping("/")
    public String index() {
        return "html/main_homepage";
    }

    // 회원가입

    @PostMapping("/resister")
    public String postCreateuser(
            @RequestParam("userId") String userId,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userNickname") String userNickname,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            HttpSession session) {
        User check = userRepository.findByUserId(userId);
        if (check == null) {
            User user = new User();
            user.setUserId(userId);
            user.setUserPassword(userPassword);
            user.setUserNickname(userNickname);
            user.setPhone(phone);
            user.setAddress(address);
            userRepository.save(user);
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            return "html/idalert";
        }
    }

    // 로그인

    @GetMapping("/login")
    public String login() {
        return "html/login";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam("userId") String userId,
            @RequestParam("userPassword") String userPassword,
            HttpSession session) {

        User user;
        user = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        int count = userRepository.findByUserPasswordAndUserId(userPassword, userId).size();
        if (count < 1) {
            return "html/alert";
        } else {
            session.setAttribute("user", user);
            System.out.println(user);
            return "redirect:/";
        }
    }

    // 비밀번호 재설정
    @GetMapping("/findid")
    public String findId() {
        return "html/findid";
    }

    @PostMapping("/findid")
    public String postFindId(
            @RequestParam("userId") String userId) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            return "redirect:/resetpassword?userId=" + userId;
        } else {
            return "html/findidalert";
        }
    }

    @GetMapping("/resetpassword")
    public String resetPassword(
            @RequestParam("userId") String userId,
            Model model) {
        model.addAttribute("userId", userId);
        return "html/resetpassword";
    }

    @PostMapping("/resetpassword")
    public String postResetPassword(
            @RequestParam("userId") String userId,
            @RequestParam("userPassword") String userPassword) {
        User user = userRepository.findByUserId(userId);
        user.setUserPassword(userPassword);
        userRepository.save(user);
        return "redirect:/login";
    }

    // 로그아웃

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 내정보

    @GetMapping("/myinfo")
    public String myinfo(
            Model model,
            HttpSession session) {
        String userId = ((User) session.getAttribute("user")).getUserId();
        List<ServiceHistory> serviceHistories = serviceHistoryRepository.findByUserId(userId);
        if (serviceHistories.size() > 0) {
            model.addAttribute("serviceHistories", serviceHistories);
        }
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);

        return "html/myinfo";
    }

    @PostMapping("/myinfo")
    public String postMyinfo(
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userNickname") String userNickname,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            HttpSession session) {
        String userId = ((User) session.getAttribute("user")).getUserId();
        User user = userRepository.findByUserId(userId);
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        user.setUserNickname(userNickname);
        user.setPhone(phone);
        user.setAddress(address);
        userRepository.save(user);
        return "redirect:/myinfo";
    }

    // 구독신청

    @GetMapping("/subpage")
    public String subpage() {
        return "html/subpage";
    }

    @PostMapping("/subcheck")
    public String postSubpage(
            @RequestParam("subtype") String subtype,
            HttpSession session) {
        String userId = ((User) session.getAttribute("user")).getUserId();
        List<ServiceHistory> shList = serviceHistoryRepository.findByUserId(userId);
        System.out.println(shList);
        if (shList.size() == 0) {
            ServiceHistory serviceHistory = new ServiceHistory();
            serviceHistory.setUserId(userId);
            serviceHistory.setSubtype(subtype);
            serviceHistoryRepository.save(serviceHistory);
            return "redirect:/subscribe";
        } else if (("Premium".equals(subtype)) && (shList.size() != 0)
                && ("Basic".equals(shList.get(0).getSubtype()))) {
            ServiceHistory serviceHistory = shList.get(0);
            serviceHistory.setSubtype("Premium");
            serviceHistoryRepository.save(serviceHistory);

            return "redirect:/subscribe";
        } else {
            return "redirect:/subfail";
        }
    }

    @GetMapping("/subfail")
    public String subfail() {
        return "html/subfail";
    }

    @GetMapping("/subcheck")
    public String subcheck(
            @RequestParam("subtype") String subtype,
            Model model) {
        model.addAttribute("subtype", subtype);
        return "html/subcheck";
    }

    @GetMapping("/subscribe")
    public String subscribe(
            HttpSession session,
            Model model) {
        String userId = ((User) session.getAttribute("user")).getUserId();
        List<ServiceHistory> serviceHistories = serviceHistoryRepository.findByUserId(userId);
        model.addAttribute("serviceHistory", serviceHistories.get(serviceHistories.size() - 1));
        System.out.println(serviceHistories.get(serviceHistories.size() - 1));
        return "html/subscribe";
    }

    // 회원탈퇴

    @GetMapping("/usercheck")
    public String usercheck() {
        return "html/usercheck";
    }

    @GetMapping("deleteuser")
    public String deleteuser(
            HttpSession session) {
        String userId = ((User) session.getAttribute("user")).getUserId();
        User user = userRepository.findByUserId(userId);
        userRepository.delete(user);
        return "redirect:/";
    }

    // 문의게시판

    @GetMapping("/counsel")
    public String counsel(
            Model model,
            HttpSession session,
            @RequestParam(defaultValue = "1") int page) {
        String userId = ((User) session.getAttribute("user")).getUserId();
        int cnt;
        if ("admin".equals(userId)) {
            List<QuestionBoard> qBoardList = questionBoardRepository.findAllByOrderByBoardSeqDesc();
            model.addAttribute("qBoardList", qBoardList);
            cnt = qBoardList.size();

        } else {
            List<QuestionBoard> qBoardList = questionBoardRepository.findByUserIdOrderByBoardSeqDesc(userId);
            model.addAttribute("qBoardList", qBoardList);
            cnt = qBoardList.size();
        }

        int startPage = (page - 1) / 10 * 10 + 1;
        int endPage = (int) Math.ceil(cnt / 6);
        model.addAttribute("cnt", cnt);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);

        return "html/counsel";
    }

    // 게스트 게시판
    @GetMapping("/guestboard")
    public String guestboard(
            Model model,
            @RequestParam(defaultValue = "1") int page) {
        String userId = "guest";
        List<QuestionBoard> qBoardList = questionBoardRepository.findByUserIdOrderByBoardSeqDesc(userId);
        model.addAttribute("qBoardList", qBoardList);

        long cnt = qBoardList.size();
        model.addAttribute("cnt", cnt);

        int startPage = (page - 1) / 10 * 10 + 1;
        int endPage = (int) Math.ceil(cnt / 6);
        model.addAttribute("cnt", cnt);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);

        return "html/guestboard";
    }

    // 문의글 쓰기
    @GetMapping("/writeq")
    public String writequestion() {
        return "html/writeq";
    }

    @PostMapping("/writeq")
    public String postWriteq(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session) {
        String userId;
        if (session.getAttribute("user") == null) {
            userId = "guest";
        } else {
            userId = ((User) session.getAttribute("user")).getUserId();
        }
        QuestionBoard questionBoard = new QuestionBoard();
        questionBoard.setTitle(title);
        questionBoard.setContent(content);
        questionBoard.setState("대기 상태");
        questionBoard.setUserId(userId);
        questionBoardRepository.save(questionBoard);

        if ("guest".equals(userId)) {
            return "redirect:/guestboard";
        } else {
            return "redirect:/counsel";
        }
    }

    // 문의글 수정
    @GetMapping("/modifyq")
    public String modifyq(
            @RequestParam("boardSeq") Long boardSeq,
            Model model) {
        QuestionBoard qBoard = questionBoardRepository.findByBoardSeq(boardSeq);
        model.addAttribute("qBoard", qBoard);
        System.out.println("♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣");
        System.out.println(qBoard);
        return "html/modifyq";
    }

    @PostMapping("/modifyq")
    public String postModifyq(
            @RequestParam("boardSeq") Long boardSeq,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            Model model) {
        model.addAttribute("boardSeq", boardSeq);
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        return "html/modifycheck";
    }

    @GetMapping("/modifycheck")
    public String modifycheck() {
        return "html/modifycheck";
    }

    @PostMapping("modifycheck")
    public String postModifycheck(
            @RequestParam("boardSeq") Long boardSeq,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session) {
        System.out.println("【【【【【【【【【【【【【【【【【【");
        System.out.println(boardSeq);
        System.out.println(title);
        System.out.println(content);
        String userId = ((User) session.getAttribute("user")).getUserId();
        QuestionBoard questionBoard = questionBoardRepository.findByBoardSeq(boardSeq);
        System.out.println(questionBoard);
        questionBoard.setTitle(title);
        questionBoard.setContent(content);
        questionBoard.setState("대기 상태");
        questionBoard.setUserId(userId);
        questionBoardRepository.save(questionBoard);
        return "redirect:/counsel";
    }

    // 문의글 삭제
    @GetMapping("/deleteqcheck")
    public String deletecheck(
            @RequestParam("boardSeq") Long boardSeq,
            Model model) {
        System.out.println("################################");
        System.out.println(boardSeq);
        System.out.println("################################");
        model.addAttribute("boardSeq", boardSeq);
        return "html/deleteqcheck";
    }

    @GetMapping("/deleteq")
    public String deleteq(
            @RequestParam("boardSeq") Long boardSeq,
            HttpSession session) {
        String userId = ((User) session.getAttribute("user")).getUserId();
        QuestionBoard qBoard = questionBoardRepository.findByBoardSeq(boardSeq);
        questionBoardRepository.delete(qBoard);
        return String.format("redirect:/counsel?userId=%s", userId);
    }

    // admin - 문의글 답변 작성
    @GetMapping("/writea")
    public String wirteAnswer() {
        return "html/writea";
    }

    @PostMapping("/writea")
    public String postWriteAnswer(
            @RequestParam("answer") String answer,
            @RequestParam("boardSeq") Long boardSeq) {
        QuestionBoard qBoard = questionBoardRepository.findByBoardSeq(boardSeq);
        qBoard.setAnswer(answer);
        qBoard.setState("답변 완료");
        questionBoardRepository.save(qBoard);
        return "redirect:/counsel";
    }

    @GetMapping("/detail")
    public String detail() {
        return "html/detail";
    }
}
