<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>직원관리 </title>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/admin/membermanage/list.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/membermanage/list.css">

</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="employee-container">
    <h2>회원 조회</h2>

    <div class="search-box">
    <select id="searchType">
            <option value="name">이름</option>
            <option value="phone">전화번호</option>
            <option value="email">메일</option>
     </select>
        <input type="text" placeholder="검색" id="searchValue">
        <button onclick="searchNotice()">검색</button>
    </div>
    <div class="news-search">


    <div class="list" id="list">

    </div>
    <div class="pagination">
    <div id="page-area"></div>
    </div>
</div>


</body>
</html>