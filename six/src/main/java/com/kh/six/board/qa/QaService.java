package com.kh.six.board.qa;

import com.kh.six.board.notice.NoticeMapper;
import com.kh.six.board.notice.NoticeVo;
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
public class QaService {
    private final QaMapper qaMapper;

    @Transactional
    public int insert(QaVo vo) {
        checkValid(vo);
        return qaMapper.insert(vo);
    }

    private void checkValid(QaVo vo) {
        //title , content
        //issue ? exception
    }

    public List<QaVo> selectList(PageVo pvo, String no) {
        return qaMapper.selectList(pvo,no);
    }

    @Transactional
    public QaVo selectOne(String no) {
        return qaMapper.selectOne(no);
    }


    public int selectCount() {
        return qaMapper.selectCount();
    }

    public List<QaVo> search(String type, String keyword){
        return qaMapper.search(type, keyword);
    }

    public List<QaVo> selectListAdmin(PageVo pvo) {return qaMapper.selectListAdmin(pvo);}
}
