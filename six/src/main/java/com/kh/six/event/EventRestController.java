package com.kh.six.event;

import com.kh.six.member.MemberVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/event")
@Slf4j
public class EventRestController {
    private final EventService eventService;

    @PostMapping("mypage/attendance")
    public ResponseEntity<HashMap<String, Object>> insertAttendance(HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        String no = loginMemberVo.getNo();
        int result = eventService.insertAttendance(no);
        int count = eventService.checkCount(no);
        HashMap<String , Object> map = new HashMap<>();
        map.put("count",count);
        return ResponseEntity.ok(map);
    }
    @GetMapping("mypage/attendance")
    public ResponseEntity<HashMap<String, Object>> check(HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        String no = loginMemberVo.getNo();
        int count = eventService.checkCount(no);
        HashMap<String , Object> map = new HashMap<>();
        map.put("count",count);
        return ResponseEntity.ok(map);
    }
}
