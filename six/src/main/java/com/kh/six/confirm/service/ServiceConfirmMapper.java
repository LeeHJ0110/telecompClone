package com.kh.six.confirm.service;

import com.kh.six.serviceContract.ServiceContractVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServiceConfirmMapper {

    // 해당 고객이 가진 신청내역중 조회하고싶은 태이블을 골라 조회
    @Select("""
        <script>
        SELECT COUNT(SC.NO)
        FROM
        <choose>
            <when test="selectTable == 'delete'">
                SERVICE_DELETE_CONFIRM SC
            </when>
            <otherwise>
                SERVICE_INSERT_CONFIRM SC
            </otherwise>
        </choose>
        JOIN FIXED_INFO F ON (SC.FIXED_NO = F.NO)
        WHERE F.MEMBER_NO = #{memberNo}
        AND F.ACTIVE_YN = 'Y'
        </script>
    """)
    int selectCount(ConfirmPageVo pvo);

    @Select("""
        <script>
        SELECT
            SC.NO
            ,SC.CONFIRM_AT
            ,SC.CONFIRM_STATUS
            ,E.NAME     AS EMPLOYEE_NAME
            ,F.PHONE    AS PHONE
            ,AD.SERVICE AS SERVICE_NAME
            ,AD.NO AS SERVICE_NO
        FROM
        <choose>
            <when test="selectTable == 'delete'">
                SERVICE_DELETE_CONFIRM SC
                JOIN SERVICE_CONTRACT SCT ON (SC.SERVICE_CONTRACT_NO = SCT.NO)
                JOIN ADD_SERVICE AD ON (SCT.SERVICE_NO = AD.NO)
            </when>
            <otherwise>
                SERVICE_INSERT_CONFIRM SC
                JOIN ADD_SERVICE AD ON (SC.SERVICE_NO = AD.NO)
            </otherwise>
        </choose>
        JOIN FIXED_INFO F ON (SC.FIXED_NO = F.NO)
        LEFT JOIN EMPLOYEE E ON (SC.EMPLOYEE_NO = E.NO)
        WHERE F.MEMBER_NO = #{memberNo}
        AND F.ACTIVE_YN = 'Y'
        ORDER BY
        <choose>
            <when test="sort == 'oldest'">
                SC.NO
            </when>
            <when test="sort == 'confirm'">
                CONFIRM_STATUS, SC.NO DESC
            </when>
            <otherwise>
                SC.NO DESC
            </otherwise>
        </choose>
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
        </script>
    """)
    List<ServiceConfirmVo> selectList(ConfirmPageVo pvo);

    @Update("""
        <script>
        UPDATE
        <choose>
            <when test="selectTable == 'delete'">
                SERVICE_DELETE_CONFIRM
            </when>
            <otherwise>
                SERVICE_INSERT_CONFIRM
            </otherwise>
        </choose>
        SET
            EMPLOYEE_NO = #{employeeNo}
            ,CONFIRM_STATUS = #{confirmStatus}
            ,CONFIRM_AT = SYSDATE
        WHERE NO = #{no}
        </script>
    """)
    int confirmService(ServiceConfirmVo scv);

    @Insert("""
        <script>
        INSERT INTO
        <choose>
            <when test="selectTable == 'delete'">
                SERVICE_DELETE_CONFIRM(
                    SERVICE_CONTRACT_NO
                    ,FIXED_NO
                )VALUES(
                    #{serviceContractNo}
                    ,#{fixedNo}
                )
            </when>
            <otherwise>
                SERVICE_INSERT_CONFIRM(
                    SERVICE_NO
                    ,FIXED_NO
                )VALUES(
                    #{serviceNo}
                    ,#{fixedNo}
                )
            </otherwise>
        </choose>
        </script>
    """)
    int confirmInsert(ServiceConfirmVo scv);

    // join 너무 많음 근데 어떻게 해결하지
    @Select("""
        SELECT
            M.ID        AS MEMBER_ID,
            M.NAME      AS MEMBER_NAME,
            M.PHONE,
            M.RESIDENT,
            F.NO        AS FIXED_NO,
            AD.SERVICE,
            AD.NO       AS SERVICE_NO,
            AD.PRICE,
            AC.ACCOUNT_NUMBER,
            P.NAME AS PAYMENT_NAME
        FROM MEMBER M
        JOIN FIXED_INFO F ON M.NO = F.MEMBER_NO
        JOIN ADD_SERVICE AD ON AD.NO = #{serviceNo}
        LEFT JOIN ACCOUNT AC ON AC.NO = F.ACCOUNT_NO
        LEFT JOIN PAYMENT P ON P.NO = AC.PAYMENT_NO
        WHERE M.NO = #{memberNo}
        AND F.NO = #{fixedNo}
    """)
    ServiceConfirmJoinVo detail(ServiceConfirmVo scv);

    @Insert("""
        INSERT INTO SERVICE_INSERT_CONFIRM(
            SERVICE_NO
            ,FIXED_NO
        )VALUES(
            #{serviceNo}
            ,#{fixedNo}
        )
    """)
    int insertJoinConfirm(ServiceConfirmVo scv);

    @Insert("""
        INSERT INTO SERVICE_CONTRACT(
            FIXED_NO,
            SERVICE_NO,
            BILL_NO,
            USED_MONTH,
            DISCOUNT_RATE
        )
        SELECT
            #{fixedNo},
            SC.SERVICE_NO,
            B.NO,
            TO_CHAR(SYSDATE, 'YYYYMM'),
            100
        FROM SERVICE_INSERT_CONFIRM SC
        JOIN BILLING B
            ON B.FIXED_NO = SC.FIXED_NO
        WHERE SC.NO = #{no}
        AND BILL_MONTH = TO_CHAR(SYSDATE, 'YYYYMM')
    """)
    int insertServiceContract(ServiceConfirmVo scv);

    @Update("""
        UPDATE SERVICE_CONTRACT
            SET DEL_DATE = SYSDATE
        WHERE NO = (
            SELECT SERVICE_CONTRACT_NO
            FROM SERVICE_DELETE_CONFIRM
            WHERE NO = #{no}
        )
    """)
    int deleteServiceContract(ServiceConfirmVo scv);

    @Select("""
        SELECT NO
        FROM SERVICE_INSERT_CONFIRM
        WHERE SERVICE_NO = #{serviceNo}
        AND FIXED_NO = #{fixedNo}
        AND CONFIRM_AT IS NULL
    """)
    String checkD(ServiceConfirmVo scv);

    @Select("""
        SELECT NAME
        FROM MEMBER
        WHERE NO = #{memberNo}
    """)
    String getName(String memberNo);
}
