
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>요금제</title>
  <link rel="stylesheet" href="/css/plan/plan.css">
  <script>
    const LOGIN_MEMBER = ${loginMemberVo != null};
    const LOGIN_ADMIN = ${loginAdminVo != null};
    const loginEmployee = ${loginEmployeeVo != null};
  </script>
  <script defer src="/js/plan/planList.js"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/common/header.jsp" %>
  <main>
      <div class="plan-sort-area">
        <c:if test="${not empty sessionScope.loginAdminVo}">
        <button class="add-btn" onclick="location.href=`/plan/admin/insert`">+ 신규 요금제 등록</button>
        </c:if>
        <span class="sort-title">정렬</span>

        <select id="sort-select">
          <option value="latest">최신순</option>
          <option value="popular">인기순</option>
          <option value="priceAsc">가격 낮은순</option>
          <option value="priceDesc">가격 높은순</option>
        </select>
      </div>
    <div id="plan-area"></div>
    <div id="page-area"></div>
  </main>


 
</body>
</html>