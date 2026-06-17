package com.kh.six.event;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EventMapper {

    @Select("""
        SELECT COUNT(ATTEND_DATE)
        FROM ATTENDANCE
        WHERE USER_ID = #{no}
    """)
    int checkCount(String no);

    @Insert("""
        INSERT INTO ATTENDANCE
        (
        USER_ID
        )
        VALUES
        (
        #{no}
        )
    """)
    int insertAttendance(String no);

}
