package com.kh.six.board.notice;

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
public class NoticeService{

    private final NoticeMapper noticeMapper;

    @Transactional
    public int insert(NoticeVo vo) {
        checkValid(vo);
        return noticeMapper.insert(vo);
    }

    private void checkValid(NoticeVo vo) {
        //title , content
        //issue ? exception
    }

    public List<NoticeVo> selectList(PageVo pvo) {
        return noticeMapper.selectList(pvo);
    }

    @Transactional
    public NoticeVo selectOne(String no) {
        return noticeMapper.selectOne(no);
    }
    @Transactional
    public NoticeVo selectPrev(String no){
        return noticeMapper.selectPrev(no);
    }
    @Transactional
    public NoticeVo selectNext(String no){
        return noticeMapper.selectNext(no);
    }

    @Transactional
    public int updateByNo(NoticeVo vo) {
        System.out.println("service vo = " + vo);
        return noticeMapper.updateByNo(vo);
    }


    @Transactional
    public int deleteByNo(NoticeVo vo) {
        return noticeMapper.deleteByNo(vo);
    }

    public int selectCount() {
        return noticeMapper.selectCount();
    }

    public List<NoticeVo> search(String type, String keyword){
        return noticeMapper.search(type, keyword);
    }
}//class