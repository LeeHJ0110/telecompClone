<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>상담이력 관리 </title>

<script defer src="${pageContext.request.contextPath}/js/admin/counsel/list.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/counsel/list.css">

</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="consult-wrap">
    <h2>상담내역</h2>
        <div class="search-box">
            <input type="text" id="searchValue" placeholder="고객이름 입력">
            <button type="button" onclick="searchCounsel()">검색하기</button>
        </div>

        <div class="consult-header">
            <div>번호</div>
            <div>이름</div>
            <div>전화번호</div>
            <div>상담날짜</div>
            <div>상담직원</div>
        </div>

        <div id="consultList" class="consult-list"></div>

        <div id="page-area"></div>

        <div class="consult-detail-box" id="consultContent">
            상담 내용을 선택해주세요.
        </div>

        <div class="footer-row">
                    <button type="button" class="submit-btn" onclick="location.href='/employee/counsel/insert'">신규작성</button>
        </div>


</body>
</html>