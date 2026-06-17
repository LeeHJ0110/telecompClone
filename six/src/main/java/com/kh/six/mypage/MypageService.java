package com.kh.six.mypage;

import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MypageService {

    private final MypageMapper mypageMapper;


    @Transactional
    public int edit(MemberVo vo, MemberVo loginMemberVo) {
        return mypageMapper.edit(vo,loginMemberVo);
    }

    @Transactional
    public int quit(MemberVo vo) {
        return mypageMapper.quit(vo);
    }


    public Map<String, Object> getMyPlanInfo(String memberNo) {
        // 1. 기본 플랜 정보 조회
        FixedInfoVo vo = mypageMapper.getMyPlanInfo(memberNo);

        // 2. DB에서 전화번호 목록 조회 (0개, 1개, 혹은 여러 개가 담길 수 있음)
        List<String> phoneList = mypageMapper.getMyPhoneNo(memberNo);

        // 3. 결과를 담을 Map 생성
        Map<String, Object> map = new HashMap<>();

        // 4. 데이터 저장
        map.put("vo", vo);

        /* * [핵심] 몇 개가 나오든 'phoneList'라는 키에 리스트 전체를 담습니다.
         * 이렇게 담으면 1개일 때도 리스트(size=1), 여러 개일 때도 리스트(size=N)로 유지되어
         * 받는 쪽(Controller나 JSP)에서 일관성 있게 처리할 수 있습니다.
         */
        map.put("phoneList", phoneList);

        return map;
    }



    public FixedInfoVo getPlanDetailByPhone(String phone, String memberNo) {
        return mypageMapper.getPlanDetailByPhone(phone, memberNo);
    }

    public FixedInfoVo send(String planName) {
        return mypageMapper.send(planName);
    }

}
