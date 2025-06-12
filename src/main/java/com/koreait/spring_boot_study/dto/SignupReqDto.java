package com.koreait.spring_boot_study.dto;

import jdk.jfr.DataAmount;
import lombok.Data;

//회원 가입 시 요청 받을 데이터(DTO)
@Data
public class SignupReqDto {
    private String username;
    private String password;
    private String email;

}
