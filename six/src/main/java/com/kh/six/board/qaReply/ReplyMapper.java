package com.kh.six.board.qaReply;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReplyMapper {

    @Insert("""
            INSERT INTO REPLY
            (
            CONTENT
            ,QA_NO
            ,EMPLOYEE_NO
            )
            VALUES
            (
            #{content}
            ,#{qaNo}
            ,#{employeeNo}
            )
            """)
    int insert(ReplyVo vo);

    @Select("""
        SELECT
            R.NO
            ,R.CONTENT
            ,R.QA_NO
            ,R.EMPLOYEE_NO
            ,R.CREATED_AT
            ,E.NAME         AS EMPLOYEE_NAME
        FROM REPLY R
        JOIN EMPLOYEE E ON (E.NO = R.EMPLOYEE_NO)
        WHERE R.QA_NO = #{qaNo}
        ORDER BY R.NO DESC
        """)
    List<ReplyVo> selectList(String boardNo);
}
