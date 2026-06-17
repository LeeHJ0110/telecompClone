package com.kh.six.employee;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Insert("""
       INSERT INTO EMPLOYEE
                (   AGENCY_NO
                    ,ID
                    ,PW
                    ,NAME
                    ,PHONE
                    ,ADDRESS
                    ,ADDRESS2
                    ,JOB_NO         
                    ,EMAIL                        
                    ,RESIDENT
                    )
                VALUES
                (
                #{agencyNo},
                #{id},
                #{pw},
                #{name},
                #{phone},
                #{address},
                #{address2},
                #{jobNo},
                #{email},
                #{resident})
    """)
    int join(EmployeeVo vo);


    @Select("""
        SELECT
            NO 
            ,AGENCY_NO
            ,ID
            ,PW
            ,NAME
            ,PHONE
        FROM EMPLOYEE
        WHERE ID = #{id}
        AND QUIT_YN = 'N'
    """)
    EmployeeVo selectById(String id);

}
