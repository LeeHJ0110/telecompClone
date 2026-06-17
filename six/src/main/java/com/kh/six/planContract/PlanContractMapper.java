package com.kh.six.planContract;

import com.kh.six.planConfirm.FixedInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlanContractMapper {

    @Select("""
            SELECT
                F.PHONE
                , C.USED_DATA AS planData
                , C.USED_VOICE AS planVoice
                , C.USED_SMS AS planSms
                , P.NAME AS planName
                , C.USED_MONTH AS usedMonth
            FROM FIXED_INFO F
            LEFT JOIN PLAN_CONTRACT C ON F.NO = C.FIXED_NO
            LEFT JOIN PLAN P ON C.PERSONAL_PLAN_NO = P.NO
            WHERE F.MEMBER_NO = #{memberNo}
                AND C.USED_MONTH = #{usedMonth}
                AND F.ACTIVE_YN = 'Y'
            ORDER BY F.PHONE ASC
            """)
    List<FixedInfoVo> selectList(FixedInfoVo fixedInfoVo);
}
