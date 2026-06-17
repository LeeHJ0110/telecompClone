package com.kh.six.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageViewController {

    @GetMapping("mypage")
    public String mypagePrivate(){
        return "/mypage/mypagePrivate";
    }

    @GetMapping("/mypage/myedit")
    public String myedit() {
        return "/mypage/myEdit";
    }

    @GetMapping("/mypage/mypagePlan")
    public String mypagePlan(){
        return "/mypage/mypagePlan";
    }
}
