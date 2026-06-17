package com.kh.six.admin.counsel;

import com.kh.six.admin.employeemanage.EmployeeManageVo;
import com.kh.six.admin.membermanage.MemberManageVo;
import com.kh.six.member.MemberVo;
import com.kh.six.util.PageVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CounselMapper {

    @Select("""
        SELECT COUNT(NO)
        FROM COUNSEL
    """)
    int selectCount();

    @Insert("""
        INSERT INTO COUNSEL
        (
         MEMBER_NO
         ,CONTENT
         ,WRITER_NO
        )
        VALUES
        (
         #{memberNo}
         ,#{content}
         ,#{writerNo}
        )
    """)
    int insert(CounselVo vo);

    @Select("""
        SELECT
            C.NO
            ,C.CONTENT
            ,C.WRITER_NO
            ,C.MEMBER_NO
            ,E.NAME AS WRITER_NAME
            ,M.NAME AS MEMBER_NAME
            ,C.COUNSEL_DATE
            ,M.PHONE
        FROM COUNSEL C
        JOIN EMPLOYEE E ON (C.WRITER_NO = E.NO)
        JOIN MEMBER M ON (C.MEMBER_NO =M.NO)
        ORDER BY C.NO DESC
        OFFSET #{offset} ROWS
        FETCH NEXT #{boardLimit} ROWS ONLY
    """)
    List<CounselVo> selectList(PageVo pvo);

    @Select("""
        SELECT
            M.NO
            ,M.NAME
            ,F.PHONE
        FROM MEMBER M
        JOIN FIXED_INFO F ON (M.NO = F.MEMBER_NO)
        WHERE M.NAME = #{name}
        AND F.PHONE = #{phone}
        FETCH FIRST 1 ROWS ONLY
        """)
    MemberVo checkMember(@Param("name") String name, @Param("phone") String phone);

    @Select("""
        SELECT
            NO
            ,NAME
            ,PHONE
        FROM MEMBER
        WHERE NAME = #{name}
        AND PHONE = #{phone}
        """)
    MemberVo checkMember2(String name, String phone);

    @Select("""
    SELECT
        C.NO
        ,C.CONTENT
        ,C.WRITER_NO
        ,C.MEMBER_NO
        ,E.NAME AS WRITER_NAME
        ,M.NAME AS MEMBER_NAME
        ,C.COUNSEL_DATE
        ,M.PHONE
    FROM COUNSEL C
    JOIN EMPLOYEE E ON (C.WRITER_NO = E.NO)
    JOIN MEMBER M ON (C.MEMBER_NO = M.NO)
    WHERE M.NAME LIKE '%' || #{keyword} || '%'
    ORDER BY C.NO DESC
""")
    List<CounselVo> search(@Param("keyword") String keyword);
}

