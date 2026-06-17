package com.kh.six.bill;

import lombok.Data;

@Data
public class BillVo {
    private String no;
    private String fixedNo;
    private String planContractNo;
    private String billMonth;
    private String billTotal;
    private String etcBil;
    private String payYn;

    private String memberNo;
    private String paymentName;
    private String accountNumber;
    private String phone;
}
