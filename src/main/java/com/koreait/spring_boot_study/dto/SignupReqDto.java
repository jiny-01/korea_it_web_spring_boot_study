package com.koreait.spring_boot_study.dto;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

//회원 가입 시 요청 받을 데이터(DTO)
@Data
@AllArgsConstructor
public class SignupReqDto {
    private String username;
    private String password;
    private String email;




}
