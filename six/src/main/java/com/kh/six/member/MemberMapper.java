package com.kh.six.member;

import com.kh.six.planConfirm.FixedInfoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Insert("""
            INSERT INTO MEMBER
            (
            ID
            ,PW
            ,NAME
            ,PHONE
            ,ADDRESS
            ,ADDRESS2
            ,EMAIL
            ,RESIDENT
            )
            VALUES
            (
            #{id}
            ,#{pw}
            ,#{name}
            ,#{phone}
            ,#{address}
            ,#{address2}
            ,#{email}
            ,#{resident}
            )
            """)
    int join(MemberVo vo);

    @Select("""
        SELECT
            NO
            ,ID
            ,PW
            ,NAME
            ,PHONE
            ,ADDRESS
            ,ADDRESS2
            ,EMAIL
            ,RESIDENT
            ,CHANGE_NAME
            ,ORIGIN_NAME
            ,CREATED_AT
            ,UPDATED_AT
        FROM MEMBER
        WHERE ID = #{id}
        AND QUIT_YN = 'N'
    """)
    MemberVo selectById(String id);

    @Select("""
            SELECT *
                FROM (
                    SELECT
                        F.NO                    AS no,
                        F.MEMBER_NO             AS memberNo,
                        F.PHONE                 AS phone,
                        F.CREATED_AT            AS createdAt,
                        F.DEL_AT                AS delAt,
                        F.MAIN_CONTRACT_END_DATE AS maincontractEndDate,
                        F.ACCOUNT_NO            AS accountNo,
                        F.MAIN_CONTRACT_NO      AS mainContractNo,
                        F.PLAN_NO               AS planNo,
                        F.ACTIVE_YN             AS activeYn,
                        P.NAME                  AS planName,
                        PAY.NAME                AS paymentName,
                        PAY.NO                  AS paymentNo,
                        A.ACCOUNT_NUMBER        AS accountNumber,
                        M.TERM                  AS term
                    FROM FIXED_INFO F
                    JOIN PLAN P ON F.PLAN_NO = P.NO
                    JOIN ACCOUNT A ON F.ACCOUNT_NO = A.NO
                    JOIN PAYMENT PAY ON A.PAYMENT_NO = PAY.NO
                    JOIN MAIN_CONTRACT M ON F.MAIN_CONTRACT_NO = M.NO
                    WHERE F.MEMBER_NO = #{memberNo}
                      AND F.ACTIVE_YN = 'Y'
                    ORDER BY F.CREATED_AT DESC
                )
                WHERE ROWNUM = 1
        """)
    FixedInfoVo selecFixedInfo(String no);

    @Select("""
        SELECT COUNT(*)
        FROM MEMBER
        WHERE RESIDENT = #{resident}
    """)
    int checkResidentDuplicate(String resident);

    @Select("""
            SELECT *
            FROM (
                SELECT 
                    USED_DATA AS planData
                    , USED_VOICE AS planVoice
                    , USED_SMS AS planSms
                FROM PLAN_CONTRACT
                WHERE FIXED_NO = #{no}
                    AND USED_MONTH = TO_CHAR(SYSDATE, 'YYYYMM')
                ORDER BY NO DESC
            )
            WHERE ROWNUM = 1
            """)
    FixedInfoVo selectHistory(FixedInfoVo fixedInfoVo);
}
