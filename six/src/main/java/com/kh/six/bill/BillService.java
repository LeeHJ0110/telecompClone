package com.kh.six.bill;

import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BillService {
    private final BillMapper billMapper;
    
    // TotalBill을 업데이트 하며 바로 조회
    @Transactional
    public BillVo selectOne(BillVo vo) {
        // 입력 달이 없으면 자동으로 오늘을 기준으로 yyyymm을 만들기
        if(vo.getBillMonth() == null || vo.getBillMonth().isBlank()){
            LocalDate now = LocalDate.now();
            vo.setBillMonth(now.format(DateTimeFormatter.ofPattern("yyyyMM")));
        }

        log.warn("insert bill to: " + vo.getFixedNo() + ", mon: " + vo.getBillMonth());
        int result = billMapper.insertTotal(vo);
        if(result != 1){
            String errMsg = "bill 가격 입력 실패";
            log.info(vo.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return billMapper.selectOne(vo);
    }

    public List<String> getBillMonths(String fixedNo) {
        return billMapper.selectBillMonths(fixedNo);
    }

    public List<FixedInfoVo> selectPhones(MemberVo loginMemberVo) {
        return billMapper.selectPhones(loginMemberVo);
    }

    public FixedInfoVo selectFixedByNo(String no) {
        return billMapper.selectFixedByNo(no);
    }
}
