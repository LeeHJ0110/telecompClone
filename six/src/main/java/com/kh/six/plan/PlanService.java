package com.kh.six.plan;

import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import com.kh.six.util.PageVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PlanService {
    private final PlanMapper planMapper;

    public int selectCount() {
        return planMapper.selectCount();
    }

    @Transactional
    public int insert(PlanVo vo) {
        return planMapper.insert(vo);
    }

    public List<PlanVo> selectList(PageVo pvo, String sort) {
        if (sort.equals("latest")){
            return planMapper.selectListLatest(pvo);
        }else if (sort.equals("popular")){
            return planMapper.selectListPopular(pvo);
        }else if (sort.equals("priceAsc")){
            return planMapper.selectListPriceAsc(pvo);
        }else{
            return planMapper.selectListPriceDesc(pvo);
        }

    }
    @Transactional
    public int update(PlanVo vo) {
        return planMapper.update(vo);
    }

    @Transactional
    public int delete(String no) {
        return planMapper.delete(no);
    }

    public PlanVo selectOne(String no) {
        return planMapper.selectOne(no);
    }

}
