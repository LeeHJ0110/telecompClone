package com.kh.six.planConfirm;

import com.kh.six.member.MemberVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("account")
@Slf4j
@RequiredArgsConstructor
public class AccountRestController {

    private final PlanConfirmService planConfirmService;


    // 등록한 결제정보 가져오기
    @PostMapping("mypage/showAccount")
    public ResponseEntity<HashMap<String, Object>> showAccountPost(@RequestBody FixedInfoVo fixedInfoVo, HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        fixedInfoVo.setMemberNo(loginMemberVo.getNo());
        FixedInfoVo accountVo = planConfirmService.showAccount(fixedInfoVo);

        HashMap<String, Object> map = new HashMap<>();
        map.put("accountVo", accountVo);
        return ResponseEntity.ok(map);
    }

    @PostMapping("updateAccount")
    public ResponseEntity<HashMap<String, Object>> updateAccount(@RequestBody FixedInfoVo fixedInfoVo, HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        fixedInfoVo.setMemberNo(loginMemberVo.getNo());
        int result = planConfirmService.updateAccount(fixedInfoVo);

        HashMap<String, Object> map = new HashMap<>();
        map.put("result", result);
        return ResponseEntity.ok(map);
    }

    //화면 초기 보여주기용
    @GetMapping("mypage/showAccount")
    public ResponseEntity<HashMap<String, Object>> showAccountGet(FixedInfoVo fixedInfoVo, HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        fixedInfoVo.setMemberNo(loginMemberVo.getNo());

        FixedInfoVo accountVo = planConfirmService.showAccount(fixedInfoVo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountVo", accountVo);
        return ResponseEntity.ok(map);
    }

    @GetMapping("mypage/getPhoneList")
    public ResponseEntity<List<String>> getPhoneList(HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        List<String> list = planConfirmService.getPhoneList(loginMemberVo.getNo());
        return ResponseEntity.ok(list);
    }

}
