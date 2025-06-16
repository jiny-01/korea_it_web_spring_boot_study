package com.koreait.spring_boot_study.controller;

//정적 페이지 반환

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
class UserDto {
    private int userId;
    private String username;
    private int age;
}

//Controller => SSR
//즉 서버쪽에서 웹페이지를 렌더링해서 반환하는 SSR 방식 - 웹페이지를 반환 (HTML, JSP) JSP: 동적
//RestControler => 데이터를 반환 (JSON 또는 XML 데이터 반환)
//
@Controller
public class MainController {
    private List<UserDto> users = new ArrayList<>();    //userDto 저장할 리스트 생성

    //정적 페이지 반환
    //데이터 즉 동적인 요소가 없는 정적 웹페이지
    @GetMapping("/main")
    public String getMain() {
        return "main.html";          //getMain 이 알아서 "main.html" 파일을 찾음 -> 정적 웹페이지 반환
                                     //원래 resources 안에 html 파일 만들어야 함 -> 요청마다 HTML 파일 만들기
    }

    //SSR 에 동적을 추가하면 Thymeleaf 를 적용하면 된다.

    //Thymeleaf -> templates 에서 찾음 -> static 의 main 이 적용안됨
    //html 파일은 템플릿 패키지 폴더에 있어야함

    //=> 서버에서 HTML 을 렌더링할 때, 자바 데이터를 끼워 넣을 수 있게 해주는 템플릿 엔진 - 동적 데이터

    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("username", "<b>김지니</b>");    //<b></b> : 굵게나옴
        model.addAttribute("isAdult", true);               //true 일 때만 나옴
        model.addAttribute("age", 20);
        Map<String, String> userList = new HashMap<>();
        userList.put("김지니", "25");
        userList.put("이동윤", "27");
        userList.put("삼동윤", "18");
        userList.put("사동윤", "44");

        model.addAttribute("userList", userList);

        //addAttribute 로 model 에 넣으면 알아서 profile.html 파일에 넣음

        return "profile.html";

    }

    @GetMapping("/search")
    public String getSearch(@RequestParam String keyword, Model model) {    //request? 해서 요청받음
        model.addAttribute("keyword", keyword);                 //요청받은 걸 model 에 넣음
        return "search.html";

    }


    //메소드가 다르면 요청주소 같아도 됨 (/signup)

    //회원가입페이지 호출
    @GetMapping("/signup")
    public String signup() {
        return "signup.html";     //signup 만 해도됨

    }


    //가입정보 받기(회원가입 처리)
    //requestparam 으로 input 값 받음
    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String name, @RequestParam int age, Model model) {
        UserDto userDto = new UserDto(users.size()+1, name, age);    //user1, 2, ....로 리스트에 추가되도록
        users.add(userDto);
        model.addAttribute("message", name + "님, 가입을 환영합니다.");
        return "signup-result";    //signup-result 페이지 반환

    }

    //가입하기 처리
    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", users);
        return "users";                         //html 파일명으로 리턴(users.html)
    }

}
