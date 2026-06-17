package com.kh.six.mypage;

import com.kh.six.member.MemberVo;
import com.kh.six.planConfirm.FixedInfoVo;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MypageMapper {

    @Select("""
                SELECT 
                    NAME
                    ,ID
                    ,EMAIL
                    ,ADDRESS
                    ,PHONE
                    .RESIDENT
                FROM MEMBER
                WHERE DEL_YN='N'
            """)
    void output(MemberVo vo, HttpSession session);


    @Update("""
                UPDATE MEMBER
                    SET
                        EMAIL=#{vo.email}
                        ,ADDRESS=#{vo.address}
                        ,ADDRESS2=#{vo.address2}
                        ,PHONE=#{vo.phone}
                        ,UPDATED_AT = SYSDATE
                    WHERE NO = #{vo.no}
            """)
    int edit(MemberVo vo, MemberVo loginMemberVo);

    @Update("""
                UPDATE MEMBER
                    SET
                        QUIT_YN = 'Y' ,
                        ID = ID || '_DELETE'
                        ,UPDATED_AT = SYSDATE
                    WHERE NO = #{no}
            """)
    int quit(MemberVo vo);

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
    FixedInfoVo getMyPlanInfo(@Param("memberNo") String memberNo);

    @Select("""
                SELECT PHONE
                FROM FIXED_INFO
                WHERE MEMBER_NO = #{memberNo}
                AND ACTIVE_YN = 'Y'
            """)
    List<String> getMyPhoneNo(@Param("memberNo") String memberNo);


    @Select("""
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
                WHERE F.PHONE = #{phone} AND F.MEMBER_NO = #{memberNo}
                  AND F.ACTIVE_YN = 'Y'
            """)
    FixedInfoVo getPlanDetailByPhone(@Param("phone") String phone, @Param("memberNo") String memberNo);



    @Select("""
                SELECT NO AS PLAN_NO
                FROM PLAN
                WHERE NAME = #{planName}
            """)
    FixedInfoVo send(String planName);
}
