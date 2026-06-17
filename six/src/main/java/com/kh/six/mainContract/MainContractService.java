package com.kh.six.mainContract;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainContractService {
    private final MainContractMapper mainContractMapper;

    @Transactional
    public int insert(MainContractVo vo) {
        return mainContractMapper.insert(vo);
    }

    public List<MainContractVo> selectList() {
        return mainContractMapper.selectList();
    }

    @Transactional
    public int delete(String no) {
        return mainContractMapper.delete(no);
    }

    @Transactional
    public int update(MainContractVo vo) {
        return mainContractMapper.update(vo);
    }

    public MainContractVo selecOne(String no) {
        return mainContractMapper.selectOne(no);
    }
}
