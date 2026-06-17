package com.kh.six.plan;

import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlanMapper {

    @Insert("""
        INSERT INTO PLAN
            (
            NAME
            ,PRICE
            ,DATA_TOTAL
            ,VOICE_TOTAL
            ,SMS_TOTAL
            ,DATA_NO
            ,DATA_SHARE
            ,CATEGORY
            ,DESCRIPTION
            )
            VALUES
            (
            #{name}
            ,#{price}
            ,#{dataTotal}
            ,#{voiceTotal}
            ,#{smsTotal}
            ,#{dataNo}
            ,#{dataShare}
            ,#{category}
            ,#{description}
            )
    """)
    int insert(PlanVo vo);

    @Select("""
        SELECT 
            NO
            ,NAME
            ,PRICE
            ,DATA_TOTAL
            ,VOICE_TOTAL
            ,SMS_TOTAL
            ,DATA_NO
            ,DATA_SHARE
            ,CATEGORY
        FROM PLAN
        WHERE SELL_YN = 'Y'
        ORDER BY CATEGORY
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<PlanVo> selectList(PageVo pvo);

    @Select("""
        SELECT COUNT(NO)
        FROM PLAN
        WHERE SELL_YN = 'Y'
    """)
    int selectCount();
    @Select("""
        SELECT 
            NO
            ,NAME
            ,PRICE
            ,DATA_TOTAL
            ,VOICE_TOTAL
            ,SMS_TOTAL
            ,DATA_NO
            ,DATA_SHARE
            ,CATEGORY
        FROM PLAN
        WHERE SELL_YN = 'Y'
        ORDER BY NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<PlanVo> selectListLatest(PageVo pvo);

    @Select("""
        SELECT 
            NO
            ,NAME
            ,PRICE
            ,DATA_TOTAL
            ,VOICE_TOTAL
            ,SMS_TOTAL
            ,DATA_NO
            ,DATA_SHARE
            ,CATEGORY
        FROM PLAN
        WHERE SELL_YN = 'Y'
        ORDER BY CATEGORY DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<PlanVo> selectListPopular(PageVo pvo);

    @Select("""
        SELECT 
            NO
            ,NAME
            ,PRICE
            ,DATA_TOTAL
            ,VOICE_TOTAL
            ,SMS_TOTAL
            ,DATA_NO
            ,DATA_SHARE
            ,CATEGORY
        FROM PLAN
        WHERE SELL_YN = 'Y'
        ORDER BY PRICE ASC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<PlanVo> selectListPriceAsc(PageVo pvo);

    @Select("""
        SELECT 
            NO
            ,NAME
            ,PRICE
            ,DATA_TOTAL
            ,VOICE_TOTAL
            ,SMS_TOTAL
            ,DATA_NO
            ,DATA_SHARE
            ,CATEGORY
        FROM PLAN
        WHERE SELL_YN = 'Y'
        ORDER BY PRICE DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<PlanVo> selectListPriceDesc(PageVo pvo);

    @Update("""
        UPDATE PLAN SET 
            NAME = #{name}
            ,PRICE = #{price}
            ,DATA_TOTAL = #{dataTotal}
            ,VOICE_TOTAL = #{voiceTotal}
            ,SMS_TOTAL = #{smsTotal}
            ,DATA_NO = #{dataNo}
            ,DATA_SHARE = #{dataShare}
            ,CATEGORY = #{category}
            ,DESCRIPTION = #{description}
        WHERE NO = #{no}
    """)
    int update(PlanVo vo);

    @Update("""
        UPDATE PLAN SET
            SELL_YN = 'N'
        WHERE NO = #{no}        
    """)
    int delete(String no);

    @Select("""
        SELECT *
        FROM PLAN
        WHERE NO = #{no}
        AND SELL_YN = 'Y'
    """)
    PlanVo selectOne(String no);


}
