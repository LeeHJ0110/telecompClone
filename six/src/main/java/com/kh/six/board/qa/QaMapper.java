package com.kh.six.board.qa;

import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QaMapper {
    @Insert("""
        INSERT INTO QA
        (
         TITLE
         ,CONTENT
         ,CATEGORY
         ,WRITER_NO
        )
        VALUES
        (
         #{title}
         ,#{content}
         ,#{category}
         ,#{writerNo}
        )
    """)
    int insert(QaVo vo);

    @Select("""
    SELECT
                    Q.NO
                    ,Q.TITLE
                    ,Q.CONTENT
                    ,Q.CATEGORY
                    ,Q.WRITER_NO
                    ,M.NAME     AS WRITER_NAME
                    ,(
                        SELECT COUNT(*)
                        FROM REPLY R
                        WHERE R.QA_NO = Q.NO
                     ) AS REPLY_COUNT
                    ,Q.CREATED_AT
                    ,Q.UPDATED_AT
                FROM QA Q
                JOIN MEMBER M ON (Q.WRITER_NO = M.NO)
                WHERE Q.DEL_YN = 'N'
                AND Q.WRITER_NO = #{no}
                ORDER BY Q.NO DESC
                OFFSET #{pvo.offset} ROWS
                FETCH NEXT #{pvo.boardLimit} ROWS ONLY
""")
    List<QaVo> selectList(PageVo pvo, String no);


    @Select("""
        SELECT
            Q.NO
            ,Q.TITLE
            ,Q.CONTENT
            ,Q.CATEGORY
            ,Q.WRITER_NO
            ,M.NAME     AS WRITER_NAME
            ,Q.CREATED_AT
            ,Q.UPDATED_AT
        FROM QA Q
        JOIN MEMBER M ON (Q.WRITER_NO = M.NO)
        WHERE Q.NO = #{no}
        AND Q.DEL_YN = 'N'
    """)
    QaVo selectOne(String no);

    @Select("""
        SELECT COUNT(NO)
        FROM QA
        WHERE DEL_YN = 'N'
    """)
    int selectCount();

    @Select("""
            SELECT
                Q.NO
                ,Q.TITLE
                ,Q.CONTENT
                ,Q.CATEGORY
                ,Q.WRITER_NO
                ,M.NAME AS WRITER_NAME
                ,Q.CREATED_AT
                ,Q.UPDATED_AT
                ,(
                    SELECT COUNT(*)
                    FROM REPLY R
                    WHERE R.QA_NO = Q.NO
                ) AS REPLY_COUNT
            FROM QA Q
            JOIN MEMBER M ON (Q.WRITER_NO = M.NO)
            WHERE Q.DEL_YN = 'N'
            AND (
                (#{type} = 'title' AND Q.TITLE LIKE '%' || #{keyword} || '%')
                OR
                (#{type} = 'content' AND Q.CONTENT LIKE '%' || #{keyword} || '%')
                OR
                (#{type} = 'name' AND M.NAME LIKE '%' || #{keyword} || '%')
            )
            ORDER BY Q.NO DESC
            
""")
    List<QaVo> search(@Param("type") String type, @Param("keyword") String keyword);

    @Select("""
    SELECT
                    Q.NO
                    ,Q.TITLE
                    ,Q.CONTENT
                    ,Q.CATEGORY
                    ,Q.WRITER_NO
                    ,M.NAME     AS WRITER_NAME
                    ,(
                        SELECT COUNT(*)
                        FROM REPLY R
                        WHERE R.QA_NO = Q.NO
                     ) AS REPLY_COUNT
                    ,Q.CREATED_AT
                    ,Q.UPDATED_AT
                FROM QA Q
                JOIN MEMBER M ON (Q.WRITER_NO = M.NO)
                WHERE Q.DEL_YN = 'N'
    ORDER BY Q.CREATED_AT DESC
    OFFSET #{offset} ROWS
    FETCH NEXT #{boardLimit} ROWS ONLY
""")
    List<QaVo> selectListAdmin(PageVo pvo);

}
