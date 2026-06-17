package com.kh.six.addService;

import lombok.Data;

@Data
public class AddServiceVo {
    private String no;
    private String service;
    private String content;
    private String price;
    private String sellYn;
    private String createdAt;
    private String category;
}
