package com.kh.six.board.faq;

import lombok.Data;

@Data
public class FaqVo {
    private String no;
    private String question;
    private String answer;
    private String category;
    private String writerNo;
    private String createdAt;
    private String updatedAt;
    private String delYn;

}
