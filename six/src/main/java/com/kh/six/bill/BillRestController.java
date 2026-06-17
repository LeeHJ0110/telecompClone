package com.kh.six.bill;

import com.kh.six.member.MemberVo;
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
@RequestMapping("bill")
@RequiredArgsConstructor
@Slf4j
public class BillRestController {
    private final BillService billService;

    //날짜, 받아와서 해당 달의 청구서(3월 -> 1/31~2/28) 조회
    @PostMapping
    public ResponseEntity<Map<String, Object>> selectOne(
            @RequestBody(required = false) BillVo vo,
            HttpSession session
    ){
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        if(fixedInfoVo == null){
            throw new IllegalStateException("조회하려는 청구정보 없음");
        }

        log.warn(fixedInfoVo.toString());
        BillVo useVo = new BillVo();
        useVo.setFixedNo(fixedInfoVo.getNo());
        useVo.setBillMonth(vo != null ? vo.getBillMonth() : null);

        log.warn(useVo.toString());
        BillVo dbVo = billService.selectOne(useVo);

        Map<String, Object> map = new HashMap<>();
        map.put("vo", dbVo);
        return ResponseEntity.ok(map);
    }

    @PostMapping("getMonth/{fixedNo}")
    public ResponseEntity<List<String>> getMonths(@PathVariable String fixedNo){
        return ResponseEntity.ok(billService.getBillMonths(fixedNo));
    }

    @PostMapping("selectPhones")
    public ResponseEntity<List<FixedInfoVo>> selectPhones(HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        List<FixedInfoVo> fixed =  billService.selectPhones(loginMemberVo);
        return ResponseEntity.ok(fixed);
    }

    @PostMapping("setFixed/{no}")
    public ResponseEntity<Map<String,Object>> setFixed(
            @PathVariable String no,
            HttpSession session
    ){
        FixedInfoVo vo = billService.selectFixedByNo(no);
        session.setAttribute("fixedInfoVo",vo);

        log.warn("bill change fixedNo:" + vo.getNo());

        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);
        return ResponseEntity.ok(map);
    }

    @PostMapping("getFixed")
    public ResponseEntity<FixedInfoVo> getFixed(HttpSession session){
        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");
        if(fixedInfoVo == null){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(fixedInfoVo);
    }


}
