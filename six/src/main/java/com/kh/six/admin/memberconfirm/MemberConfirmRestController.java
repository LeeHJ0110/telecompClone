package com.kh.six.admin.memberconfirm;

import com.kh.six.planConfirm.FixedInfoVo;
import com.kh.six.util.PageVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employee/membermanage/confirm-list")
@RequiredArgsConstructor
@Slf4j
public class MemberConfirmRestController {

    private final MemberConfirmService memberConfirmService;

    @Value("${page.pageLimit}")
    private int pageLimit;

    @Value("${page.boardLimit}")
    private int boardLimit;


    @GetMapping
    public ResponseEntity<Map<String, Object>> selectList(
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            HttpSession session
    ) {
        Object loginAdminVo = session.getAttribute("loginAdminVo");
        Object loginEmployeeVo = session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        int listCount = memberConfirmService.selectCount();
        PageVo pvo = new PageVo(listCount, currentPage, pageLimit, boardLimit);

        Map<String, Object> resultMap = memberConfirmService.selectList(pvo);
        resultMap.put("pvo", pvo);

        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/select-one")
    public ResponseEntity<Map<String, String>> selectOne(
            @RequestParam String no,
            @RequestParam String category,
            HttpSession session
    ) {
        Object loginAdminVo = session.getAttribute("loginAdminVo");
        Object loginEmployeeVo = session.getAttribute("loginEmployeeVo");

        if (loginAdminVo == null && loginEmployeeVo == null) {
            throw new IllegalStateException("login required");
        }

        FixedInfoVo fixedInfoVo = memberConfirmService.selectOneForSession(no, category);

        if (fixedInfoVo == null) {
            throw new IllegalArgumentException("신청 정보를 찾을 수 없습니다.");
        }

        session.setAttribute("fixedInfoVo", fixedInfoVo);
        log.info("fixedInfoVo = {}", fixedInfoVo);

        Map<String, String> map = new HashMap<>();
        map.put("result", "ok");

        return ResponseEntity.ok(map);
    }
}
