package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
//IOC 컨테이너에 Service 로서 담겠다는 의미
public class PostService {

    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String getPost() {
        String result = postRepository.getPost();
        return "서비스에서 보낸 어떠한 게시물의 데이터";
    }


}
