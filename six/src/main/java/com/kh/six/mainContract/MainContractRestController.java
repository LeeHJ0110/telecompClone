package com.kh.six.mainContract;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.member.MemberService;
import com.kh.six.util.Authority;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/main-contract")
@Slf4j
@RequiredArgsConstructor
public class MainContractRestController {
    private final MainContractService mainContractService;

    @PostMapping("admin/insert")
    public ResponseEntity<HashMap<String, Object>> insert(@RequestBody MainContractVo vo , HttpSession session){
        EmployeeVo loginAdminVo = (EmployeeVo) session.getAttribute("loginAdminVo");
        if (loginAdminVo == null) {
            throw new IllegalStateException("관리자만 접근 가능");
        }
        int result = mainContractService.insert(vo);
        HashMap<String , Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }
    @GetMapping("list")
    public ResponseEntity<HashMap<String, Object>> selectList(){
        List<MainContractVo> voList = mainContractService.selectList();
        HashMap<String , Object> map = new HashMap<>();
        map.put("voList",voList);
        return ResponseEntity.ok(map);
    }
    @DeleteMapping("admin/delete")
    public ResponseEntity<HashMap<String, Object>> delete(@RequestBody MainContractVo vo){
        int result = mainContractService.delete(vo.getNo());
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }
    @PutMapping("admin/update")
    public ResponseEntity<HashMap<String, Object>> update(@RequestBody MainContractVo vo, HttpSession session){
        Authority.checkAdmin(session);
        int result = mainContractService.update(vo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }
    @GetMapping("{no}")
    public ResponseEntity<HashMap<String, Object>> selectOne(@PathVariable String no){
        MainContractVo vo = mainContractService.selecOne(no);
        HashMap<String, Object> map = new HashMap<>();
        map.put("vo",vo);
        return ResponseEntity.ok(map);
    }

}
