package com.kh.six.serviceContract;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.planConfirm.FixedInfoVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("serviceContract")
@RequiredArgsConstructor
@Slf4j
public class ServiceContractRestController {
    private final ServiceContractService serviceContractService;


    @PostMapping
    public ResponseEntity<List<ServiceContractVo>> selectList(
            @RequestBody(required = false) ServiceContractVo vo,
            HttpSession session
    ){
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        if(fixedInfoVo == null){
            throw new IllegalStateException("조회하려는 청구정보 없음");
        }
        vo.setMemberNo(fixedInfoVo.getMemberNo());
        return ResponseEntity.ok(serviceContractService.selectList(vo));
    }

    // 계약 번호 가져와서 delDate 변경 신청
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteContract(
            @RequestBody ServiceContractVo vo,
            HttpSession session
    ){
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        vo.setFixedNo(fixedInfoVo.getNo());

        Map<String, Object> result = new HashMap<>();
        try {
            serviceContractService.deleteContract(vo);
            result.put("success", true);
            return ResponseEntity.ok(result);

        } catch (IllegalStateException e) {
            result.put("success", false);
            result.put("message", e.getMessage());

            return ResponseEntity.status(400).body(result);
        }
    }

    @PostMapping("selectCount")
    public ResponseEntity<Map<String,Object>> selectCount(HttpSession session){
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");

        ServiceContractVo dbVo = serviceContractService.selectCount(fixedInfoVo.getNo());

        Map<String, Object> map = new HashMap<>();
        map.put("serviceCount", dbVo.getServiceCount());
        map.put("freeServiceCount", dbVo.getFreeServiceCount());
        return ResponseEntity.ok(map);
    }
}
