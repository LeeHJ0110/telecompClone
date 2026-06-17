<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>대리점 상세조회</title>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/agency/agencydetail.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/agency/agencydetail.css">
</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="agency-detail-container">
    <div class="page-header">
        <h2>대리점 상세조회</h2>
    </div>

    <div class="detail-card">
        <div class="form-row">
            <label for="name">대리점명</label>
            <input type="text" id="name" readonly>
        </div>

        <div class="form-row">
            <label for="managerName">담당자</label>
            <input type="text" id="managerName" readonly>
        </div>

        <div class="form-row">
            <label for="phone">연락처</label>
            <input type="text" id="phone" readonly>
        </div>

        <div class="form-row">
            <label for="address">주소</label>
            <input type="text" id="address" readonly>
        </div>

        <div class="form-row">
            <label for="createdAt">등록일</label>
            <input type="text" id="createdAt" readonly>
        </div>
    </div>

    <div class="btn-area">
        <button type="button" class="btn primary" onclick="moveToEditPage()">수정</button>
        <button type="button" class="btn danger" onclick="deleteAgency()">삭제</button>
        <button type="button" class="btn"
                onclick="location.href='${pageContext.request.contextPath}/employee/employeemanage/admin/store/list/1'">
            목록
        </button>
    </div>
</div>

</body>
</html>