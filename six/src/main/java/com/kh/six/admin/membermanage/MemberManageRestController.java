package com.kh.six.admin.membermanage;


import com.kh.six.employee.EmployeeVo;
import com.kh.six.planConfirm.FixedInfoVo;
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
@RequestMapping("/employee/membermanage")
@RequiredArgsConstructor
@Slf4j
public class MemberManageRestController {

    private final MemberManageService memberManageService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;


    @GetMapping
    public ResponseEntity<Map<String, Object>> selectList(@RequestParam(required = false , defaultValue = "1") int currentPage, HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        log.info("loginAdminVo = {}", loginAdminVo);
        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }


        int listCount = memberManageService.selectCount();
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;

        log.info("listCount = {}", listCount);
        log.info("pageLimit = {}", pageLimit);
        log.info("boardLimit = {}", boardLimit);

        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);
        List<MemberManageVo> voList = memberManageService.selectList(pvo);
        Map<String, Object> map = new HashMap<>();
        map.put("pvo" , pvo);
        map.put("voList" , voList);
        log.info("voList = {}", voList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{no}")
    public ResponseEntity<Map<String, Object>> selectOne(
            @PathVariable String no,
            HttpSession session){

        log.info("=== selectOne start ===");
        log.info("받은 no = {}", no);

        if(session.getAttribute("loginAdminVo") == null
                && session.getAttribute("loginEmployeeVo") == null){
            throw new IllegalStateException("login required");
        }

        MemberManageVo vo = memberManageService.selectOneByNo(no);

        List<FixedInfoVo> fixedInfoList = memberManageService.selectFixedInfoList(no);

        FixedInfoVo fixedInfoVo = (FixedInfoVo) session.getAttribute("fixedInfoVo");

        log.info("최종 vo = {}", vo);
        log.info("fixedInfoList size = {}", fixedInfoList == null ? 0 : fixedInfoList.size());
        log.info("세션 fixedInfoVo = {}", fixedInfoVo);
        log.info("=== selectOne end ===");

        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);
        map.put("fixedInfoVo", fixedInfoVo);
        map.put("fixedInfoList", fixedInfoList);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/count/{no}")
    public ResponseEntity<Map<String, Object>> selectOneCount(@PathVariable String no, HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }
        MemberManageVo vo = new MemberManageVo();
        String addServiceCount = memberManageService.addServiceCount(no);
        String qaCount = memberManageService.qaCount(no);
        String counselCount = memberManageService.counselCount(no);
        vo.setAddServiceCount(addServiceCount);
        vo.setQaCount(qaCount);
        vo.setCounselCount(counselCount);


        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        return ResponseEntity.ok(map);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateByNo(@RequestBody MemberManageVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");
        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }
        int result = memberManageService.updateByNo(vo);
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
    public ResponseEntity<Map<String, Object>> deleteByNo(@RequestBody MemberManageVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        EmployeeVo loginEmployeeVo = (EmployeeVo) session.getAttribute("loginEmployeeVo");

        if(loginAdminVo == null && loginEmployeeVo== null){
            throw new IllegalStateException("login required");
        }

        int result = memberManageService.deleteByNo(vo);
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

        List<MemberManageVo> voList = memberManageService.search(type, keyword);

        Map<String, Object> map = new HashMap<>();
        map.put("voList", voList);

        return map;
    }

    @PostMapping("/select")
    public ResponseEntity<Map<String, Object>> selectMember(
            @RequestBody MemberSelectVo req,
            HttpSession session) {

        String no = req.getNo();
        String phone = req.getPhone();
        log.info("세션 no = {}", no);
        log.info("세션 phone = {}", phone);
        boolean noPhone = (phone == null || phone.trim().isEmpty());

        if(noPhone){
            session.removeAttribute("fixedInfoVo");
        }else{
            FixedInfoVo fixedInfoVo = memberManageService.selectFixedInfoOne(no, phone);
            session.setAttribute("fixedInfoVo", fixedInfoVo);
            log.info("세션 fixedInfoVo = {}", fixedInfoVo);
        }


        Map<String, Object> map = new HashMap<>();
        map.put("result", "ok");

        return ResponseEntity.ok(map);
    }

}
