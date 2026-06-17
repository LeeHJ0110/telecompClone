package com.kh.six.addService;

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
@RequiredArgsConstructor
@RequestMapping("api/add-service")
@Slf4j
public class AddServiceRestController {
    private final AddServiceService addServiceService;

    @Value("${page.pageLimit}")
    private int pageLimit;
    @Value("${page.boardLimit}")
    private int boardLimit;

    @GetMapping
    public ResponseEntity<Map<String, Object>> selectList(int currentPage,String category){
        int listCount = addServiceService.selectCount(category);
        int pageLimit = this.pageLimit;
        int boardLimit = this.boardLimit;

        PageVo pvo = new PageVo(listCount, currentPage, pageLimit,boardLimit);

        List<AddServiceVo> voList = addServiceService.selectList(pvo,category);
        Map<String , Object> map = new HashMap<>();
        map.put("voList",voList);
        map.put("pvo",pvo);
        return ResponseEntity.ok(map);
    }
    @DeleteMapping("/admin/{no}")
    public ResponseEntity<HashMap<String, Object>> delete(@PathVariable String no, HttpSession session){
        Authority.checkAdmin(session);
        int result = addServiceService.delete(no);
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/admin/insert")
    public ResponseEntity<HashMap<String, Object>> insert(@RequestBody AddServiceVo vo, HttpSession session){
        Authority.checkAdmin(session);
        int result = addServiceService.insert(vo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }
    @PutMapping("/admin/update")
    public ResponseEntity<HashMap<String, Object>> update(@RequestBody AddServiceVo vo, HttpSession session){
        Authority.checkAdmin(session);
        int result = addServiceService.update(vo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",result);
        return ResponseEntity.ok(map);
    }
    @GetMapping("{no}")
    public ResponseEntity<HashMap<String, Object>> selectOne(@PathVariable String no, HttpSession session){
        AddServiceVo vo = addServiceService.selectOne(no);
        HashMap<String, Object> map = new HashMap<>();
        map.put("vo",vo);
        return ResponseEntity.ok(map);
    }


}
