package com.kh.six.admin.store;

import com.kh.six.admin.employeemanage.EmployeeManageMapper;
import com.kh.six.employee.EmployeeVo;
import com.kh.six.util.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final StoreMapper storeMapper;

    public int selectCount() {
        return storeMapper.selectCount();
    }

    public List<StoreVo> selectList(PageVo pvo) {
        return storeMapper.selectList(pvo);
    }

    public StoreVo selectOne(String no) {
        return storeMapper.selectOne(no);
    }

    public int insert(StoreVo vo) {
        return storeMapper.insert(vo);
    }

    public int update(StoreVo vo) {
        return storeMapper.update(vo);
    }

    public int deleteByNo(StoreVo vo) {
        return storeMapper.deleteByNo(vo);
    }

    public List<StoreVo> search(String type, String keyword) {
        return storeMapper.search(type, keyword);
    }

    public List<EmployeeVo> selectEmployeeList() {
        return storeMapper.selectEmployeeList();
    }
}