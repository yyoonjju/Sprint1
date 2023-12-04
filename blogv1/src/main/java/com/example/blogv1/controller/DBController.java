package com.example.blogv1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.blogv1.dto.SaveDTO;
import com.example.blogv1.model.Post;

import com.example.blogv1.service.PostService;

import java.util.*;

@Controller
public class DBController {
    private PostService postService;

    public DBController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/memo")
    public String memo() {
        return "html/memo";
    }

    @GetMapping("/comunity")
    public String comu() {
        return "html/comunity";
    }

    @PostMapping("/save")
    public String boardInsert(SaveDTO saveDTO) {
        postService.savePost(saveDTO);
        return "redirect:/memo";
    }

    @GetMapping("/list")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "html/list";
    }

}
// @GetMapping("board/detail"){ } 구현 해야 하는데 못한것들.. 목록 자세히보기

// @GetMapping("board/answer") 답글 등록

// @GetMapping("board/answer/execute") 답글 등록 확인

// @GetMapping("board/delete") 글 삭제

// @GetMapping("board/update") 글 수정

// @GetMapping("board/update/execute") 수정 실행 창?
