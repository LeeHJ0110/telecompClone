package com.kh.six.admin.membermanage;

import com.kh.six.planConfirm.FixedInfoVo;
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
public class MemberManageService {

    private final MemberManageMapper memberManageMapper;

    public List<MemberManageVo> selectList(PageVo pvo) {
        return memberManageMapper.selectList(pvo);
    }

    @Transactional
    public MemberManageVo selectOne(String no, String phone) {
        MemberManageVo vo = memberManageMapper.selectOne(no, phone);

        if (vo != null) {
            vo.setAddServiceCount(memberManageMapper.addServiceCount(no));
            vo.setQaCount(memberManageMapper.qaCount(no));
            vo.setCounselCount(memberManageMapper.counselCount(no));
        }

        return vo;
    }

    public MemberManageVo selectOneByNo(String no) {
        log.info("### service selectOneByNo start, no = {}", no);
        log.info("########## NEW-CODE-12345 ##########");

        MemberManageVo vo = memberManageMapper.selectOneByNo(no);

        log.info("### service selectOneByNo result = {}", vo);
        return vo;
    }

    @Transactional
    public FixedInfoVo selectFixedInfoOne(String no, String phone) {
        return memberManageMapper.selectFixedInfoOne(no, phone);
    }

    @Transactional
    public int updateByNo(MemberManageVo vo) {
        log.info("service vo = {}", vo);
        return memberManageMapper.updateByNo(vo);
    }

    @Transactional
    public int deleteByNo(MemberManageVo vo) {
        return memberManageMapper.deleteByNo(vo);
    }

    public int selectCount() {
        return memberManageMapper.selectCount();
    }

    public List<MemberManageVo> search(String type, String keyword) {
        return memberManageMapper.search(type, keyword);
    }

    public String addServiceCount(String no) {
        return memberManageMapper.addServiceCount(no);
    }

    public String qaCount(String no) {
        return memberManageMapper.qaCount(no);
    }

    public String counselCount(String no) {
        return memberManageMapper.counselCount(no);
    }

    public List<FixedInfoVo> selectFixedInfoList(String no) {
        return memberManageMapper.selectFixedInfoList(no);
    }
}