package com.kh.six.plan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("plan")
public class PlanViewController {
    @GetMapping("list/{pno}")
    public String planList(){
        return "plan/planList";
    }
    @GetMapping("admin/insert")
    public String planInsert(){
        return "plan/planInsert";
    }
    @GetMapping("admin/update")
    public String planUpdate(){
        return "plan/planUpdate";
    }
    @GetMapping("detail/{no}")
    public String planDetail(){
        return "plan/planDetail";
    }
    @GetMapping("mypage/editList")
    public String planEdit(){
        return "plan/userPlanEdit";
    }



}
