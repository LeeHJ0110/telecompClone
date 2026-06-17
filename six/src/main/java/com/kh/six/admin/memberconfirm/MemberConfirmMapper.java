package com.kh.six.admin.memberconfirm;

import com.kh.six.admin.memberconfirm.MemberConfirmVo;
import com.kh.six.planConfirm.FixedInfoVo;
import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberConfirmMapper {

    @Select("""
        SELECT
            (SELECT COUNT(*) FROM PLAN_UPDATE_CONFIRM WHERE CONFIRM_STATUS = 'A')
          + (SELECT COUNT(*) FROM PLAN_DELETE_CONFIRM WHERE CONFIRM_STATUS = 'A')
          + (SELECT COUNT(*) FROM PLAN_INSERT_CONFIRM WHERE CONFIRM_STATUS = 'A')
          + (SELECT COUNT(*) FROM SERVICE_INSERT_CONFIRM WHERE CONFIRM_STATUS = 'A')
          + (SELECT COUNT(*) FROM SERVICE_DELETE_CONFIRM WHERE CONFIRM_STATUS = 'A')
        FROM DUAL
    """)
    int selectCount();

    @Select("""
        SELECT
            PU.NO
            ,M.NO AS MEMBER_NO
            ,M.NAME
            ,PU.FIXED_NO
            ,PU.CONFIRM_STATUS
            ,F.CREATED_AT
            , F.PHONE
            , P.NAME AS PLAN
        FROM PLAN_UPDATE_CONFIRM PU
        JOIN FIXED_INFO F ON (PU.FIXED_NO = F.NO)
        JOIN MEMBER M ON (F.MEMBER_NO = M.NO)
        LEFT JOIN PLAN P ON P.NO = F.PLAN_NO
        WHERE PU.CONFIRM_STATUS = 'A'
        ORDER BY PU.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<MemberConfirmVo> selectPlanUpdateList(PageVo pvo);

    @Select("""
        SELECT
            PD.NO
            ,M.NO AS MEMBER_NO
            ,M.NAME
            ,PD.FIXED_NO
            ,PD.CONFIRM_STATUS
            ,F.CREATED_AT
            , F.PHONE
            , P.NAME AS PLAN
        FROM PLAN_DELETE_CONFIRM PD
        JOIN FIXED_INFO F ON (PD.FIXED_NO = F.NO)
        JOIN MEMBER M ON (F.MEMBER_NO = M.NO)
        LEFT JOIN PLAN P ON P.NO = F.PLAN_NO
        WHERE PD.CONFIRM_STATUS = 'A'
        ORDER BY PD.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<MemberConfirmVo> selectPlanDeleteList(PageVo pvo);

    @Select("""
        SELECT
            PI.NO
            ,M.NO AS MEMBER_NO
            ,M.NAME
            ,PI.FIXED_NO
            ,PI.CONFIRM_STATUS
            ,F.CREATED_AT
            , F.PHONE
            , P.NAME AS PLAN
        FROM PLAN_INSERT_CONFIRM PI
        JOIN FIXED_INFO F ON (PI.FIXED_NO = F.NO)
        JOIN MEMBER M ON (F.MEMBER_NO = M.NO)
        LEFT JOIN PLAN P ON P.NO = F.PLAN_NO
        WHERE PI.CONFIRM_STATUS = 'A'
        ORDER BY PI.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<MemberConfirmVo> selectPlanInsertList(PageVo pvo);

    @Select("""
        SELECT
            SI.NO
            ,M.NO AS MEMBER_NO
            ,M.NAME
            ,SI.FIXED_NO
            ,SI.CONFIRM_STATUS
            ,F.CREATED_AT
            ,A.SERVICE
            , F.PHONE
        FROM SERVICE_INSERT_CONFIRM SI
        JOIN FIXED_INFO F ON (SI.FIXED_NO = F.NO)
        JOIN MEMBER M ON (F.MEMBER_NO = M.NO)
        JOIN ADD_SERVICE A ON (SI.SERVICE_NO=A.NO)
        WHERE SI.CONFIRM_STATUS = 'A'
        ORDER BY SI.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<MemberConfirmVo> selectServiceInsertList(PageVo pvo);

    @Select("""
        SELECT
            SD.NO
            ,M.NO AS MEMBER_NO
            ,M.NAME
            ,SD.FIXED_NO
            ,SD.CONFIRM_STATUS
            ,F.CREATED_AT
            ,A.SERVICE
            , F.PHONE
        FROM SERVICE_DELETE_CONFIRM SD
        JOIN FIXED_INFO F ON (SD.FIXED_NO = F.NO)
        JOIN MEMBER M ON (F.MEMBER_NO = M.NO)
        JOIN SERVICE_CONTRACT SC ON (SD.FIXED_NO =SC.NO)
        JOIN ADD_SERVICE A ON (SC.SERVICE_NO=A.NO)
        WHERE SD.CONFIRM_STATUS = 'A'
        ORDER BY SD.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<MemberConfirmVo> selectServiceDeleteList(PageVo pvo);

    @Select("""
    SELECT
        F.NO
        ,F.MEMBER_NO
        ,F.PHONE
        ,F.MAIN_CONTRACT_NO
        ,F.ACCOUNT_NO
        ,F.CREATED_AT
    FROM PLAN_INSERT_CONFIRM PI
    JOIN FIXED_INFO F ON (PI.FIXED_NO = F.NO)
    WHERE PI.NO = #{no}
""")
    FixedInfoVo selectPlanInsertOne(String no);

    @Select("""
    SELECT
        F.NO
        ,F.MEMBER_NO
        ,F.PHONE
        ,F.MAIN_CONTRACT_NO
        ,F.ACCOUNT_NO
        ,F.CREATED_AT
    FROM PLAN_UPDATE_CONFIRM PU
    JOIN FIXED_INFO F ON (PU.FIXED_NO = F.NO)
    WHERE PU.NO = #{no}
""")
    FixedInfoVo selectPlanUpdateOne(String no);

    @Select("""
    SELECT
        F.NO
        ,F.MEMBER_NO
        ,F.PHONE
        ,F.MAIN_CONTRACT_NO
        ,F.ACCOUNT_NO
        ,F.CREATED_AT
    FROM PLAN_DELETE_CONFIRM PD
    JOIN FIXED_INFO F ON (PD.FIXED_NO = F.NO)
    WHERE PD.NO = #{no}
""")
    FixedInfoVo selectPlanDeleteOne(String no);

    @Select("""
    SELECT
        F.NO
        ,F.MEMBER_NO
        ,F.PHONE
        ,F.MAIN_CONTRACT_NO
        ,F.ACCOUNT_NO
        ,F.CREATED_AT
    FROM SERVICE_INSERT_CONFIRM SI
    JOIN FIXED_INFO F ON (SI.FIXED_NO = F.NO)
    WHERE SI.NO = #{no}
""")
    FixedInfoVo selectServiceInsertOne(String no);

    @Select("""
    SELECT
        F.NO
        ,F.MEMBER_NO
        ,F.PHONE
        ,F.MAIN_CONTRACT_NO
        ,F.ACCOUNT_NO
        ,F.CREATED_AT
    FROM SERVICE_DELETE_CONFIRM SD
    JOIN FIXED_INFO F ON (SD.FIXED_NO = F.NO)
    WHERE SD.NO = #{no}
""")
    FixedInfoVo selectServiceDeleteOne(String no);
}
