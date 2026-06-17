package com.kh.six.admin.store;

import lombok.Data;

@Data
public class StoreVo {
    private String no;
    private String managerNo;
    private String name;
    private String managerName;
    private String phone;
    private String address;
    private String createdAt;
    private String updatedAt;
    private String quitYn;
}