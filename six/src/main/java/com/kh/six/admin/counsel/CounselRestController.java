package com.kh.six.admin.counsel;


import com.kh.six.admin.employeemanage.EmployeeManageVo;
import com.kh.six.admin.membermanage.MemberManageService;
import com.kh.six.admin.membermanage.MemberManageVo;
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
@RequestMapping("/employee/counsel")
@RequiredArgsConstructor
@Slf4j
public class CounselRestController {

    private final CounselService counselService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;


    @PostMapping
    public ResponseEntity<Map<String, String>> insert(@RequestBody CounselVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        log.info("loginAdminVo = {}", loginAdminVo);
        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }
        if(loginEmployeeVo== null) {
            vo.setWriterNo(loginAdminVo.getNo());
        }else{
            vo.setWriterNo(loginEmployeeVo.getNo());
        }

        int result = counselService.insert(vo);

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
    public ResponseEntity<Map<String, Object>> selectList(@RequestParam(required = false , defaultValue = "1") int currentPage, HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        log.info("loginAdminVo = {}", loginAdminVo);
        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }


        int listCount = counselService.selectCount();
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;

        log.info("listCount = {}", listCount);
        log.info("pageLimit = {}", pageLimit);
        log.info("boardLimit = {}", boardLimit);

        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);
        List<CounselVo> voList = counselService.selectList(pvo);
        log.info("voList = {}", voList);
        Map<String, Object> map = new HashMap<>();
        map.put("pvo" , pvo);
        map.put("voList" , voList);


        return ResponseEntity.ok(map);
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkMember(
            @RequestParam String name,
            @RequestParam String phone,
            HttpSession session) {

        if(session.getAttribute("loginAdminVo") == null
                && session.getAttribute("loginEmployeeVo") == null) {
            throw new IllegalStateException("login required");
        }

        MemberVo memberVo = counselService.checkMember(name, phone);
        MemberVo memberVo1= counselService.checkMember2(name, phone);
        Map<String, Object> map = new HashMap<>();




        if(memberVo != null) {
            map.put("exists", true);
            map.put("memberNo", memberVo.getNo());
        } else {
            if(memberVo1 != null){
                map.put("exists", true);
                map.put("memberNo", memberVo1.getNo());
            }else{
                map.put("exists", false);
            }
        }

        return ResponseEntity.ok(map);
    }

    @GetMapping("/search")
    public Map<String, Object> search( String keyword, HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }

        List<CounselVo> voList = counselService.search(keyword);

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return map;
    }
}
