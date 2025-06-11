package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//controller 두 가지 방식 존재
// 1. controller
// html(웹페이지) 반환하는 서버 사이드 렌더링 (SSR)
// 2. RestController
// Json, 문자열 등 다양한 데이터 반환 => REST API 서버 (웹서버)

//서버 사이드 렌더링은 서버 측에서 프론트의 웹페이지까지 통쨰로 반환하고, 그걸 브라우저에서 띄움
//그럼 해당 요청 경로에 따라서 웹페이지가 다 할당되어있어서 느림

//하지만 우리가 하게 될 프론트(리액트) + 백엔드(스프링부트 웹서버)
//리액트는 클라이언트 사이드 렌더링(CSR)  즉 프론트엔드 쪽에서 웹페이지를 로드
//백엔드ㅡ는 요청에 해당하는 데이터만 반환시켜줌
//이러한 조합 => Single Page Application(SPA)

//기능별로 Controller 만들 수 있음 - 유저, 게시판, 댓글...etc
@RestController
//이 컨트롤러는 RestController
//IOC 컨테이너에 PostController 객체를 생성해서 담아달라는 의미

//@Service
//IOC 컨테이너에 Service 로서 담겠다는 의미로 PostService 도 담아달라는 것


@RequestMapping("/post")
//해당 컨트롤러에 공통 주소가 있다면 RequestMapping 으로 묶기 가능



public class PostController {

    private final PostService postService;     //상수로 정의

    //IOC(Inversion Of Control, 제어의 역전)
    //객체 생성과 제어의 주도권을 개발자가 아닌, 스프링부트가 갖는 것
    //스프링부트는 내가 만든 객체를 다 가지고 있을 것 -> IOC container 에 담아둠
    //ioc container => 스프링부트가 만든 객체들을 담아두고 관리하는 창고
    //필요한 곳이 있으면 꺼내서 넣어줌
    //ioc 컨테이너에서 해당 객체를 찾아서 자동으로 넣어주니까 우리는 new로 생성할 필요 X
    //이미 실행될 때 ioc 컨테이너에 객체들이 생성되서 보관되어있음

    //DI(Dependency Injection, 의존성 주입)
    //필요한 객체(의존성)를 직접 만들지 않고 외부(스프링부트)에서 대신 넣어주는 것(행위)

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //매개변수로 들어오는 PostService 의 객체는 스프링이 알아서 만들어준 것

    @GetMapping("/get")
    public String getPost() {
        System.out.println("get 으로 들어온 요청입니다~~");
//        return "어떤 게시물의 데이터";
        return postService.getPost();
        //postService 의 메소드를 호출하기 위해 @Service 해줘야하고,
        // PostController 에 postService 객체 생성해서 생성자로 정의해야 함
    }


    @GetMapping("/user")
    public String getPostUser() {
        System.out.println("get/user 로 들어온 요청입니다~~");
        return "어떤 게시물의 유저 정보";
    }

}
