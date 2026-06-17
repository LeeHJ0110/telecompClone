package com.kh.six.board.notice;

import com.kh.six.employee.EmployeeVo;
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
@RequestMapping("/board/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeRestController {

    private final NoticeService noticeService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;

    @PostMapping
    public ResponseEntity<Map<String, String>> insert(@RequestBody NoticeVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        if(loginAdminVo == null){
            throw new IllegalStateException("login required");
        }

        vo.setWriterNo(loginAdminVo.getNo());


        int result = noticeService.insert(vo);

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
    public ResponseEntity<Map<String, Object>> selectList(@RequestParam(required = false , defaultValue = "1") int currentPage){
        int listCount = noticeService.selectCount();
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;
        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);
        List<NoticeVo> voList = noticeService.selectList(pvo);
        Map<String, Object> map = new HashMap<>();
        map.put("pvo" , pvo);
        map.put("voList" , voList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{no}")
    public ResponseEntity<Map<String, Object>> selectOne(@PathVariable String no){

        NoticeVo vo = noticeService.selectOne(no);
        NoticeVo prev = noticeService.selectPrev(no);
        NoticeVo next = noticeService.selectNext(no);

        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);
        map.put("prev", prev);
        map.put("next", next);

        return ResponseEntity.ok(map);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateByNo(@RequestBody NoticeVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        int result = noticeService.updateByNo(vo);
        if(result != 1){
            String errMsg = "[B-410] update err ...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result" , result);
        return ResponseEntity.ok(map);
    }


    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteByNo(@RequestBody NoticeVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        if(loginAdminVo == null){
            throw new IllegalStateException("login required");
        }

        int result = noticeService.deleteByNo(vo);
        if(result != 1){
            String errMsg = "[B-510] delete err ...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result" , result);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/search")
    public Map<String, Object> search(String type, String keyword){

        List<NoticeVo> voList = noticeService.search(type, keyword);

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return map;
    }


}