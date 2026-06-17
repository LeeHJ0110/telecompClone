package com.kh.six.admin.store;

import com.kh.six.admin.employeemanage.EmployeeManageService;
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
@RequestMapping("/employee/employeemanage/admin/store")
@RequiredArgsConstructor
@Slf4j
public class StoreRestController {

    private final StoreService storeService;
    private final EmployeeManageService employeeManageService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;

    @GetMapping
    public ResponseEntity<Map<String, Object>> selectList(
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            HttpSession session
    ) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        int listCount = storeService.selectCount();
        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);
        List<StoreVo> voList = storeService.selectList(pvo);

        Map<String, Object> map = new HashMap<>();
        map.put("pvo", pvo);
        map.put("voList", voList);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/{no}")
    public ResponseEntity<Map<String, Object>> selectOne(@PathVariable String no, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        StoreVo vo = storeService.selectOne(no);

        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> insert(@RequestBody StoreVo vo, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        int result = storeService.insert(vo);
        if (result != 1) {
            String errMsg = "[STORE-100] insert fail ...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);

        return ResponseEntity.ok(map);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> update(@RequestBody StoreVo vo, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        int result = storeService.update(vo);
        if (result != 1) {
            String errMsg = "[STORE-200] update fail ...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);

        return ResponseEntity.ok(map);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteByNo(@RequestBody StoreVo vo, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        int result = storeService.deleteByNo(vo);
        if (result != 1) {
            String errMsg = "[STORE-300] delete fail ...";
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/search")
    public Map<String, Object> search(String type, String keyword, HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        List<StoreVo> voList = storeService.search(type, keyword);

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return map;
    }

    @GetMapping("/employee-list")
    public ResponseEntity<Map<String, Object>> selectEmployeeList(HttpSession session) {
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        List<EmployeeVo> voList = storeService.selectEmployeeList();

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return ResponseEntity.ok(map);
    }
}