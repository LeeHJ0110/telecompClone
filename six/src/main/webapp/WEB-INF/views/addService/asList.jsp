
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부가서비스 리스트</title>
<link rel="stylesheet" href="/css/addService/asList.css">
  <script>
    const LOGIN_MEMBER = ${loginMemberVo != null};
    const LOGIN_ADMIN = ${loginAdminVo != null};
    const loginEmployee = ${loginEmployeeVo != null};
  </script>
<script defer src="/js/addService/asList.js"></script>

</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="container">
        <header class="page-header">
            <h1>모바일서비스</h1>

            <c:if test="${not empty sessionScope.loginAdminVo}">
                <button class="add-btn" onclick="location.href='/add-service/admin/insert'">
                    + 신규 서비스
                </button>
            </c:if>
        </header>

        <nav class="tabs">
            <a href="#" data-category="ALL">전체</a>
            <a href="#" data-category="DATA">데이터 추가</a>
            <a href="#" data-category="OTT">콘텐츠 서비스</a>
            <a href="#" data-category="SECURITY">보안 서비스</a>
            <a href="#" data-category="INSURANCE">단말기 보험</a>
            <a href="#" data-category="ROAMING">해외로밍</a>
        </nav>

        <section class="service-list">
        </section>
        
        <div class="paging-wrap">
            <div class="paging"></div>
        </div>
    </div>
</body>
</html>




