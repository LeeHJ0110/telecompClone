package com.kh.six.board.faq;

import com.kh.six.board.notice.NoticeVo;
import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FaqMapper {
    @Insert("""
    INSERT INTO FAQ
    (
     QUESTION
     ,ANSWER
     ,WRITER_NO
     ,CATEGORY
     ,UPDATED_AT
    )
    VALUES
    (
     #{question}
     ,#{answer}
     ,#{writerNo}
     ,#{category}
     ,SYSDATE
    )
""")
    int insert(FaqVo vo);

    @Select("""
    SELECT
        F.NO
        ,F.QUESTION
        ,F.ANSWER
        ,F.CATEGORY
        ,F.WRITER_NO
        ,E.NAME AS EMPLOYEE_NAME
        ,F.CREATED_AT
        ,F.UPDATED_AT
    FROM FAQ F
    JOIN EMPLOYEE E ON (F.WRITER_NO = E.NO)
    WHERE F.DEL_YN = 'N'
    AND (#{category} IS NULL OR F.CATEGORY = #{category})
    ORDER BY F.NO DESC
    OFFSET #{pvo.offset} ROWS
    FETCH NEXT #{pvo.boardLimit} ROWS ONLY
""")
    List<FaqVo> selectList(
            @Param("pvo") PageVo pvo,
            @Param("category") String category
    );
    @Select("""
        SELECT
            F.NO
                ,F.QUESTION
                ,F.ANSWER
                ,F.CATEGORY
                ,F.WRITER_NO
                ,E.NAME AS EMPLOYEE_NAME
                ,F.CREATED_AT
                ,F.UPDATED_AT
        FROM FAQ F
        JOIN EMPLOYEE E ON (F.WRITER_NO = E.NO)
        WHERE F.NO = #{no}
        AND F.DEL_YN = 'N'
    """)
    FaqVo selectOne(String no);


    @Update("""
        UPDATE FAQ
        SET
            QUESTION = #{question}
            , ANSWER = #{answer}
            , CATEGORY = #{category}
            , UPDATED_AT = SYSDATE
        WHERE NO = #{no}
        AND DEL_YN = 'N'
    """)
    int updateByNo(FaqVo vo);

    @Update("""
        UPDATE FAQ
        SET 
            DEL_YN = 'Y'
            , UPDATED_AT = SYSDATE
        WHERE NO = #{no}
    """)
    int deleteByNo(FaqVo vo);

    @Select("""
    SELECT COUNT(NO)
    FROM FAQ
    WHERE DEL_YN = 'N'
    AND (#{category} IS NULL OR CATEGORY = #{category})
""")
    int selectCount(String category);

}
