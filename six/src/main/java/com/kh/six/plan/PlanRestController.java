package com.kh.six.plan;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.util.Authority;
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
@RequestMapping("api/plan")
@RequiredArgsConstructor
@Slf4j
public class PlanRestController {
    private final PlanService planService;

    @Value("${page.pageLimit}")
    private int pageLimit;
    @Value("${page.boardLimit}")
    private int boardLimit;

    @PostMapping("admin/insert")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody PlanVo vo, HttpSession session){
        Authority.checkAdmin(session);
        int result = planService.insert(vo);
        Map<String, Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> selectList(int currentPage, String sort){
        int listCount = planService.selectCount();
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;

        PageVo pvo = new PageVo(listCount, currentPage, pageLimit,boardLimit);

        List<PlanVo> voList = planService.selectList(pvo,sort);
        Map<String , Object> map = new HashMap<>();
        map.put("voList",voList);
        map.put("pvo",pvo);
        return ResponseEntity.ok(map);
    }

    @PutMapping("admin/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody PlanVo vo, HttpSession session){
        Authority.checkAdmin(session);
        int result = planService.update(vo);
        System.out.println("result = " + result);
        Map<String,Object> map = new HashMap<>();
        map.put("result", result);
        return ResponseEntity.ok(map);
    }
    @DeleteMapping("admin/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody PlanVo vo, HttpSession session){
        Authority.checkAdmin(session);
        String no = vo.getNo();
        int result = planService.delete(no);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);

    }
    @GetMapping("detail/{no}")
    public ResponseEntity<HashMap<String, Object>> selectOne(@PathVariable String no , HttpSession session){
        PlanVo vo = planService.selectOne(no);
        HashMap<String, Object> map = new HashMap<>();
        map.put("vo",vo);
        return ResponseEntity.ok(map);
    }


}

















