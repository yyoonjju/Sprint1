package com.example.sprint.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.sprint.model.Service;
import com.example.sprint.model.User;
import com.example.sprint.repository.ServiceRepository;
import com.example.sprint.repository.UserRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/")
    public String index(){
        return "html/index";
    }

    @GetMapping("/createuser")
    public String createuser(){
        return "html/createuser";
    }
    @PostMapping("/createuser")
    public String postCreateuser(
    @RequestParam("userId") String userId,
    @RequestParam("userPassword") String userPassword,
    @RequestParam("userNickname") String userNickname,
    @RequestParam("phone") String phone,
    @RequestParam("address") String address
    ){
        User user = new User();
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        user.setUserNickname(userNickname);
        user.setPhone(phone);
        user.setAddress(address);
        userRepository.save(user);

        return "redirect:/";
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
    @GetMapping("/deleteuser")
    public String deleteuser(
        @RequestParam("userId") String userId
    ){
        User user=userRepository.findByUserId(userId);
        userRepository.delete(user);
        return "html/deleteuser";
    }

    @GetMapping("/subinfo")
    public String subinfo(){
        return "html/subinfo";
    }
    @GetMapping("/counsel")
    public String counsel(){
        return "html/counsel";
    }
}
