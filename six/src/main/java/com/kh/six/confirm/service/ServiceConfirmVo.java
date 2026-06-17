package com.kh.six.confirm.service;

import lombok.Data;

@Data
public class ServiceConfirmVo {
    private String no;
    private String employeeNo;
    private String serviceNo;
    private String serviceContractNo;
    private String fixedNo;
    private String confirmAt;
    private String confirmStatus;

    private String employeeName;
    private String memberNo;
    private String phone;
    private String serviceName;
    private String selectTable;
}
