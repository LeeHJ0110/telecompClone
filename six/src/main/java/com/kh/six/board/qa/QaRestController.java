package com.kh.six.board.qa;


import com.kh.six.board.notice.NoticeVo;
import com.kh.six.employee.EmployeeVo;
import com.kh.six.member.MemberVo;
import com.kh.six.util.PageVo;
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
@RequestMapping("/board/qa")
@RequiredArgsConstructor
@Slf4j
public class QaRestController {

    private final QaService qaService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;

    @PostMapping
    public ResponseEntity<Map<String, String>> insert(@RequestBody QaVo vo , HttpSession session){
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");
        if(loginMemberVo == null){
            throw new IllegalStateException("login required");
        }

        vo.setWriterNo(loginMemberVo.getNo());


        int result = qaService.insert(vo);

        if(result != 1){
            String errMsg = "[B-100] insert err...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        Map<String, String> map = new HashMap<>();
        map.put("result", result+"");
        return ResponseEntity.ok( map );
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> selectList(
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            HttpSession session){

        Map<String, Object> map = new HashMap<>();

        int listCount = qaService.selectCount();
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;
        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);

        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        if(loginEmployeeVo == null){
            loginEmployeeVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        }

        if(loginEmployeeVo != null){
            List<QaVo> voList = qaService.selectListAdmin(pvo);
            map.put("pvo", pvo);
            map.put("voList", voList);
            return ResponseEntity.ok(map);
        }

// 회원
        MemberVo loginMemberVo = (MemberVo) session.getAttribute("loginMemberVo");

        if(loginMemberVo == null){
            throw new IllegalStateException("login required");
        }

        String readerNo = loginMemberVo.getNo();

        List<QaVo> voList = qaService.selectList(pvo, readerNo);

        map.put("pvo", pvo);
        map.put("voList", voList);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/{no}")
    public ResponseEntity<Map<String, Object>> selectOne(@PathVariable String no){

        QaVo vo = qaService.selectOne(no);

        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/search")
    public Map<String, Object> search(String type, String keyword){

        List<QaVo> voList = qaService.search(type, keyword);

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return map;
    }
}

