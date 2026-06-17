package com.kh.six.planConfirm;

import com.kh.six.confirm.plan.ConfirmResultVo;
import com.kh.six.member.MemberVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("planConfirm")
@Slf4j
@RequiredArgsConstructor
public class PlanConfirmRestController {

    private final PlanConfirmService planConfirmService;

    @PutMapping("/mypage/update")
    public ResponseEntity<HashMap<String, String>> update(HttpSession session, @RequestBody FixedInfoVo updateFixedInfoVo){
        HashMap<String, String> map = new HashMap<>();
        try{
            MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");

            //변경요금제, 전화번호 가져오기
            String phone = updateFixedInfoVo.getPhone();
            String newPlanNo = updateFixedInfoVo.getPlanNo();

            // 기존가입내역
            FixedInfoVo currentFixed = planConfirmService.selectFixed(phone, loginMemberVo.getNo());
            if(currentFixed == null){
                throw new IllegalStateException("가입된 정보 없음 ~");
            }

            //변경 신청서
            ConfirmResultVo confirmResultVo = new ConfirmResultVo();
            confirmResultVo.setFixedNo(currentFixed.getNo()); // 기존 가입내역의 pk
            confirmResultVo.setNewPlanNo(newPlanNo); // 새로 바꿀 요금제 pk ~~~

            //변경신청서
            int result = planConfirmService.update(confirmResultVo);

            map.put("data", String.valueOf(result));
            return ResponseEntity.ok(map);
        }catch(IllegalStateException e){
            if("ALREADY_PENDING".equals(e.getMessage())){
                map.put("data", "pending");
                return ResponseEntity.ok(map);
            }
            throw e;
        }
    }

    //신청 건 중복검사
    @GetMapping("/mypage/pending")
    public ResponseEntity<HashMap<String, Object>> checkPending(HttpSession session){
        HashMap<String, Object> map = new HashMap<>();
        try{
            FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
            ConfirmResultVo confirmResultVo = new ConfirmResultVo();
            confirmResultVo.setFixedNo(fixedInfoVo.getNo());
            int result = planConfirmService.checkPending(confirmResultVo);

            map.put("result", result);
            return ResponseEntity.ok(map);

        } catch (IllegalStateException e) {
            if("ALREADY_PENDING".equals(e.getMessage())){
                map.put("result", "pending");
                return ResponseEntity.ok(map);
            }
            throw e;
        }
    }

    @PostMapping("/mypage/join")
    public ResponseEntity<HashMap<String, String>> join(@RequestBody FixedInfoVo fixedInfoVo, HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");

        fixedInfoVo.setMemberNo(loginMemberVo.getNo());

        com.kh.six.confirm.plan.ConfirmResultVo confirmResultVo = new com.kh.six.confirm.plan.ConfirmResultVo();
        int result = planConfirmService.join(fixedInfoVo, confirmResultVo);

        HashMap<String, String> map = new HashMap<>();
        map.put("result", String.valueOf(result));
        return ResponseEntity.ok(map);
    }

    // 로컬스토리지에서 요금제이름, 약정개월수 가져오기
    @GetMapping("/plan/details")
    public ResponseEntity<FixedInfoVo> getPlanDetails(FixedInfoVo fixedInfoVo) {
         FixedInfoVo resp = planConfirmService.getDetails(fixedInfoVo);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("generate-phone")
    public ResponseEntity<String> generateUniquePhone(@RequestParam("lastDigit") String lastDigit){
        String newPhone = planConfirmService.generateUniquePhone(lastDigit);
        return ResponseEntity.ok(newPhone);
    }

    @DeleteMapping("mypage/delete")
    public ResponseEntity<HashMap<String, Object>> delete(HttpSession session, @RequestBody FixedInfoVo fixedInfoVo){
        HashMap<String, Object> map = new HashMap<>();
        try{
            MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
            fixedInfoVo.setMemberNo(loginMemberVo.getNo());

            // 기존가입내역
            FixedInfoVo originFixedInfoVo = planConfirmService.selectFixed(fixedInfoVo.getPhone(), loginMemberVo.getNo());
            if(originFixedInfoVo == null){
                throw new IllegalStateException("가입된 정보 없음 ~");
            }

            // 삭제 신청
            ConfirmResultVo confirmResultVo = new ConfirmResultVo();
            confirmResultVo.setFixedNo(originFixedInfoVo.getNo());

            int result = planConfirmService.delete(confirmResultVo);
            if(result != 1){
                throw new IllegalStateException("my plan delete fail...");
            }

            map.put("result", result);
            return ResponseEntity.ok(map);
        }catch(IllegalStateException e){
            if("ALREADY_PENDING".equals(e.getMessage())){
                map.put("result", "pending");
                return ResponseEntity.ok(map);
            }
            throw e;
        }
    }

}
