package com.kh.six.admin.memberconfirm;

import lombok.Data;

@Data
public class MemberConfirmVo {
    private String no;
    private String memberNo;
    private String name;
    private String confirmStatus;
    private String confirmAt;
    private String service;
    private String fixedNo;
    private String createdAt;

    private String phone;
    private String plan;


}
