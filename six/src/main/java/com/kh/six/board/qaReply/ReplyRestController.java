package com.kh.six.board.qaReply;


import com.kh.six.employee.EmployeeVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board/qa/reply")
@RequiredArgsConstructor
@Slf4j
public class ReplyRestController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> insert(@RequestBody ReplyVo vo , HttpSession session){
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginEmployeeVo == null){
            loginEmployeeVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        }

        if(loginEmployeeVo == null){
            throw new IllegalStateException("[R-111] login require");
        }
        String loginMemberNo = loginEmployeeVo.getNo();
        vo.setEmployeeNo(loginMemberNo);
        int result = replyService.insert(vo);

        if(result != 1){
            throw new IllegalArgumentException("[R-110] reply insert fail ...");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        return ResponseEntity.ok(map);
    }

    //댓글 목록 조회
    @GetMapping
    public List<ReplyVo> selectList(ReplyVo vo){
        List<ReplyVo> voList = replyService.selectList(vo.getQaNo());
        return voList;
    }
}
