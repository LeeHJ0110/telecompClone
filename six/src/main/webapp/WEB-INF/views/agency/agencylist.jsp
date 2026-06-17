<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>대리점 관리</title>
<script defer src="${pageContext.request.contextPath}/js/agency/agencylist.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/agency/agencylist.css">
</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="agency-container">
    <div class="page-header">
        <h2>대리점 조회</h2>
        <button class="write-btn" type="button"
                onclick="location.href='${pageContext.request.contextPath}/employee/employeemanage/admin/store/write'">
            대리점 등록
        </button>
    </div>

    <div class="search-box">
        <select id="searchType">
            <option value="name">대리점명</option>
            <option value="phone">번호</option>
            <option value="address">주소</option>
            <option value="managerName">담당자명</option>
        </select>

        <input type="text" id="searchValue" placeholder="검색어를 입력하세요">
        <button type="button" onclick="searchNotice()">검색</button>
    </div>

    <div class="list-wrap">
        <div class="row header">
            <span class="no">번호</span>
            <span class="name">대리점명</span>
            <span class="manager">담당자명</span>
            <span class="phone">연락처</span>
            <span class="address">주소</span>
        </div>

        <div class="list" id="list"></div>
    </div>

    <div class="pagination">
        <div id="page-area"></div>
    </div>
</div>

</body>
</html>