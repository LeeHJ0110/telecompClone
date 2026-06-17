package com.kh.six.confirm.plan;

import com.kh.six.confirm.service.ConfirmPageVo;
import com.kh.six.employee.EmployeeVo;
import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/confirm/plan")
@Slf4j
@RequiredArgsConstructor
public class ConfirmResultRestController {

    private final ConfirmResultService confirmResultService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;


    // 고객 번호, 조회하는 테이블(기본신규), 페이지 번호(기본1) 받아서 신규 신청내역 구성하기
    @PostMapping
    public ResponseEntity<Map<String, Object>> selectPlanContract(
            @RequestBody ConfirmPageVo pvo,
            HttpSession session
    ){
        // 세션 채크
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        if(fixedInfoVo != null){pvo.setMemberNo(fixedInfoVo.getMemberNo());}
        if(loginEmployeeVo == null){loginEmployeeVo = (EmployeeVo) session.getAttribute("loginAdminVo");}
        pvo.setEmployee(loginEmployeeVo != null);

        if(pvo.getMemberNo() == null){
            String errMsg = "조회하려는 맴버 없음";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        //멤버 이름 조회
        String memberName = confirmResultService.selectMemberName(pvo);

        // 신청 내역의 총 갯수 가져오기
        int listCount = confirmResultService.selectCount(pvo);
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;
        pvo.setConfirmPageVo(listCount, pageLimit, boardLimit);

        // 신청내역의 한 해당 페이지 가져오기
        List<ConfirmResultVo> voList = confirmResultService.selectList(pvo);

        //신청내역 status 확인

        Map<String, Object> map = new HashMap<>();
        map.put("memberName", memberName);
        map.put("pvo" , pvo);
        map.put("voList" , voList);

        log.warn(pvo.toString());

        return ResponseEntity.ok(map);
    }

    // 신청 번호, 조회 테이블, 변경 내용을 받아 신청 내역 수정
    @PostMapping("/employee/confirming")
    public ResponseEntity<Map<String, Object>> confirmPlan(
            @RequestBody ConfirmResultVo crv,
            HttpSession session
    ){
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        if(loginEmployeeVo != null){
            crv.setEmployeeNo(loginEmployeeVo.getNo());
        }else if(loginAdminVo != null){
            crv.setEmployeeNo(loginAdminVo.getNo());
        }else{
            String errMsg = "관리자 계정 아님";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        fixedInfoVo.setPhone(crv.getPhone());

        int result = confirmResultService.confirmPlan(crv, fixedInfoVo);
        Map<String, Object> map = new HashMap<>();
        map.put("result" , result);
        return ResponseEntity.ok(map);
    }

}
