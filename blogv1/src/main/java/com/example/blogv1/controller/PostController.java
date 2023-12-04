package com.example.blogv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogv1.dto.SaveDTO;
import com.example.blogv1.model.Post;
import com.example.blogv1.service.PostService;

@RestController
public class PostController {  //  dbcontroller에 있는 list 페이지를 구현하기 위해서. 
  private PostService postService;

  @Autowired
  public void DBController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping("/posts")
  public String createPost(@ModelAttribute SaveDTO dto) {
    Post post = postService.savePost(dto);
    return "redirect:/posts/" + post.getId();
  }
}
