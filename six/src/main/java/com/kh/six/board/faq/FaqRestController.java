package com.kh.six.board.faq;


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
@RequestMapping("/board/faq")
@RequiredArgsConstructor
@Slf4j
public class FaqRestController {

    private final FaqService faqService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;

    @PostMapping
    public ResponseEntity<Map<String, String>> insert(@RequestBody FaqVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        if(loginAdminVo == null){
            throw new IllegalStateException("login required");
        }

        vo.setWriterNo(loginAdminVo.getNo());


        int result = faqService.insert(vo);

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
            @RequestParam(required = false , defaultValue = "1") int currentPage,
            @RequestParam(required = false) String category
    ){

        int listCount = faqService.selectCount(category);

        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;

        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);

        List<FaqVo> voList = faqService.selectList(pvo , category);

        Map<String, Object> map = new HashMap<>();
        map.put("pvo" , pvo);
        map.put("voList" , voList);

        return ResponseEntity.ok(map);
    }

    @GetMapping("{no}")
    public ResponseEntity<HashMap<String, Object>> selectOne(@PathVariable String no){

        FaqVo vo = faqService.selectOne(no);

        if(vo == null){
            throw new IllegalStateException("faq not found");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("vo",vo);
        return ResponseEntity.ok(map);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateByNo(@RequestBody FaqVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        vo.setWriterNo(loginAdminVo.getNo());
        int result = faqService.updateByNo(vo);
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
    public ResponseEntity<Map<String, Object>> deleteByNo(@RequestBody FaqVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        if(loginAdminVo == null){
            throw new IllegalStateException("login required");
        }

        int result = faqService.deleteByNo(vo);
        if(result != 1){
            String errMsg = "[B-510] delete err ...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result" , result);
        return ResponseEntity.ok(map);
    }
}
