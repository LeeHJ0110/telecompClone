package com.kh.six.confirm.plan;

import com.kh.six.confirm.service.ConfirmPageVo;
import com.kh.six.planConfirm.FixedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmResultService {

    private final ConfirmResultMapper confirmResultMapper;

    public int selectCount(ConfirmPageVo pvo) {
        return confirmResultMapper.selectCount(pvo);
    }

    public List<ConfirmResultVo> selectList(ConfirmPageVo pvo) {
        return confirmResultMapper.selectList(pvo);
    }

    @Transactional
    public int confirmPlan(ConfirmResultVo crv, FixedInfoVo fixedInfoVo) {
        int result = confirmResultMapper.confirmPlan(crv);
        if(result != 1){
            String errMsg = "요금제 처리 실패";
            log.info(crv.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        // 승인하면 신청대로 서비스 만들어주기
        if(crv.getConfirmStatus().equals("Y")){

            if(crv.getSelectTable().equals("delete")){
                // delete 테이블
                // fixed_info > 비활성화, 해지날짜
                int resultd = confirmResultMapper.deleteFixed(fixedInfoVo);
                if(resultd != 1){
                    throw new IllegalStateException("해지 승인 실패");
                }

            }else if(crv.getSelectTable().equals("update")){
                // update 테이블
                // fixed_info > 요금제 번호 바꾸고
                int resultu = confirmResultMapper.updateFixed(fixedInfoVo);
                if(resultu != 1){
                    throw new IllegalStateException("수정 승인 실패");
                }
                // plan_contract > 요금제 번호 바꾸거
                int resultu2 = confirmResultMapper.updatePlanContract(fixedInfoVo);
                if(resultu2 != 1){
                    throw new IllegalStateException("사용내역 수정 실패");
                }

            }else{
                // insert 테이블
                // 그거에 맞는 Fixed_Info 테이블 'y'
                int resulti = confirmResultMapper.insertFixed(fixedInfoVo);
                if(resulti != 1){
                    throw new IllegalStateException("신규 승인 실패");
                }
                // plan_contract 테이블 만들기
                int resulti2 = confirmResultMapper.createPlanContract(fixedInfoVo);
                if(resulti2 != 1){
                    throw new IllegalStateException("사용내역 만들기 실패");
                }
                // bill 테이블 만들기
                int resulti3 = confirmResultMapper.createBill(fixedInfoVo);
                if(resulti3 != 1){
                    throw new IllegalStateException("bill 만들기 실패");
                }

                // member > 번호 바꾸기
                int resulti4 = confirmResultMapper.updateMemberPhone(fixedInfoVo);
                if(resulti4 != 1){
                    throw new IllegalStateException("멤버 번호 수정 실패");
                }
            }
        }
        return result;
    }

    public String selectMemberName(ConfirmPageVo pvo) {
        return confirmResultMapper.selectMemberName(pvo);
    }
}
