package com.kh.six.employee;

import com.kh.six.member.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    @Value("${file.upload.path.member}")
    private String filePath;

    private final EmployeeMapper employeeMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public int join(EmployeeVo vo , MultipartFile profile) throws IOException {

        //valid
        checkValidation(vo);

        //save file
        if( profile != null && !profile.isEmpty()){
            vo.setProfileOriginName(profile.getOriginalFilename());
        }

        //encrypt
        String encodedPw = bCryptPasswordEncoder.encode(vo.getPw());
        vo.setPw(encodedPw);

        return employeeMapper.join(vo);
    }

    private void checkValidation(EmployeeVo vo){
        checkIdValid(vo.getId());
        checkPwValid(vo.getPw());
    }

    private void checkIdValid(String id) {
        if(id != null && id.length() >= 4 && id.length() <= 12){
            return;
        }
        throw new IllegalArgumentException("[M-101] id length");
    }

    private void checkPwValid(String pw) {
        if(pw != null && pw.length() >= 4 && pw.length() <= 12){
            return;
        }
        throw new IllegalArgumentException("[M-102] pw length");
    }

    public EmployeeVo login(EmployeeVo vo) {
        EmployeeVo dbVo = employeeMapper.selectById(vo.getId());
        boolean isPwMatch = bCryptPasswordEncoder.matches(vo.getPw(), dbVo.getPw());
        boolean isAgencyMatch = vo.getAgencyNo().equals(dbVo.getAgencyNo());

        if(isPwMatch && isAgencyMatch){
            return dbVo;
        }

        return null;
    }

    public boolean isIdDuplicate(String id) {
        EmployeeVo employee = employeeMapper.selectById(id);

        return employee != null;
    }
}//class
