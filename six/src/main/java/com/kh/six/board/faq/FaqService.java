package com.kh.six.board.faq;

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
public class FaqService {
    private final FaqMapper faqMapper;

    @Transactional
    public int insert(FaqVo vo) {
        checkValid(vo);
        return faqMapper.insert(vo);
    }

    private void checkValid(FaqVo vo) {
        //title , content
        //issue ? exception
    }

    public List<FaqVo> selectList(PageVo pvo, String category) {
        return faqMapper.selectList(pvo , category);
    }


    @Transactional
    public int updateByNo(FaqVo vo) {
        System.out.println("service vo = " + vo);
        return faqMapper.updateByNo(vo);
    }


    @Transactional
    public int deleteByNo(FaqVo vo) {
        return faqMapper.deleteByNo(vo);
    }

    public int selectCount(String category) {
        return faqMapper.selectCount(category);
    }

    public FaqVo selectOne(String no){
        return faqMapper.selectOne(no);
    }
}
