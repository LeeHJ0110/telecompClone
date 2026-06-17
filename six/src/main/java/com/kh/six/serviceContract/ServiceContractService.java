package com.kh.six.serviceContract;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ServiceContractService {
    private final ServiceContractMapper serviceContractMapper;

    public List<ServiceContractVo> selectList(ServiceContractVo vo) {
        LocalDate now = LocalDate.now();
        vo.setUsedMonth(now.format(DateTimeFormatter.ofPattern("yyyyMM")));

        List<ServiceContractVo> voList = serviceContractMapper.selectList(vo);
        // 중복인 서비스 리스트
        List<String> confirmedList = serviceContractMapper.checkList(vo);

        for(ServiceContractVo scv : voList){
            if(confirmedList.contains(scv.getNo())){
                scv.setHasConfirm("dup");
            }
        }

        return voList;
    }

    public int deleteContract(ServiceContractVo vo) {
        log.warn("해지 신청");
        String checkD = serviceContractMapper.checkD(vo);
        if(checkD != null){
            String errMsg = "이미 신청한 내용";
            log.info(vo.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }

        log.warn("중복 없음");
        int result = serviceContractMapper.deleteContract(vo);
        if(result != 1){
            String errMsg = "부가서비스 해지 신청 실패";
            log.info(vo.toString());
            log.error(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return result;
    }

    public ServiceContractVo selectCount(String no) {
        return serviceContractMapper.selectCount(no);
    }
}
