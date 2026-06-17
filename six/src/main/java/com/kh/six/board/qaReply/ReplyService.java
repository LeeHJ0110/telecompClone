package com.kh.six.board.qaReply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyMapper replyMapper;

    @Transactional
    public int insert(ReplyVo vo) {
        return replyMapper.insert(vo);
    }

    public List<ReplyVo> selectList(String boardNo) {
        return replyMapper.selectList(boardNo);
    }

}
