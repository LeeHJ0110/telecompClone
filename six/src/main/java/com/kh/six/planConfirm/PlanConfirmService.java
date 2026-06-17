package com.kh.six.planConfirm;

import com.kh.six.confirm.plan.ConfirmResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PlanConfirmService {

    private final PlanConfirmMapper planConfirmMapper;


    @Transactional
    public int join(FixedInfoVo fixedInfoVo, ConfirmResultVo confirmResultVo) {
        //결제수단 저장
        int accountResult = planConfirmMapper.insertAccount(fixedInfoVo);
        if(accountResult != 1){
            throw new IllegalStateException("결제수단 등록 실패...");
        }

        //가입정보
        int result = planConfirmMapper.planJoin(fixedInfoVo);
        if(result != 1){
            throw new IllegalStateException("다시 작성 필요...");
        }

        confirmResultVo.setFixedNo(fixedInfoVo.getNo());

        //가입신청
        confirmResultVo.setFixedNo(fixedInfoVo.getNo());
        int result2 = planConfirmMapper.waitPlanJoin(confirmResultVo);
        if(result2 != 1){
            throw new IllegalStateException("가입신청 등록 실패 ...");
        }
        return result2;

    } //join

    public String generateUniquePhone(String lastDigit){
        String uniquePhone = "";
        boolean isDuplicate = true;

        while(isDuplicate){
            // 임의 중간 숫자 생성
            int middle = ThreadLocalRandom.current().nextInt(1000, 10000);
            String randomPhone  = String.format("010%04d", middle) + lastDigit;

            // DB 중복체크
            int count = planConfirmMapper.countByPhone(randomPhone);
            if(count == 0){
                uniquePhone = randomPhone; // 번호 확정
                isDuplicate = false; // 루프 탈출
            }
        }
        return uniquePhone;
    }//번호생성메서드


    public FixedInfoVo selectFixed(String phone, String no) {
        return planConfirmMapper.selectFixed(phone, no);
    } // 기존 가입내역 불러오기

    @Transactional
    public int update(ConfirmResultVo confirmResultVo) {
        //변경신청
        // 중복 신청건 검사
        checkPending(confirmResultVo);

        return planConfirmMapper.waitPlanUpdate(confirmResultVo);
    }

    //중복검사
    public int checkPending(ConfirmResultVo confirmResultVo){
        int pendingCount = planConfirmMapper.countPendingRequest(confirmResultVo.getFixedNo());
        if(pendingCount > 0) {
            throw new IllegalStateException("ALREADY_PENDING");
        }
        return pendingCount; // 0이 정상
    }

    @Transactional
    public int delete(ConfirmResultVo confirmResultVo) {
        //해지신청
        int pendingCount = planConfirmMapper.countPendingRequest(confirmResultVo.getFixedNo());
        if(pendingCount > 0){
            throw new IllegalStateException("ALREADY_PENDING");
        }
        return planConfirmMapper.waitPlanDelete(confirmResultVo);

    }

    //결제정보 리스트업
    public List<PaymentVo> getPaymentList() {
        return planConfirmMapper.getPaymentList();
    }

    //요금제이름, 약정기간 조회
    public FixedInfoVo getDetails(FixedInfoVo fixedInfoVo) {
        //요금제 가져오기
        FixedInfoVo planDetailsVo = planConfirmMapper.getPlanDetails(fixedInfoVo);

        //약정기간 가져오기
        if(fixedInfoVo.getMainContractNo() != null && !fixedInfoVo.getMainContractNo().isEmpty()){
            String term = planConfirmMapper.getTerm(fixedInfoVo);
            if(planDetailsVo != null){
                planDetailsVo.setTerm(term);
            }
        }
        return planDetailsVo;
    }


    public FixedInfoVo showAccount(FixedInfoVo fixedInfoVo) {
        return planConfirmMapper.showAccount(fixedInfoVo);
    }

    @Transactional
    public int updateAccount(FixedInfoVo fixedInfoVo) {
        return planConfirmMapper.updateAccount(fixedInfoVo);
    }

    public List<String> getPhoneList(String no) {
        return planConfirmMapper.getPhoneList(no);
    }
}//class




