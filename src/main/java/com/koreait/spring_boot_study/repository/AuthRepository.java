package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.dto.SignupReqDto;

import java.util.HashMap;
import java.util.Map;

//레포지토리는 확인하기 위한 용도 - 직접 처리하는 것은 아님 => 관심사 분리
public class AuthRepository {
    private final Map<String, String> userdb = new HashMap<>();

    public AuthRepository() {
        userdb.put("test@example.com", "홍길동");
    }

    //이메일 중복확인
    public int findByEmail(String email) {
        if(userdb.containsKey(email)) {
            return 0;            //중복 있으면 0
        } else {
            return 1;            //없으면 1
        }
    }


    public int addUser(SignupReqDto signupReqDto) {
        userdb.put(signupReqDto.getEmail(), signupReqDto.getUsername());
        return 1;
    }
}
