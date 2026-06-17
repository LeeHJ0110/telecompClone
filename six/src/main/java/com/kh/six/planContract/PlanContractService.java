package com.kh.six.planContract;

import com.kh.six.planConfirm.FixedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PlanContractService {

    private final PlanContractMapper planContractMapper;

    public List<FixedInfoVo> selectList(FixedInfoVo fixedInfoVo) {
        return planContractMapper.selectList(fixedInfoVo);
    }
}
