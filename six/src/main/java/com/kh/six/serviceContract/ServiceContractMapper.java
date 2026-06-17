package com.kh.six.serviceContract;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServiceContractMapper {
    @Select("""
        <script>
        SELECT
            SC.NO
            ,PHONE
            ,SERVICE
            ,SC.DEL_DATE
        FROM SERVICE_CONTRACT SC
        JOIN FIXED_INFO F ON F.NO = SC.FIXED_NO
        JOIN ADD_SERVICE AD ON AD.NO = SC.SERVICE_NO
        WHERE USED_MONTH = #{usedMonth}
        AND F.MEMBER_NO = #{memberNo}
        AND F.ACTIVE_YN = 'Y'
        ORDER BY
        <choose>
            <when test="sort == 'phone'">
                PHONE
            </when>
            <when test="sort == 'name'">
                SERVICE
            </when>
            <otherwise>
                SC.NO
            </otherwise>
        </choose>
        </script>
    """)
    List<ServiceContractVo> selectList(ServiceContractVo vo);

    @Insert("""
        INSERT INTO SERVICE_DELETE_CONFIRM(
            SERVICE_CONTRACT_NO
            ,FIXED_NO
        )VALUES(
            #{no},
            #{fixedNo}
        )
    """)
    int deleteContract(ServiceContractVo vo);

    @Select("""
        SELECT
            COUNT(CASE WHEN DISCOUNT_RATE = 0 THEN 1 END) AS SERVICE_COUNT,
            COUNT(CASE WHEN DISCOUNT_RATE = 100 THEN 1 END) AS FREE_SERVICE_COUNT
        FROM SERVICE_CONTRACT
        WHERE FIXED_NO = #{fixedNo}
        AND USED_MONTH = TO_CHAR(SYSDATE, 'YYYYMM')
    """)
    ServiceContractVo selectCount(String no);

    @Select("""
        SELECT NO
        FROM SERVICE_DELETE_CONFIRM
        WHERE SERVICE_CONTRACT_NO = #{no}
        AND FIXED_NO = #{fixedNo}
        AND CONFIRM_AT IS NULL
    """)
    String checkD(ServiceContractVo vo);

    @Select("""
        SELECT SC.SERVICE_CONTRACT_NO
        FROM SERVICE_DELETE_CONFIRM SC
        JOIN FIXED_INFO F ON F.NO = SC.FIXED_NO
        WHERE MEMBER_NO = #{memberNo}
        AND CONFIRM_AT IS NULL
    """)
    List<String> checkList(ServiceContractVo vo);

}
