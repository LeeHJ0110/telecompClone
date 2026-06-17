package com.kh.six.employee;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @PostMapping("join")
    public ResponseEntity<HashMap<String, String>> join(
            EmployeeVo vo
            , @RequestParam(required = false) MultipartFile profile
    ) throws IOException {
        System.out.println("vo = " + vo);
        int result = employeeService.join(vo, profile);

        HashMap<String, String> map = new HashMap<>();
        map.put("x" , String.valueOf(result));
        return ResponseEntity.ok(map);
    }//method

    @PostMapping("login")
    public ResponseEntity.BodyBuilder login(@RequestBody EmployeeVo vo , HttpSession session){
        EmployeeVo loginEmployeeVo = employeeService.login(vo);
        if(loginEmployeeVo == null){
            throw new IllegalArgumentException("[M-200] login err ...");
        }
        if("1".equals(loginEmployeeVo.getAgencyNo())){
            session.setAttribute("loginAdminVo" , loginEmployeeVo);
        }else{
            session.setAttribute("loginEmployeeVo" , loginEmployeeVo);
        }

        return ResponseEntity.ok();
    }

    @GetMapping("/checkId")
    public ResponseEntity<HashMap<String, Boolean>> checkId(@RequestParam("id") String id) {
        // MemberService를 호출해서 DB에 아이디가 있는지 확인하는 로직
        boolean isDuplicate = employeeService.isIdDuplicate(id);

        HashMap<String, Boolean> map = new HashMap<>();
        map.put("isDuplicate", isDuplicate);

        return ResponseEntity.ok(map);
    }

}//class