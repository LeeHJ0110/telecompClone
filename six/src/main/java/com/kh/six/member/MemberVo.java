package com.kh.six.member;

import lombok.Data;

@Data
public class MemberVo {
    private String no;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String address;
    private String address2;
    private String email;
    private String resident;
    private String changeName;
    private String originName;
    private String createdAt;
    private String updatedAt;
    private String quitYn;
}
