package com.kh.six.confirm.service;

import lombok.Data;

@Data
public class ServiceConfirmJoinVo {

    private String memberId;
    private String memberName;
    private String phone;
    private String resident;

    private String service;
    private String price;

    private String accountNumber;
    private String paymentName;

    private String serviceNo;
    private String fixedNo;
    private String hasConfirm;
}
