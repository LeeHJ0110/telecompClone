package com.kh.six.admin.memberconfirm;

import com.kh.six.planConfirm.FixedInfoVo;
import com.kh.six.util.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberConfirmService {

    private final MemberConfirmMapper memberConfirmMapper;
    public int selectCount() {
        return memberConfirmMapper.selectCount();
    }

    public Map<String, Object> selectList(PageVo pvo) {
        Map<String, Object> map = new HashMap<>();

        map.put("planUpdateList", memberConfirmMapper.selectPlanUpdateList(pvo));
        map.put("planDeleteList", memberConfirmMapper.selectPlanDeleteList(pvo));
        map.put("planInsertList", memberConfirmMapper.selectPlanInsertList(pvo));
        map.put("serviceInsertList", memberConfirmMapper.selectServiceInsertList(pvo));
        map.put("serviceDeleteList", memberConfirmMapper.selectServiceDeleteList(pvo));

        return map;
    }

    public FixedInfoVo selectOneForSession(String no, String category) {
        return switch (category) {
            case "planInsert" -> memberConfirmMapper.selectPlanInsertOne(no);
            case "planUpdate" -> memberConfirmMapper.selectPlanUpdateOne(no);
            case "planDelete" -> memberConfirmMapper.selectPlanDeleteOne(no);
            case "serviceInsert" -> memberConfirmMapper.selectServiceInsertOne(no);
            case "serviceDelete" -> memberConfirmMapper.selectServiceDeleteOne(no);
            default -> throw new IllegalArgumentException("invalid category");
        };
    }
}
