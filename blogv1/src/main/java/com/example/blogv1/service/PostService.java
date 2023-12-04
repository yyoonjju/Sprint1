package com.example.blogv1.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.blogv1.model.Post;
import com.example.blogv1.repository.MemberRepository;
import com.example.blogv1.repository.PostRepository;

import org.springframework.stereotype.Service;

import com.example.blogv1.dto.SaveDTO;
import com.example.blogv1.model.Member;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public Post savePost(SaveDTO saveDTO) {

        String title = saveDTO.getTitle();
        String content = saveDTO.getContent();
        String memberId = saveDTO.getMember_id();

        Post postbox = new Post();
        postbox.setTitle(title);
        postbox.setContent(content);
        postbox.setCreatedAt(LocalDateTime.now());

        Member member = memberRepository.findByMemberId(memberId);
        postbox.setUser(member);

        return postRepository.save(postbox);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
