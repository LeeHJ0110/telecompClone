package com.kh.six.admin.counsel;

import com.kh.six.admin.employeemanage.EmployeeManageVo;
import com.kh.six.admin.membermanage.MemberManageMapper;
import com.kh.six.admin.membermanage.MemberManageVo;
import com.kh.six.board.qa.QaVo;
import com.kh.six.member.MemberVo;
import com.kh.six.util.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CounselService {

    private final CounselMapper counselMapper;

    public int insert(CounselVo vo) {
        return counselMapper.insert(vo);
    }

    public int selectCount() {
        return counselMapper.selectCount();
    }

    public List<CounselVo> selectList(PageVo pvo) {
        return counselMapper.selectList(pvo);
    }

    public MemberVo checkMember(String name, String phone) {
        return counselMapper.checkMember(name, phone);
    }

    public MemberVo checkMember2(String name, String phone) {
        return counselMapper.checkMember2(name, phone);
    }

    public List<CounselVo> search(String keyword) {return counselMapper.search(keyword);
    }
}
