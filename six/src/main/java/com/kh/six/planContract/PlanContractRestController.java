package com.kh.six.planContract;

import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("planContract")
@RequiredArgsConstructor
@Slf4j
public class PlanContractRestController {

    private final PlanContractService planContractService;

    @GetMapping("/mypage/list")
    public ResponseEntity<HashMap<String, Object>> selectList(FixedInfoVo fixedInfoVo, HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        fixedInfoVo.setMemberNo(loginMemberVo.getNo());

        List<FixedInfoVo> list = planContractService.selectList(fixedInfoVo);

        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);

        return ResponseEntity.ok(map);
    }
}
