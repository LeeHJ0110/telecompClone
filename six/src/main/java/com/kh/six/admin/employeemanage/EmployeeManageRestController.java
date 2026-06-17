package com.kh.six.admin.employeemanage;

import com.kh.six.board.notice.NoticeVo;
import com.kh.six.employee.EmployeeVo;
import com.kh.six.util.PageVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee/employeemanage")
@RequiredArgsConstructor
@Slf4j
public class EmployeeManageRestController {

    private final EmployeeManageService employeeManageService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;

    @PostMapping("/admin/insert")
    public ResponseEntity<HashMap<String, String>> insert(
            EmployeeManageVo vo
            , @RequestParam(required = false) MultipartFile profile ,HttpSession session
    ) throws IOException {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }

        log.info("===== insert start =====");
        log.info("vo = {}", vo);
        log.info("profile = {}", profile);

        int result = employeeManageService.insert(vo, profile);

        log.info("===== service result = {} =====", result);

        HashMap<String, String> map = new HashMap<>();
        map.put("x" , String.valueOf(result));

        log.info("===== insert end =====");
        return ResponseEntity.ok(map);
    }//method

    @GetMapping
    public ResponseEntity<Map<String, Object>> selectList(@RequestParam(required = false , defaultValue = "1") int currentPage, HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }

        int listCount = employeeManageService.selectCount();
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;
        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);
        List<EmployeeManageVo> voList = employeeManageService.selectList(pvo);
        Map<String, Object> map = new HashMap<>();
        map.put("pvo" , pvo);
        map.put("voList" , voList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{no}")
    public ResponseEntity<Map<String, Object>> selectOne(@PathVariable String no, HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }

        EmployeeManageVo vo = employeeManageService.selectOne(no);

        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        return ResponseEntity.ok(map);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateByNo(@RequestBody EmployeeManageVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }
        int result = employeeManageService.updateByNo(vo);
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
    public ResponseEntity<Map<String, Object>> deleteByNo(@RequestBody EmployeeManageVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }



        int result = employeeManageService.deleteByNo(vo);
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
    public Map<String, Object> search(String type, String keyword, HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }

        List<EmployeeManageVo> voList = employeeManageService.search(type, keyword);

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return map;
    }

    @GetMapping("/job-list")
    public ResponseEntity<Map<String, Object>> selectJobList(HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        List<EmployeeManageVo> voList = employeeManageService.selectJobList();

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/agency-list")
    public ResponseEntity<Map<String, Object>> selectAgencyList(HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        List<EmployeeManageVo> voList = employeeManageService.selectAgencyList();

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return ResponseEntity.ok(map);
    }

}
