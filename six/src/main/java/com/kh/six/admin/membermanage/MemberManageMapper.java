package com.kh.six.admin.membermanage;

import com.kh.six.planConfirm.FixedInfoVo;
import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberManageMapper {

    @Select("""
        SELECT
            M.NO
            ,M.ID
            ,M.PW
            ,M.NAME
            ,F.PHONE
            ,M.ADDRESS
            ,M.ADDRESS2
            ,M.EMAIL
            ,M.RESIDENT
            ,M.CHANGE_NAME
            ,M.ORIGIN_NAME
            ,M.QUIT_YN
            ,F.CREATED_AT
            ,F.ACTIVE_YN
        FROM MEMBER M
        LEFT JOIN FIXED_INFO F ON (M.NO = F.MEMBER_NO)
        WHERE M.QUIT_YN = 'N'
        ORDER BY M.NO DESC
        OFFSET #{pvo.offset} ROWS
        FETCH NEXT #{pvo.boardLimit} ROWS ONLY
    """)
    List<MemberManageVo> selectList(@Param("pvo") PageVo pvo);

    @Select("""
        SELECT
            M.NO
            ,F.MEMBER_NO
            ,M.ID
            ,M.PW
            ,M.NAME
            ,F.PHONE
            ,M.ADDRESS
            ,M.ADDRESS2
            ,M.EMAIL
            ,M.RESIDENT
            ,M.CHANGE_NAME
            ,M.ORIGIN_NAME
            ,F.CREATED_AT
            ,F.MAIN_CONTRACT_END_DATE
            ,F.MAIN_CONTRACT_NO
            ,MAIN.TERM
            ,M.QUIT_YN
            ,P.NAME AS PLAN_NAME
            ,F.ACTIVE_YN
        FROM MEMBER M
        JOIN FIXED_INFO F ON (M.NO = F.MEMBER_NO)
        JOIN PLAN P ON (P.NO = F.PLAN_NO)
        JOIN MAIN_CONTRACT MAIN ON (MAIN.NO = F.MAIN_CONTRACT_NO)
        WHERE M.NO = #{no}
          AND M.QUIT_YN = 'N'
          AND F.PHONE = #{phone}
    """)
    MemberManageVo selectOne(@Param("no") String no, @Param("phone") String phone);

    @Select("""
        SELECT
            M.NO
            ,M.ID
            ,M.PW
            ,M.NAME
            ,M.ADDRESS
            ,M.ADDRESS2
            ,M.EMAIL
            ,M.RESIDENT
            ,M.CHANGE_NAME
            ,M.ORIGIN_NAME
            ,M.CREATED_AT
            ,M.UPDATED_AT
            ,M.QUIT_YN
        FROM MEMBER M
        WHERE M.NO = #{no}
          AND M.QUIT_YN = 'N'
    """)
    MemberManageVo selectOneByNo(@Param("no") String no);

    @Select("""
        SELECT *
        FROM (
            SELECT
                NO
                ,MEMBER_NO
                ,PHONE
                ,CREATED_AT
                ,DEL_AT
                ,MAIN_CONTRACT_END_DATE
                ,ACCOUNT_NO
                ,MAIN_CONTRACT_NO
                ,PLAN_NO
                ,ACTIVE_YN
            FROM FIXED_INFO
            WHERE MEMBER_NO = #{no}
              AND PHONE = #{phone}
            ORDER BY NO DESC
        )
        WHERE ROWNUM = 1
    """)
    FixedInfoVo selectFixedInfoOne(@Param("no") String no, @Param("phone") String phone);

    @Select("""
        SELECT
            NO
            ,MEMBER_NO
            ,PHONE
            ,CREATED_AT
            ,DEL_AT
            ,MAIN_CONTRACT_END_DATE
            ,ACCOUNT_NO
            ,MAIN_CONTRACT_NO
            ,PLAN_NO
            ,ACTIVE_YN
        FROM FIXED_INFO
        WHERE MEMBER_NO = #{no}
        ORDER BY NO DESC
    """)
    List<FixedInfoVo> selectFixedInfoList(@Param("no") String no);

    @Update("""
        UPDATE MEMBER
        SET 
            NAME = #{name}
            ,ADDRESS = #{address}
            ,ADDRESS2 = #{address2}
            ,EMAIL = #{email}
            ,UPDATED_AT = SYSDATE
        WHERE NO = #{no}
          AND QUIT_YN = 'N'
    """)
    int updateByNo(MemberManageVo vo);

    @Update("""
        UPDATE MEMBER
        SET
            QUIT_YN = 'Y'
            ,UPDATED_AT = SYSDATE
        WHERE NO = #{no}
    """)
    int deleteByNo(MemberManageVo vo);

    @Select("""
        SELECT COUNT(NO)
        FROM MEMBER
        WHERE QUIT_YN = 'N'
    """)
    int selectCount();

    @Select("""
        SELECT DISTINCT
            M.NO
            ,M.ID
            ,M.PW
            ,M.NAME
            ,M.ADDRESS
            ,M.ADDRESS2
            ,M.EMAIL
            ,M.RESIDENT
            ,M.CHANGE_NAME
            ,M.ORIGIN_NAME
            ,M.CREATED_AT
            ,M.QUIT_YN
        FROM MEMBER M
        LEFT JOIN FIXED_INFO F ON (M.NO = F.MEMBER_NO)
        WHERE M.QUIT_YN = 'N'
          AND (
              (#{type} = 'name' AND M.NAME LIKE '%' || #{keyword} || '%')
              OR
              (#{type} = 'phone' AND F.PHONE LIKE '%' || #{keyword} || '%')
              OR
              (#{type} = 'email' AND M.EMAIL LIKE '%' || #{keyword} || '%')
          )
        ORDER BY M.NO DESC
    """)
    List<MemberManageVo> search(@Param("type") String type, @Param("keyword") String keyword);

    @Select("""
        SELECT COUNT(NO)
        FROM QA
        WHERE WRITER_NO = #{no}
          AND DEL_YN = 'N'
    """)
    String qaCount(String no);

    @Select("""
        SELECT COUNT(S.NO)
        FROM SERVICE_CONTRACT S
        JOIN FIXED_INFO F ON (F.NO = S.FIXED_NO)
        WHERE F.MEMBER_NO = #{no}
          AND F.ACTIVE_YN = 'Y'
          AND S.USED_MONTH = TO_CHAR(SYSDATE, 'YYYYMM')
    """)
    String addServiceCount(String no);

    @Select("""
        SELECT COUNT(NO)
        FROM COUNSEL
        WHERE MEMBER_NO = #{no}
    """)
    String counselCount(String no);
}