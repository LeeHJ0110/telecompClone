package com.kh.six.planConfirm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("planConfirm")
@RequiredArgsConstructor
public class PlanConfirmViewController {

    private final PlanConfirmService planConfirmService;

    @GetMapping("/mypage/update")
    public String update(){
        return "planConfirm/update";
    }

    @GetMapping("/mypage/join")
    public String join(Model model){
        List<PaymentVo> paymentList = planConfirmService.getPaymentList();
        model.addAttribute("paymentList", paymentList);
        return "planConfirm/join";
    }

    @GetMapping("/mypage/contract")
    public String contract(){
        return "planConfirm/contract";
    }

    @GetMapping("/mypage/changeAccount")
    public String changeAccount(@RequestParam String phone, Model model){
        model.addAttribute("phone", phone);
        List<PaymentVo> paymentList = planConfirmService.getPaymentList();
        model.addAttribute("paymentList", paymentList);
        return "planConfirm/changeAccount";
    }
}
