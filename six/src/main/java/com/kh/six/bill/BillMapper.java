package com.kh.six.bill;

import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BillMapper {

    @Select("""
        SELECT
            B.BILL_MONTH
            , B.BILL_TOTAL
            , B.ETC_BIL
            , B.PAY_YN
            , P.NAME            AS PAYMENT_NAME
            , A.ACCOUNT_NUMBER  AS ACCOUNT_NUMBER
            , F.PHONE           AS PHONE
            , F.NO              AS FIXED_NO
        FROM BILLING B
        JOIN FIXED_INFO F ON (B.FIXED_NO = F.NO)
        JOIN ACCOUNT A ON (F.ACCOUNT_NO = A.NO)
        JOIN PAYMENT P ON (A.PAYMENT_NO = P.NO)
        WHERE B.BILL_MONTH = #{billMonth}
        AND B.FIXED_NO = #{fixedNo}
    """)
    BillVo selectOne(BillVo vo);

    @Update("""
        UPDATE BILLING B
            SET BILL_TOTAL = (
                SELECT
                    P.PRICE + NVL(SUM(
                        CASE
                            WHEN SC.DISCOUNT_RATE = 100 THEN 0
                            ELSE A.PRICE
                        END
                    ), 0)
                FROM PLAN_CONTRACT PC
                JOIN PLAN P
                    ON PC.PERSONAL_PLAN_NO = P.NO
                LEFT JOIN SERVICE_CONTRACT SC
                    ON SC.BILL_NO = B.NO
                LEFT JOIN ADD_SERVICE A
                    ON SC.SERVICE_NO = A.NO
                WHERE PC.NO = B.PLAN_CONTRACT_NO
                GROUP BY P.PRICE
            )
        WHERE B.BILL_MONTH = #{billMonth}
        AND FIXED_NO = #{fixedNo}
    """)
    int insertTotal(BillVo vo);

    @Select("""
        SELECT BILL_MONTH
        FROM BILLING
        WHERE FIXED_NO = #{fixedNo}
        ORDER BY BILL_MONTH DESC
    """)
    List<String> selectBillMonths(String fixedNo);

    @Select("""
        SELECT
            PHONE
            ,NO
        FROM FIXED_INFO
        WHERE MEMBER_NO = #{no}
        AND ACTIVE_YN = 'Y'
        ORDER BY NO DESC
    """)
    List<FixedInfoVo> selectPhones(MemberVo loginMemberVo);

    @Select("""
        SELECT
            NO
            ,MEMBER_NO
            ,PHONE
            ,CREATED_AT
            ,DEL_AT
            ,MAIN_CONTRACT_END_DATE
            ,ACCOUNT_NO
            ,MAIN_CONTRACT_NO
            ,PLAN_NO
        FROM FIXED_INFO
        WHERE NO = #{no}
        AND ACTIVE_YN = 'Y'
    """)
    FixedInfoVo selectFixedByNo(String no);
}
