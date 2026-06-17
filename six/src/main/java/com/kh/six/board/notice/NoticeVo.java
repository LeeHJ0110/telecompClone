package com.kh.six.board.notice;

import lombok.Data;

@Data
public class NoticeVo {
    private String no;
    private String title;
    private String content;
    private String writerNo;
    private String hit;
    private String createdAt;
    private String updatedAt;
    private String delYn;

}