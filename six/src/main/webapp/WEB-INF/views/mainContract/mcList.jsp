
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>약정정보</title>
    <link rel="stylesheet" href="/css/mainContract/mcList.css">
    <script defer src="/js/mainContract/mcList.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <script>
        const LOGIN_MEMBER = ${loginMemberVo != null};
        const LOGIN_ADMIN = ${loginAdminVo != null};
    </script>
    <div class="contract-page">   
        <div class="page-header">
            <h1>약정 정책 리스트</h1>
            <c:if test="${not empty sessionScope.loginAdminVo}">
                <button class="add-btn" onclick="location.href = `/main-contract/admin/insert`">+ 신규 약정 등록</button>
            </c:if>
        </div>
        <div class="contract-list">
            
        </div>
    </div>
</body>
</html>