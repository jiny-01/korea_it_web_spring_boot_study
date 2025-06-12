package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.SigninReqDto;
import com.koreait.spring_boot_study.dto.SignupReqDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //RequestParam (쿼리스트링)
    //클라이언트가 URL 쿼리스트링으로 넘긴 값을 메소드 파라미터로 전달

    @GetMapping("/get")                //헤더에 내가 입력한 값을 그대로 url로 전송
    public String getUser(@RequestParam String userId) {
        System.out.println("RequestParam 으로 들어온 값 : " + userId);
        return "RequestParam 으로 들어온 값 : " + userId;        //서버에 표시될 내용
    }
    //http://localhost:8080/auth/get?userId=10 으로 확인가능

    @GetMapping("/get/name")
    public String getUsername
            (@RequestParam(value = "name", defaultValue = "홍길동") String username, @RequestParam(required = false) Integer age) {
        System.out.println(username + age);
        return username + age;

        //@GetMapping -> 헤더에 내가 입력한 값을 그대로 url로 전송
        //@PostMapping -> 패킷에 입력한 값이 숨겨져서 전달



        //@RequestParam - Get 으로 호출할 때 사용
        //http://localhost:8080/auth/get/name?
        //2개면 사이에 &

        //내가 보낼 땐 name 으로, 서버에선 username 으로 보이게 다르게 할 수 있음
        //-> 안에서 사용하는 변수명과 쿼리스트링의 키값이 다를 경우 (value= ) 괄호 안에 표기
        //값 안 넣었을 떄 defaultValue = 기본값 지정 가능
        //다른 타입도 가능하며 여러 개의 RequestParam 도 받을 수 있다.





        //**주의**
        // int - null 허용X, 값 존재안함 -> null에러발생
        // Integer - 값이 없지만 null 포함 -> null이 표기됨
        //가장 좋은 건 String 으로 받고 나중에 변환하는 것 권장(parseInt)
        //required = false 하면 받든말든 선택적(디폴트값은 true임)
        //but default value 설정되있다면 required 설정은 무의미함


        //RequestParam 주의사항
        //파라미터가 없으면 500 에러
        //타입이 안맞을 떄
        //이름이 불일치할 때
        //민감한 정보 쓰지 말 것!!(보안적 관점)

    }

    @GetMapping("/get/names")
    public String getUsernames(@RequestParam List<String> names) {
        return names.toString();
    }
    //get/names=a,b




    //요청주소는 /search => RequestParam - name, email
    //name 은 필수 아님, 이메일은 기본값으로 no-email 표기
    //요청 -> /auth/search?name=lee
    //반환 -> "검색 조건 - 이름 : ***, 이메일: ***"

    @GetMapping("/search")
    public String getUser
            (@RequestParam(required = false) String name, @RequestParam(defaultValue = "no email") String email) {
        System.out.println(name + " " + email);
        return "검색 조건 - 이름 : " + name + "," + " 이메일 : " + email;
    }

    //RequestBody
    //HTTP 요청의 바디에 들어있는 JSON 데이터를 자바 객체(DTO)로 반환해서 주입하는 어노테이션
    //클라이언트가 JSON 형식으로 데이터 보냄
    //@RequestBody -> JSON 형식으로 데이터 받음
    //백엔드 서버는 그 JSON을 @RequestBody 가 붙은 DTO 로 자동 매핑
    //일반적으로 POST, PUT, PATCH 에서 사용

    //DTO (Data Transfer Object)  -> Entity -> DB
    //데이터를 전달하기 위한 객체, Entity 한테 가기 위한 중간 단계
    //클라이언트 간 데이터를 주고받을 때 사용하는 중간 객체
    //이름명 - 메소드 이름 따서 만듦 보통
    //request dto(req)  /   response dto(res)

    //DTO 패키지에 SignupReqDto 만들어서 회원가입 시 받을 데이터 JSON 객체로 생성

    @PostMapping("/signup")                //post 방식-패킷에 입력한 값이 숨겨져서 전달
    public String signup(@RequestBody SignupReqDto signupReqDto) {
        System.out.println(signupReqDto);
        return signupReqDto.getUsername() + "님 회원가입이 완료되었습니다.";
    }



    //포스트맨 Body - raw - json 선택
    // send 시 객체 생성자 이름과 보낸 정보의 키(JSon의 키) 이름 일치해야함
    //header 에 content type 있어야함


    //Post 요청 signin 로그인 로직
    //SigninReqDto => email, password

    @PostMapping("/signin")
    public String signin(@RequestBody SigninReqDto signinReqDto) {
        System.out.println(signinReqDto);
        return "로그인 완료 : " + signinReqDto.getEmail() + "님 반갑습니다.";
    }






}
