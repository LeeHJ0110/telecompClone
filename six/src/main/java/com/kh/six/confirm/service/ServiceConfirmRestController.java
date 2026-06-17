package com.kh.six.confirm.service;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import com.kh.six.util.Authority;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("confirm/service")
@RequiredArgsConstructor
@Slf4j
public class ServiceConfirmRestController {

    private final ServiceConfirmService serviceConfirmService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;

    // 고객 번호, 조회하는 테이블(기본신규), 페이지 번호(기본1) 받아서 신규 신청내역 구성하기
    @PostMapping
    public ResponseEntity<Map<String, Object>> selectServiceContract(
            @RequestBody ConfirmPageVo pvo,
            HttpSession session
    ){
        // 세션 채크
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        pvo.setMemberNo(fixedInfoVo.getMemberNo());
        if(loginEmployeeVo == null){loginEmployeeVo = (EmployeeVo) session.getAttribute("loginAdminVo");}
        pvo.setEmployee(loginEmployeeVo != null);

        if(pvo.getMemberNo() == null){
            String errMsg = "조회하려는 맴버 없음";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        String name = serviceConfirmService.getName(pvo.getMemberNo());

        // 신청 내역의 총 갯수 가져오기
        int listCount = serviceConfirmService.selectCount(pvo);
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;
        pvo.setConfirmPageVo(listCount, pageLimit, boardLimit);

        // 신청내역의 한 해당 페이지 가져오기
        List<ServiceConfirmVo> voList = serviceConfirmService.selectList(pvo);
        Map<String, Object> map = new HashMap<>();
        map.put("pvo" , pvo);
        map.put("voList" , voList);
        map.put("name", name);
        return ResponseEntity.ok(map);
    }

    // 신청 번호, 조회 테이블, 변경 내용을 받아 신청 내역 수정
    @PostMapping("confirming")
    public ResponseEntity<Map<String, Object>> confirmService(
            @RequestBody ServiceConfirmVo scv,
            HttpSession session
    ){
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        scv.setFixedNo(fixedInfoVo.getNo());
        if(loginEmployeeVo != null){
            scv.setEmployeeNo(loginEmployeeVo.getNo());
        }else if(loginAdminVo != null){
            scv.setEmployeeNo(loginAdminVo.getNo());
        }else{
            String errMsg = "관리자 계정 아님";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        log.warn("do confirm" + scv);
        int result = serviceConfirmService.confirmService(scv);
        Map<String, Object> map = new HashMap<>();
        map.put("result" , result);
        log.warn(map.toString());
        return ResponseEntity.ok(map);
    }

    // 번호, 부가서비스 번호 OR 계약 번호, 조회 테이블 받아서 컨펌 생성
    @PostMapping("insert")
    public ResponseEntity<Map<String, Object>> serviceInsert(
            @RequestBody ServiceConfirmVo scv,
            HttpSession session
    ){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        scv.setMemberNo(loginMemberVo.getNo());
        scv.setFixedNo(fixedInfoVo.getNo());
        int result = serviceConfirmService.confirmInsert(scv);
        Map<String, Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }

    //서비스 번호, 전화번호(없으면 대표번호) 받아서 화면 구성
    @PostMapping("detail")
    public ResponseEntity<Map<String, Object>> serviceDetail(
            @RequestBody ServiceConfirmVo scv,
            HttpSession session
    ){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        scv.setMemberNo(loginMemberVo.getNo());
        scv.setFixedNo(fixedInfoVo.getNo());
        ServiceConfirmJoinVo sjv = serviceConfirmService.detail(scv);

        log.warn(sjv.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("sjv", sjv);
        return ResponseEntity.ok(map);
    }

    // fixedNo, serviceNo 받아서 입력
    @PutMapping("insert")
    public ResponseEntity<Map<String, Object>> insertJoinConfirm(
            @RequestBody ServiceConfirmVo scv,
            HttpSession session
    ){
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        scv.setFixedNo(fixedInfoVo.getNo());

        Map<String, Object> result = new HashMap<>();
        try {
            serviceConfirmService.insertJoinConfirm(scv);
            result.put("success", true);
            return ResponseEntity.ok(result);

        } catch (IllegalStateException e) {
            result.put("success", false);
            result.put("message", e.getMessage());

            return ResponseEntity.status(400).body(result);
        }
    }


}
