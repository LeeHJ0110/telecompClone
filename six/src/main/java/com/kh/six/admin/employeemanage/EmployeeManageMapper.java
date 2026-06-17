package com.kh.six.admin.employeemanage;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeManageMapper {

    @Insert("""
        INSERT INTO EMPLOYEE
        (
            AGENCY_NO
            ,ID
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
            #{agencyNo}
            ,#{id}
            ,#{pw}
            ,#{name}
            ,#{phone}
            ,#{address}
            ,#{address2}
            
            ,#{email}
            ,#{resident}
        )
    """)
    int insert(EmployeeManageVo vo);

    @Select("""
        SELECT
            E.NO
            ,E.AGENCY_NO
            ,A.NAME AS AGENCY_NAME
            ,E.ID
            ,E.PW
            ,E.NAME
            ,E.PHONE
            ,E.ADDRESS
            ,E.ADDRESS2
            ,E.JOB_NO
            ,J.JOB_NAME AS JOB_NAME
            ,E.EMAIL
            ,E.RESIDENT
            ,E.CREATED_AT
            ,E.UPDATED_AT
            ,E.QUIT_YN
            ,E.PROFILE_ORIGIN_NAME
        FROM EMPLOYEE E
        JOIN JOB J ON (E.JOB_NO = J.NO)
        JOIN AGENCY A ON (E.AGENCY_NO = A.NO)
        WHERE E.QUIT_YN = 'N'
        ORDER BY E.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<EmployeeManageVo> selectList(PageVo pvo);

    @Select("""
        SELECT
            E.NO
            ,E.AGENCY_NO
            ,A.NAME AS AGENCY_NAME
            ,E.ID
            ,E.PW
            ,E.NAME
            ,E.PHONE
            ,E.ADDRESS
            ,E.ADDRESS2
            ,E.JOB_NO
            ,J.JOB_NAME AS JOB_NAME
            ,E.EMAIL
            ,E.RESIDENT
            ,E.CREATED_AT
            ,E.UPDATED_AT
            ,E.QUIT_YN
            ,E.PROFILE_ORIGIN_NAME
        FROM EMPLOYEE E
        JOIN JOB J ON (E.JOB_NO = J.NO)
        JOIN AGENCY A ON (E.AGENCY_NO = A.NO)
        WHERE E.NO = #{no}
        AND E.QUIT_YN = 'N'
    """)
    EmployeeManageVo selectOne(String no);

    @Update("""
    UPDATE EMPLOYEE
    SET
        NAME = #{name}
        ,EMAIL = #{email}
        ,PHONE = #{phone}
        ,AGENCY_NO = #{agencyNo}
        ,JOB_NO = #{jobNo}
        ,ADDRESS = #{address}
        ,ADDRESS2 = #{address2}
        ,UPDATED_AT = SYSDATE
    WHERE NO = #{no}
      AND QUIT_YN = 'N'
""")
    int updateByNo(EmployeeManageVo vo);

    @Update("""
        UPDATE EMPLOYEE
        SET
            QUIT_YN = 'Y'
            ,UPDATED_AT = SYSDATE
        WHERE NO = #{no}
    """)
    int deleteByNo(EmployeeManageVo vo);

    @Select("""
        SELECT COUNT(NO)
        FROM EMPLOYEE
        WHERE QUIT_YN = 'N'
    """)
    int selectCount();

    @Select("""
        SELECT
            E.NO
            ,E.AGENCY_NO
            ,A.NAME AS AGENCY_NAME
            ,E.ID
            ,E.PW
            ,E.NAME
            ,E.PHONE
            ,E.ADDRESS
            ,E.ADDRESS2
            ,E.JOB_NO
            ,J.JOB_NAME AS JOB_NAME
            ,E.EMAIL
            ,E.RESIDENT
            ,E.CREATED_AT
            ,E.UPDATED_AT
            ,E.QUIT_YN
            ,E.PROFILE_ORIGIN_NAME
        FROM EMPLOYEE E
        JOIN JOB J ON (E.JOB_NO = J.NO)
        JOIN AGENCY A ON (E.AGENCY_NO = A.NO)
        WHERE E.QUIT_YN = 'N'
        AND (
            (#{type} = 'name' AND E.NAME LIKE '%' || #{keyword} || '%')
            OR
            (#{type} = 'agencyName' AND A.NAME LIKE '%' || #{keyword} || '%')
            OR
            (#{type} = 'jobName' AND J.JOB_NAME LIKE '%' || #{keyword} || '%')
            OR
            (#{type} = 'email' AND E.EMAIL LIKE '%' || #{keyword} || '%')
        )
        ORDER BY E.NO DESC
    """)
    List<EmployeeManageVo> search(@Param("type") String type, @Param("keyword") String keyword);

    @Select("""
    SELECT
        NO
        ,JOB_NAME
    FROM JOB
    ORDER BY NO ASC
""")
    List<EmployeeManageVo> selectJobList();

    @Select("""
    SELECT
        NO
        ,NAME
    FROM AGENCY
    WHERE QUIT_YN = 'N'
    ORDER BY NAME ASC
""")
    List<EmployeeManageVo> selectAgencyList();
}