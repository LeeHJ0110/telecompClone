package com.kh.six.confirm.plan;

import com.kh.six.confirm.service.ConfirmPageVo;
import com.kh.six.planConfirm.FixedInfoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ConfirmResultMapper {
    @Select("""
            <script>
            SELECT COUNT(SC.NO)
            FROM
            <choose>
                <when test="selectTable == 'delete'">
                    PLAN_DELETE_CONFIRM SC
                </when>
                <when test="selectTable == 'update'">
                    PLAN_UPDATE_CONFIRM SC
                </when>
                <otherwise>
                    PLAN_INSERT_CONFIRM SC
                </otherwise>
            </choose>
            JOIN FIXED_INFO F ON (SC.FIXED_NO = F.NO)
            WHERE F.MEMBER_NO = #{memberNo}
            </script>
            """)
    int selectCount(ConfirmPageVo pvo);

    @Select("""
            <script>
            SELECT
                    SC.NO               AS no,
                    SC.CONFIRM_AT       AS confirmAt,
                    SC.CONFIRM_STATUS   AS confirmStatus,
                    E.NAME              AS employeeName,
                    F.PHONE             AS phone,
                    F.MEMBER_NO         AS memberNo,
                    <choose>
                        <when test="selectTable == 'update'">
                            SC.NEW_PLAN_NO AS planNo,
                            P.NAME         AS planName
                        </when>
                        <otherwise>
                            F.PLAN_NO      AS planNo,
                            P.NAME         AS planName
                        </otherwise>
                    </choose>
            FROM
            <choose>
                <when test="selectTable == 'delete'"> PLAN_DELETE_CONFIRM SC </when>
                <when test="selectTable == 'update'"> PLAN_UPDATE_CONFIRM SC </when>
                <otherwise> PLAN_INSERT_CONFIRM SC </otherwise>
            </choose>
            /* 2. 가입정보(FIXED_INFO)와 연결 */
            JOIN FIXED_INFO F ON (SC.FIXED_NO = F.NO)
        
            /* 3. 요금제 마스터(PLAN)와 연결 (selectTable 값에 따라 조인 대상 변경) */
            LEFT JOIN PLAN P ON (
                <choose>
                    <when test="selectTable == 'update'"> 
                        SC.NEW_PLAN_NO = P.NO 
                    </when>
                    <otherwise> 
                        F.PLAN_NO = P.NO 
                    </otherwise>
                </choose>
            )
            LEFT JOIN EMPLOYEE E ON (SC.EMPLOYEE_NO = E.NO)
            WHERE F.MEMBER_NO = #{memberNo}
            ORDER BY
            <choose>
                <when test="sort == 'oldest'"> SC.NO </when>
                <when test="sort == 'confirm'"> CONFIRM_STATUS, SC.NO DESC </when>
                <otherwise> SC.NO DESC </otherwise>
            </choose>
            OFFSET #{offset} ROWS
            FETCH NEXT #{boardLimit} ROWS ONLY
            </script>
            """)
    List<ConfirmResultVo> selectList(ConfirmPageVo pvo);

    @Update("""
            <script>
            UPDATE
            <choose>
                <when test="selectTable == 'delete'">
                    PLAN_DELETE_CONFIRM
                </when>
                <when test="selectTable == 'update'">
                    PLAN_UPDATE_CONFIRM
                </when>
                <otherwise>
                    PLAN_INSERT_CONFIRM
                </otherwise>
            </choose>
            SET
                EMPLOYEE_NO = #{employeeNo}
                ,CONFIRM_STATUS = #{confirmStatus}
                ,CONFIRM_AT = SYSDATE
            WHERE NO = #{no}
            </script>
            """)
    int confirmPlan(ConfirmResultVo crv);

    @Update("""
            UPDATE FIXED_INFO
            SET
                DEL_AT = SYSDATE
                , ACTIVE_YN = 'N'
            WHERE PHONE = #{phone}
                AND MEMBER_NO = #{memberNo}
            """)
    int deleteFixed(FixedInfoVo fixedInfoVo);

    @Update("""
            UPDATE FIXED_INFO F
                SET 
                    PLAN_NO = (
                    SELECT NEW_PLAN_NO
                    FROM (
                        SELECT PU.NEW_PLAN_NO
                        FROM PLAN_UPDATE_CONFIRM PU
                        WHERE FIXED_NO = #{no}
                        ORDER BY CONFIRM_AT DESC
                        )
                    WHERE ROWNUM = 1
                ) 
            WHERE F.PHONE = #{phone}
                AND F.MEMBER_NO = #{memberNo}
            """)
    int updateFixed(FixedInfoVo fixedInfoVo);

    @Update("""
            UPDATE FIXED_INFO
                SET
                    ACTIVE_YN = 'Y'
            WHERE PHONE = #{phone}
                AND MEMBER_NO = #{memberNo}
            """)
    int insertFixed(FixedInfoVo fixedInfoVo);

    @Insert("""
            INSERT INTO PLAN_CONTRACT
            (
            FIXED_NO
            , USED_MONTH
            , PERSONAL_PLAN_NO
            )
            SELECT
                NO
                , TO_CHAR(SYSDATE, 'YYYYMM')
                , PLAN_NO
            FROM FIXED_INFO
            WHERE PHONE = #{phone}
                AND MEMBER_NO = #{memberNo}
            """)
    int createPlanContract(FixedInfoVo fixedInfoVo);

    @Update("""
            UPDATE PLAN_CONTRACT
                SET
                    PERSONAL_PLAN_NO = (
                        SELECT NEW_PLAN_NO
                        FROM PLAN_UPDATE_CONFIRM U
                        LEFT JOIN FIXED_INFO F ON F.NO = U.FIXED_NO
                        WHERE F.PHONE = #{phone}
                            AND F.MEMBER_NO = #{memberNo}
                            AND U.CONFIRM_STATUS = 'Y'
                    )
            WHERE FIXED_NO = (
                SELECT NO
                FROM FIXED_INFO
                WHERE PHONE = #{phone}
                    AND MEMBER_NO = #{memberNo}
            ) 
            """)
    int updatePlanContract(FixedInfoVo fixedInfoVo);

    @Update("""
            UPDATE MEMBER
                SET
                    PHONE = #{phone}
            WHERE NO = #{memberNo}
            """)
    int updateMemberPhone(FixedInfoVo fixedInfoVo);

    @Select("""
            SELECT NAME
            FROM MEMBER
            WHERE NO = #{memberNo}
            """)
    String selectMemberName(ConfirmPageVo pvo);

    @Insert("""
            INSERT INTO BILLING
            (
            FIXED_NO
            , PLAN_CONTRACT_NO
            , BILL_MONTH
            )
            SELECT
                P.FIXED_NO
                , P.NO
                , P.USED_MONTH
            FROM PLAN_CONTRACT P
            LEFT JOIN FIXED_INFO F ON P.FIXED_NO = F.NO
            WHERE F.MEMBER_NO = #{memberNo}
                AND F.PHONE = #{phone}
            """)
    int createBill(FixedInfoVo fixedInfoVo);
}