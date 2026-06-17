package com.kh.six.board.notice;

import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {

    @Insert("""
        INSERT INTO NOTICE
        (
         TITLE
         ,CONTENT
         ,WRITER_NO
        )
        VALUES
        (
         #{title}
         ,#{content}
         ,#{writerNo}
        )
    """)
    int insert(NoticeVo vo);

    @Select("""
        SELECT
            N.NO
            ,N.TITLE
            ,N.CONTENT
            ,N.WRITER_NO
            ,E.NAME         AS EMPLOYEE_NAME
            ,N.HIT
            ,N.CREATED_AT
            ,N.UPDATED_AT
        FROM NOTICE N
        JOIN EMPLOYEE E ON (N.WRITER_NO = E.NO)
        WHERE N.DEL_YN = 'N'
        ORDER BY N.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<NoticeVo> selectList(PageVo pvo);


    @Select("""
        SELECT
            N.NO
            ,N.TITLE
            ,N.CONTENT
            ,N.WRITER_NO
            ,E.NAME         AS EMPLOYEE_NAME
            ,N.HIT
            ,N.CREATED_AT
            ,N.UPDATED_AT
        FROM NOTICE N
        JOIN EMPLOYEE E ON (N.WRITER_NO = E.NO)
        WHERE N.NO = #{no}
        AND N.DEL_YN = 'N'
    """)
    NoticeVo selectOne(String no);

    @Update("""
        UPDATE NOTICE
        SET
            TITLE = #{title}
            , CONTENT = #{content}
            , UPDATED_AT = SYSDATE
        WHERE NO = #{no}
        AND DEL_YN = 'N'
    """)
    int updateByNo(NoticeVo vo);

    @Update("""
        UPDATE NOTICE
        SET 
            DEL_YN = 'Y'
            , UPDATED_AT = SYSDATE
        WHERE NO = #{no}
    """)
    int deleteByNo(NoticeVo vo);

    @Select("""
        SELECT COUNT(NO)
        FROM NOTICE
        WHERE DEL_YN = 'N'
    """)
    int selectCount();

    @Select("""
    SELECT
        N.NO
        ,N.TITLE
        ,N.CONTENT
        ,N.WRITER_NO
        ,E.NAME AS EMPLOYEE_NAME
        ,N.HIT
        ,N.CREATED_AT
        ,N.UPDATED_AT
    FROM NOTICE N
    JOIN EMPLOYEE E ON (N.WRITER_NO = E.NO)
    WHERE N.NO < #{no}
    AND N.DEL_YN = 'N'
    ORDER BY N.NO DESC
    FETCH FIRST 1 ROWS ONLY
""")
    NoticeVo selectPrev(String no);

    @Select("""
    SELECT
        N.NO
        ,N.TITLE
        ,N.CONTENT
        ,N.WRITER_NO
        ,E.NAME AS EMPLOYEE_NAME
        ,N.HIT
        ,N.CREATED_AT
        ,N.UPDATED_AT
    FROM NOTICE N
    JOIN EMPLOYEE E ON (N.WRITER_NO = E.NO)
    WHERE N.NO > #{no}
    AND N.DEL_YN = 'N'
    ORDER BY N.NO ASC
    FETCH FIRST 1 ROWS ONLY
""")
    NoticeVo selectNext(String no);

    @Select("""
SELECT
    N.NO
    ,N.TITLE
    ,N.CONTENT
    ,N.WRITER_NO
    ,E.NAME AS EMPLOYEE_NAME
    ,N.HIT
    ,N.CREATED_AT
    ,N.UPDATED_AT
FROM NOTICE N
JOIN EMPLOYEE E ON (N.WRITER_NO = E.NO)
WHERE N.DEL_YN = 'N'
AND (
    (#{type} = 'title' AND N.TITLE LIKE '%' || #{keyword} || '%')
    OR
    (#{type} = 'content' AND N.CONTENT LIKE '%' || #{keyword} || '%')
)
ORDER BY N.NO DESC
""")
    List<NoticeVo> search(@Param("type") String type, @Param("keyword") String keyword);
}