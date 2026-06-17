package com.kh.six.mainContract;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MainContractMapper {
    @Insert("""
        INSERT INTO MAIN_CONTRACT
        (
        TERM
        ,DISCOUNT_RATE
        ,PENALTY
        )
        VALUES
        (
        #{term}
        ,#{discountRate}
        ,#{penalty}
        )
    """)
    int insert(MainContractVo vo);

    @Select("""
        SELECT *
        FROM MAIN_CONTRACT
    """)
    List<MainContractVo> selectList();

    @Delete("""
        DELETE FROM MAIN_CONTRACT
        WHERE NO = #{no}
    """)
    int delete(String no);

    @Update("""
        UPDATE MAIN_CONTRACT SET
            TERM = #{term}
            ,DISCOUNT_RATE = #{discountRate}
            ,PENALTY = #{penalty}
        WHERE NO = #{no}
    """)
    int update(MainContractVo vo);

    @Select("""
        SELECT *
        FROM MAIN_CONTRACT
        WHERE NO = #{no}
    """)
    MainContractVo selectOne(String no);
}
