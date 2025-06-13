package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.SignupReqDto;
import com.koreait.spring_boot_study.dto.SignupRespDto;
import com.koreait.spring_boot_study.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    //유효성 검사 - SignupReqDto 의 이메일, 비밀번호 받아옴 -> RequestBody
    //signupReqDto 를 받아와서 signupRespDto 만들어서 확인

    public SignupRespDto signup(@RequestBody SignupReqDto signupReqDto) {
        if(signupReqDto.getEmail() == null || signupReqDto.getEmail().trim().isEmpty()) {
            SignupRespDto signupRespDto = new SignupRespDto("failed", "이메일을 입력해주세요"
            );
            return  signupRespDto;
        } else if (signupReqDto.getPassword() == null || signupReqDto.getPassword().trim().isEmpty()) {
            SignupRespDto signupRespDto = new SignupRespDto(
                    "failed", "비밀번호를 입력"
            );
        } else if (signupReqDto.getUsername() == null || signupReqDto.getUsername().trim().isEmpty()) {
            SignupRespDto signupRespDto = new SignupRespDto("failed", "사용자이름 입력");
            return signupRespDto;
        }


        //이메일 중복확인 로직
        int chkEmail = authRepository.findByEmail(signupReqDto.getEmail());
        if(chkEmail == 1) {                         //중복 없으면 authRepository.addUser() 적용
            authRepository.addUser(signupReqDto);
            return new SignupRespDto
                    ("success", signupReqDto.getUsername() + "님 회원가입 완료");
        } else if(chkEmail == 0) {
            return new SignupRespDto("failed", "이미 존재하는 이메일임");
        }
        return new SignupRespDto("failed", "회원가입에 오류가 발생함. 다시 시도");

    }

}
