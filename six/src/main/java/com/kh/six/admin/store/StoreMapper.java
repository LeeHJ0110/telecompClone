package com.kh.six.admin.store;

import com.kh.six.employee.EmployeeVo;
import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Select("""
        SELECT COUNT(A.NO)
        FROM AGENCY A
        WHERE A.QUIT_YN = 'N'
    """)
    int selectCount();

    @Select("""
        SELECT
            A.NO
            ,A.MANAGER_NO
            ,E.NAME AS MANAGER_NAME
            ,A.NAME
            ,A.PHONE
            ,A.ADDRESS
            ,A.CREATED_AT
            ,A.UPDATED_AT
            ,A.QUIT_YN
        FROM AGENCY A
        LEFT JOIN EMPLOYEE E ON (A.MANAGER_NO = E.NO)
        WHERE A.QUIT_YN = 'N'
        ORDER BY A.NO DESC
        OFFSET #{pvo.offset} ROWS
        FETCH NEXT #{pvo.boardLimit} ROWS ONLY
    """)
    List<StoreVo> selectList(@Param("pvo") PageVo pvo);

    @Select("""
        SELECT
            A.NO
            ,A.MANAGER_NO
            ,E.NAME AS MANAGER_NAME
            ,A.NAME
            ,A.PHONE
            ,A.ADDRESS
            ,A.CREATED_AT
            ,A.UPDATED_AT
            ,A.QUIT_YN
        FROM AGENCY A
        LEFT JOIN EMPLOYEE E ON (A.MANAGER_NO = E.NO)
        WHERE A.NO = #{no}
          AND A.QUIT_YN = 'N'
    """)
    StoreVo selectOne(String no);

    @Insert("""
        INSERT INTO AGENCY
        (
            NO
            ,MANAGER_NO
            ,NAME
            ,PHONE
            ,ADDRESS
            ,CREATED_AT
            ,QUIT_YN
        )
        VALUES
        (
            SEQ_AGENCY.NEXTVAL
            ,#{managerNo}
            ,#{name}
            ,#{phone}
            ,#{address}
            ,SYSDATE
            ,'N'
        )
    """)
    int insert(StoreVo vo);

    @Update("""
        UPDATE AGENCY
        SET
            MANAGER_NO = #{managerNo}
            ,NAME = #{name}
            ,PHONE = #{phone}
            ,ADDRESS = #{address}
            ,UPDATED_AT = SYSDATE
        WHERE NO = #{no}
          AND QUIT_YN = 'N'
    """)
    int update(StoreVo vo);

    @Update("""
        UPDATE AGENCY
        SET
            QUIT_YN = 'Y'
            ,UPDATED_AT = SYSDATE
        WHERE NO = #{no}
          AND QUIT_YN = 'N'
    """)
    int deleteByNo(StoreVo vo);

    @Select("""
        SELECT
            A.NO
            ,A.MANAGER_NO
            ,E.NAME AS MANAGER_NAME
            ,A.NAME
            ,A.PHONE
            ,A.ADDRESS
            ,A.CREATED_AT
            ,A.UPDATED_AT
            ,A.QUIT_YN
        FROM AGENCY A
        LEFT JOIN EMPLOYEE E ON (A.MANAGER_NO = E.NO)
        WHERE A.QUIT_YN = 'N'
        AND (
            #{type} = ''
            OR
            (#{type} = 'name' AND A.NAME LIKE '%' || #{keyword} || '%')
            OR
            (#{type} = 'phone' AND A.PHONE LIKE '%' || #{keyword} || '%')
            OR
            (#{type} = 'address' AND A.ADDRESS LIKE '%' || #{keyword} || '%')
            OR
            (#{type} = 'managerName' AND E.NAME LIKE '%' || #{keyword} || '%')
        )
        ORDER BY A.NO DESC
    """)
    List<StoreVo> search(@Param("type") String type, @Param("keyword") String keyword);

    @Select("""
        SELECT
            NO
            ,NAME
            ,PHONE
        FROM EMPLOYEE
        WHERE QUIT_YN = 'N'
        ORDER BY NAME ASC
    """)
    List<EmployeeVo> selectEmployeeList();
}


