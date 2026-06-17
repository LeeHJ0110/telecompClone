package com.kh.six.member;

import com.kh.six.planConfirm.FixedInfoVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
@Slf4j
public class MemberRestController {

    private final MemberService memberService;


    @PostMapping("join")
    public ResponseEntity<HashMap<String, Object>> join(@RequestBody MemberVo vo) {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println("vo = " + vo);
        if (memberService.isResidentDuplicate(vo.getResident())) {
            map.put("x", "-1");
            map.put("msg", "duplicate_resident");
            return ResponseEntity.ok(map); // 여기서 바로 리턴 (가입 중단)
        }

        // 2. 중복이 아니면 정상적으로 회원가입 진행
        int result = memberService.join(vo);
        map.put("x", String.valueOf(result));

        return ResponseEntity.ok(map);
    }


    @PostMapping("login")
    public ResponseEntity.BodyBuilder login(@RequestBody MemberVo vo, HttpSession session) {
        MemberVo loginMemberVo = memberService.login(vo);
        if (loginMemberVo == null) {
            throw new IllegalArgumentException("[M-200] login err ...");
        }

        session.setAttribute("loginMemberVo", loginMemberVo);
        FixedInfoVo fixedInfoVo = memberService.selecFixedInfo(loginMemberVo.getNo());
        session.setAttribute("fixedInfoVo", fixedInfoVo);

        //사용내역 불러오기
        FixedInfoVo historyVo = memberService.selectHistory(fixedInfoVo);
        System.out.println("historyVo = " + historyVo);
        session.setAttribute("loginUsage", historyVo);

        System.out.println("fixedInfoVo = " + fixedInfoVo);
        return ResponseEntity.ok();
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/checkId")
    public ResponseEntity<HashMap<String, Boolean>> checkId(@RequestParam("id") String id) {
        // MemberService를 호출해서 DB에 아이디가 있는지 확인하는 로직
        boolean isDuplicate = memberService.isIdDuplicate(id);

        HashMap<String, Boolean> map = new HashMap<>();
        map.put("isDuplicate", isDuplicate);

        return ResponseEntity.ok(map);
    }
}
