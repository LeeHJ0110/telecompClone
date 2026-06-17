package com.kh.six.planConfirm;

import com.kh.six.confirm.plan.ConfirmResultVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlanConfirmMapper {


    @Select("""
            SELECT COUNT(*)
            FROM FIXED_INFO
            WHERE PHONE = #{phone}
            """)
    int countByPhone(String randomPhone);

    @Insert("""
    INSERT INTO FIXED_INFO (
        NO,
        MEMBER_NO,
        PHONE,
        ACCOUNT_NO,
        MAIN_CONTRACT_NO,
        PLAN_NO,
        MAIN_CONTRACT_END_DATE
    ) VALUES (
        #{no},
        #{memberNo},
        #{phone},
        #{accountNo},
        #{mainContractNo},
        #{planNo},
        (SELECT TO_CHAR(ADD_MONTHS(SYSDATE, TO_NUMBER(TERM)), 'YYYYMMDD')
            FROM MAIN_CONTRACT
            WHERE NO = #{mainContractNo})
    )
""")
    @SelectKey(
            statement = "SELECT SEQ_FIXED_INFO.NEXTVAL FROM DUAL",
            keyProperty = "no",
            before = true,
            resultType = String.class
    )
    int planJoin(FixedInfoVo fixedInfoVo);

    @Insert("""
            INSERT INTO PLAN_INSERT_CONFIRM
            (
                FIXED_NO
            )
            VALUES
            (
                #{fixedNo}
            )
            """)
    int waitPlanJoin(com.kh.six.confirm.plan.ConfirmResultVo confirmResultVo);

    @Select("""
            SELECT *
            FROM FIXED_INFO
            WHERE PHONE = #{phone}
            AND MEMBER_NO = #{no}
            AND ACTIVE_YN = 'Y'
            """)
    FixedInfoVo selectFixed(String phone, String no);

    @Insert("""
            INSERT INTO PLAN_UPDATE_CONFIRM
            (
            FIXED_NO
            , NEW_PLAN_NO
            )
            VALUES
            (
            #{fixedNo}
            , #{newPlanNo}
            )
            """)
    int waitPlanUpdate(ConfirmResultVo confirmResultVo);

    @Select("""
            SELECT 
                NO, 
                NAME
            FROM PAYMENT
            ORDER BY NO DESC
            """)
    List<PaymentVo> getPaymentList();

    @Insert("""
        INSERT INTO ACCOUNT (
            NO,
            ACCOUNT_NUMBER,
            PAYMENT_NO
        ) VALUES (
            #{accountNo},
            #{accountNumber},
            #{paymentNo}
        )
    """)
    @SelectKey(
            statement = "SELECT SEQ_ACCOUNT.NEXTVAL FROM DUAL",
            keyProperty = "accountNo",
            before = true, // 인서트 전에 번호를 미리 딴다!
            resultType = String.class
    )
    int insertAccount(FixedInfoVo fixedInfoVo);


    @Select("""
            SELECT NAME AS planName
                , DATA_TOTAL AS planData
                , VOICE_TOTAL AS planVoice
                , PRICE AS planPrice
            FROM PLAN
            WHERE NO = #{planNo}
            """)
    FixedInfoVo getPlanDetails(FixedInfoVo fixedInfoVo);

    @Select("""
            SELECT TERM
            FROM MAIN_CONTRACT
            WHERE NO = #{mainContractNo}
            """)
    String getTerm(FixedInfoVo fixedInfoVo);

    @Insert("""
            INSERT INTO PLAN_DELETE_CONFIRM
            (
                FIXED_NO
            )
            VALUES
            (
                #{fixedNo}
            )
            """)
    int waitPlanDelete(ConfirmResultVo confirmResultVo);

    @Select("""
            SELECT
                PHONE
                , ACCOUNT_NUMBER
                , NAME AS paymentName
            FROM FIXED_INFO F
            LEFT JOIN ACCOUNT A ON F.ACCOUNT_NO = A.NO
            LEFT JOIN PAYMENT P ON P.NO = A.PAYMENT_NO
            WHERE F.MEMBER_NO = #{memberNo}
                AND F.PHONE = #{phone}
            """)
    FixedInfoVo showAccount(FixedInfoVo fixedInfoVo);

    @Update("""
            UPDATE ACCOUNT A
                SET 
                    A.ACCOUNT_NUMBER = #{accountNumber}
                    , A.PAYMENT_NO = (
                        SELECT P.NO
                        FROM PAYMENT P
                        WHERE P.NAME = #{paymentName}
                    )
            WHERE A.NO = (
                SELECT F.ACCOUNT_NO
                FROM FIXED_INFO F
                WHERE F.PHONE = #{phone}
                    AND F.MEMBER_NO = #{memberNo}
            )
            """)
    int updateAccount(FixedInfoVo fixedInfoVo);

    @Select("""
            SELECT PHONE
            FROM FIXED_INFO
            WHERE MEMBER_NO = #{no}
            AND ACTIVE_YN = 'Y'
            """)
    List<String> getPhoneList(String no);

    @Select("""
            SELECT 
                (SELECT COUNT(*) FROM PLAN_UPDATE_CONFIRM WHERE FIXED_NO = #{fixedNo} AND CONFIRM_STATUS = 'A') +
                (SELECT COUNT(*) FROM PLAN_DELETE_CONFIRM WHERE FIXED_NO = #{fixedNo} AND CONFIRM_STATUS = 'A')
            FROM DUAL
    """)
    int countPendingRequest(String fixedNo);
}



