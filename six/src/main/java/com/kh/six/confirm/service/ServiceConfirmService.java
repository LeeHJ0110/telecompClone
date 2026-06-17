package com.kh.six.confirm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ServiceConfirmService {
    private final ServiceConfirmMapper serviceConfirmMapper;

    public int selectCount(ConfirmPageVo pvo) {
        return serviceConfirmMapper.selectCount(pvo);
    }

    public List<ServiceConfirmVo> selectList(ConfirmPageVo pvo) {
        return serviceConfirmMapper.selectList(pvo);
    }

    public int confirmService(ServiceConfirmVo scv) {
        int result = serviceConfirmMapper.confirmService(scv);

        if(result != 1){
            String errMsg = "부가서비스 처리 실패";
            log.info(scv.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        if(scv.getConfirmStatus().equals("Y")){
            String selectTable = scv.getSelectTable();
            if(selectTable == "delete"){
                log.warn(scv.getNo());
                int result2 = serviceConfirmMapper.deleteServiceContract(scv);
                if(result2 != 1){
                    String errMsg = "부가서비스 계약 삭제 실패";
                    log.info(scv.toString());
                    log.error(errMsg);
                    throw new IllegalStateException(errMsg);
                }
                return result2;
            }else{
                // 승인하면 신청대로 서비스 만들어주기
                log.warn("신청중");
                int result2 = serviceConfirmMapper.insertServiceContract(scv);
                log.warn("신청실패");
                if(result2 != 1){
                    String errMsg = "부가서비스 계약 생성 실패";
                    log.info(scv.toString());
                    log.error(errMsg);
                    throw new IllegalStateException(errMsg);
                }
                return result2;
            }
        }
        return result;
    }

    public int confirmInsert(ServiceConfirmVo scv) {
        int result = serviceConfirmMapper.confirmInsert(scv);
        if(result != 1){
            String errMsg = "부가서비스 신청 실패";
            log.info(scv.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return result;
    }

    public ServiceConfirmJoinVo detail(ServiceConfirmVo scv) {
        ServiceConfirmJoinVo sjv = serviceConfirmMapper.detail(scv);
        String checkD = serviceConfirmMapper.checkD(scv);
        if(checkD != null) {
            log.warn("해당 서비스 중복됨");
            sjv.setHasConfirm("dup");
        }
        return sjv;
    }

    public int insertJoinConfirm(ServiceConfirmVo scv) {
        String checkD = serviceConfirmMapper.checkD(scv);
        if(checkD != null){
            String errMsg = "이미 신청한 내용";
            log.info(scv.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        int result = serviceConfirmMapper.insertJoinConfirm(scv);
        if(result != 1){
            String errMsg = "서비스 가입 선청 실패";
            log.info(scv.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return result;
    }

    public String getName(String memberNo) {
        return serviceConfirmMapper.getName(memberNo);
    }
}
