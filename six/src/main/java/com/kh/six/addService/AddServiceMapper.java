package com.kh.six.addService;

import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddServiceMapper {
    @Select("""
        SELECT COUNT(NO)
            FROM ADD_SERVICE
            WHERE SELL_YN = 'Y'
            AND (#{category} = 'ALL' OR CATEGORY = #{category})
    """)
    int selectCount(String category);

    @Select("""
        SELECT
            NO
            ,SERVICE
            ,CONTENT
            ,PRICE
            ,CREATED_AT
            ,CATEGORY
        FROM ADD_SERVICE
        WHERE SELL_YN = 'Y'
        AND (#{category} = 'ALL' OR CATEGORY = #{category})
        ORDER BY NO DESC
        OFFSET #{pvo.offset} ROWS
        FETCH NEXT #{pvo.boardLimit} ROWS ONLY
    """)
    List<AddServiceVo> selectList(@Param("pvo") PageVo pvo,
                                  @Param("category") String category);

    @Update("""
        UPDATE ADD_SERVICE
            SET SELL_YN = 'N'
            WHERE NO = #{no}
    """)
    int delete(String no);

    @Insert("""
        INSERT INTO ADD_SERVICE
            (
            SERVICE,
            CONTENT,
            PRICE,
            CATEGORY
            )
            VALUES
            (
            #{service},
            #{content},
            #{price},
            #{category}
            )
    """)
    int insert(AddServiceVo vo);
    @Update("""
        UPDATE ADD_SERVICE
            SET
                SERVICE = #{service}
                ,CONTENT = #{content}
                ,PRICE = #{price}
                ,CATEGORY = #{category}
                WHERE NO = #{no}
    """)
    int update(AddServiceVo vo);

    @Select("""
        SELECT *
        FROM ADD_SERVICE
        WHERE NO = #{no}
        AND SELL_YN = 'Y'
    """)
    AddServiceVo selectOne(String no);
}
