package com.kh.six.mypage;

import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import com.kh.six.util.Authority;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("mypage")
@RequiredArgsConstructor
@Slf4j
public class MypageRestController {

    private final MypageService mypageService;

    @PutMapping("/edit")
    public ResponseEntity<Map<String, Object>> edit(
            @RequestBody MemberVo vo
            , HttpSession session) {
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");

        if (loginMemberVo == null) {
            throw new IllegalStateException("[M401] need to login");
        }

        vo.setNo(loginMemberVo.getNo());
        int result = mypageService.edit(vo, loginMemberVo);
        if (result == 1) {
            loginMemberVo.setEmail(vo.getEmail());
            loginMemberVo.setAddress(vo.getAddress());
            loginMemberVo.setPhone(vo.getPhone());
            loginMemberVo.setAddress2(vo.getAddress2());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", result); // JS의 data.result와 이름 맞춤
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/myquit")
    public ResponseEntity<Map<String, Object>> quit(@RequestBody MemberVo vo, HttpSession session) {
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        vo.setNo(loginMemberVo.getNo());
        int result = mypageService.quit(vo);
        if (result != 1) {
            String errMsg = "[B-510] delete err ...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        session.invalidate();

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/plan")
    public ResponseEntity<Map<String, Object>> mypagePlan(HttpSession session) {
        Map<String, Object> checkmap = new HashMap<>();
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");

        Authority.checkMember(session);
        if (loginMemberVo == null) {
            checkmap.put("status", "fail");
            checkmap.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(checkmap);
        }
        String loginNo = loginMemberVo.getNo();
        Map<String, Object> map = mypageService.getMyPlanInfo(loginNo);


        map.put("status", "success");


        return ResponseEntity.ok(map);

    }

    @GetMapping("phoneToggle")
    @ResponseBody
    public Map<String, Object> phoneToggle(HttpSession session){
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");

        String memberNo = fixedInfoVo.getMemberNo();
        Map<String, Object> map = mypageService.getMyPlanInfo(memberNo);

        map.put("memberNo", memberNo);
        session.setAttribute("fixedInfoVo",fixedInfoVo);
//        System.out.println("map = " + map);

        return map;
    }

    @GetMapping("getPlanByPhone")
    @ResponseBody
    public FixedInfoVo getPlanByPhone(@RequestParam("phone") String phone, HttpSession session) {
        // 보안을 위해 세션에서 memberNo를 가져와 함께 체크하는 것이 좋습니다.

        FixedInfoVo vo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        String memberNo = vo.getMemberNo();
        // 서비스에서 해당 번호의 상세 정보를 가져옴
        FixedInfoVo detailVo = mypageService.getPlanDetailByPhone(phone, memberNo);

        // 선택된 정보를 세션에 업데이트 (필요한 경우)
        session.setAttribute("fixedInfoVo", detailVo);

//        System.out.println("detailVo = " + detailVo);
        return detailVo;
    }

    @GetMapping("send")
    public ResponseEntity<Map<String, Object>> send(@RequestBody FixedInfoVo fixedInfoVo, HttpSession session) {

        FixedInfoVo vo = mypageService.send(fixedInfoVo.getPlanName());

        HashMap<String, Object> map = new HashMap<>();
        map.put("result", 1);
        session.setAttribute("fixedInfoVo", vo);
        if (vo == null) {
            throw new IllegalStateException("send fail");
        }
        return ResponseEntity.ok(map);
    }

}



