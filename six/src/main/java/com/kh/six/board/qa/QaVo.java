package com.kh.six.board.qa;

import lombok.Data;

@Data
public class QaVo {
    private String no;
    private String title;
    private String content;
    private String category;
    private String writerNo;
    private String writerName;
    private String createdAt;
    private String updatedAt;
    private String delYn;
    private int replyCount;
}
