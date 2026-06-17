package com.kh.six.member;

import com.kh.six.planConfirm.FixedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public int join(MemberVo vo) {
        checkValidation(vo);

        //encrypt
        String encodedPw = bCryptPasswordEncoder.encode(vo.getPw());
        vo.setPw(encodedPw);
//        vo.setOriginName(profile.getOriginalFilename());

        return memberMapper.join(vo);
    }

    //valid
    private void checkValidation(MemberVo vo){
        checkIdValid(vo.getId());
        checkPwValid(vo.getPw());
        checkNameValid(vo.getName());
    }

    private void checkIdValid(String id) {
        if(id != null && id.length() >= 4 && id.length() <= 20){
            return;
        }
        throw new IllegalArgumentException("[M-101] id length");
    }

    private void checkPwValid(String pw) {
        if(pw != null && pw.length() >= 4 && pw.length() <= 20){
            return;
        }
        throw new IllegalArgumentException("[M-102] pw length");
    }

    private void checkNameValid(String name) {
        if(name != null && name.length() >= 2 && name.length() <= 10){
            return;
        }
        throw new IllegalArgumentException("[M-103] name length");
    }

    private void checkEditValidation(MemberVo vo) {
        if(vo.getPw() != null && !vo.getPw().isEmpty()){ checkPwValid(vo.getPw()); }
        if(vo.getName() != null && !vo.getName().isEmpty()){ checkNameValid(vo.getName()); }
    }

    public MemberVo login(MemberVo vo) {
        MemberVo dbVo = memberMapper.selectById(vo.getId());
        boolean isMatch = bCryptPasswordEncoder.matches(vo.getPw(), dbVo.getPw());
        return isMatch ? dbVo : null;
    }

    public boolean isIdDuplicate(String id) {
        MemberVo member = memberMapper.selectById(id);

        return member != null;
    }

    public FixedInfoVo selecFixedInfo(String no) {
        return memberMapper.selecFixedInfo(no);

    }

    public boolean isResidentDuplicate(String resident) {
        // DB에서 조회한 횟수가 0보다 크면 true(중복), 아니면 false(사용가능) 반환
        return memberMapper.checkResidentDuplicate(resident) > 0;
    }

    public FixedInfoVo selectHistory(FixedInfoVo fixedInfoVo) {
        return memberMapper.selectHistory(fixedInfoVo);
    }
}
