package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.SigninReqDto;
import com.koreait.spring_boot_study.dto.SigninRespDto;
import com.koreait.spring_boot_study.dto.SignupReqDto;
import com.koreait.spring_boot_study.dto.SignupRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping("/signup")                //post 방식-패킷에 입력한 값이 숨겨져서 전달
//    public String signup(@RequestBody SignupReqDto signupReqDto) {
//        System.out.println(signupReqDto);
//        return signupReqDto.getUsername() + "님 회원가입이 완료되었습니다.";
//    }



    //포스트맨 Body - raw - json 선택
    // send 시 객체 생성자 이름과 보낸 정보의 키(Json의 키) 이름 일치해야함
    //header 에 content type 있어야함


    //Post 요청 signin 로그인 로직
    //SigninReqDto => email, password

//    @PostMapping("/signin")
//    public String signin(@RequestBody SigninReqDto signinReqDto) {
//        System.out.println(signinReqDto);
//        return "로그인 완료 : " + signinReqDto.getEmail() + "님 반갑습니다.";
//    }

    //Response Entity
    //http 응답 전체(데이터)를 커스텀해서 보낼 수 있는 스프링 클래스
    //HTTP 상태 코드, 응답바디, 응답헤더(설정요소)까지 모두 포함

    @PostMapping("/signin")
    public ResponseEntity<SigninRespDto> signin(@RequestBody SigninReqDto signinReqDto) {
        if(signinReqDto.getEmail() == null || signinReqDto.getEmail().trim().isEmpty()) {
            SigninRespDto signinRespDto = new SigninRespDto("failed", "이메일을 다시 입력");
            return ResponseEntity.badRequest().body(signinRespDto);
            //이메일 값이 없거나 or 공백제거한 이메일 값이 없거나

        } else if(signinReqDto.getPassword() == null || signinReqDto.getPassword().trim().isEmpty()) {
            SigninRespDto signinRespDto = new SigninRespDto("failed", "비밀번호 다시 입력");
            return ResponseEntity.badRequest().body(signinRespDto);
        }



        SigninRespDto signinRespDto = new SigninRespDto("success", "로그인 성공");
        return ResponseEntity.ok(signinRespDto);    //포스트맨 - 200 OK 로 표기됨
//        return ResponseEntity.ok().body(signinRespDto);
//        return ResponseEntity.status(HttpStatus.OK).body(signinRespDto);    status(HttpStatus.OK) - 상태코드 따로 뺀 것
    }
    // return 은 요청 입장에선 다 ok 로 되어있어야함 -> 보낼 땐 무조건 OK
    // 정확한 정보는 프론트에서 확인하도록 함
    // 원래는 유효성 검사(validate) 시행함 (프론트, 백 둘 다 가능)

    //상태 코드

    //200 OK => 요청 성공
    //400 Bad Request => 잘못된 요청 (ex. 유효성 실패, Json 파싱 오류)
    //401 Unauthorized => 인증 실패 (ex. 로그인 안 됨, 토큰 없음) - 인가되지 않은 사용자 로그인
    //403 Forbidden => 접근 권한 없음 (ex. 관리자만 접근 가능)
    //404 Not Found => 리소스 없음 (ex. 없는 요청, 잘못된 정보 조회)
    //409 Conflict => 중복 등으로 인한 충돌 (ex. 이미 존재하는 이메일)
    //500 Internal Server Error => 서버 내부 오류(코드 문제, 예외 등) - 바로 백서버(소스코드) 확인

    // -> 200 은 정상적으로 됐다
    // -> 400 은 네가 잘못 보냈다
    // -> 500 은 서버가 터졌다

    //

    //원래라면 레포 -> 서비스 -> 컨트롤러 순으로 만드는 게 원칙
    //req dto 랑 resp dto 같아도 하나 더 만들어서 하기를 권장
    @PostMapping("/signup")
    public ResponseEntity<SignupRespDto> signup(@RequestBody SignupReqDto signupReqDto) {

    }


}
