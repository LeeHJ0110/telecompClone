package com.kh.six.addService;

import com.kh.six.util.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddServiceService {
    private final AddServiceMapper addServiceMapper;

    public int selectCount(String category) {
        return addServiceMapper.selectCount(category);
    }

    public List<AddServiceVo> selectList(PageVo pvo, String category) {
            return addServiceMapper.selectList(pvo,category);
    }

    @Transactional
    public int delete(String no) {
        return addServiceMapper.delete(no);
    }

    @Transactional
    public int insert(AddServiceVo vo) {
        return addServiceMapper.insert(vo);
    }

    @Transactional
    public int update(AddServiceVo vo) {
        return addServiceMapper.update(vo);
    }

    public AddServiceVo selectOne(String no) {
        return addServiceMapper.selectOne(no);
    }
}
