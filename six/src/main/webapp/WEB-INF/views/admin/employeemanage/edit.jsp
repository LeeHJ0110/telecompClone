<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>직원 정보 수정</title>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/admin/employeemanage/edit.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/employeemanage/edit.css">
</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="employee-edit-container">
    <div class="page-header">
        <h2>직원 정보 수정</h2>
    </div>

    <div class="edit-card">
        <div class="form-row">
            <label for="name">이름</label>
            <input type="text" id="name">
        </div>

        <div class="form-row">
            <label for="email">이메일</label>
            <input type="text" id="email">
        </div>

        <div class="form-row">
            <label for="phone">연락처</label>
            <input type="text" id="phone">
        </div>

        <div class="form-row">
            <label for="agencyNo">소속</label>
            <select id="agencyNo"></select>
        </div>

        <div class="form-row">
            <label for="jobNo">직책</label>
            <select id="jobNo"></select>
        </div>

        <div class="form-row">
            <label for="resident">주민번호</label>
            <input type="text" id="resident" readonly>
        </div>

        <div class="form-row">
            <label for="address">주소</label>
            <input type="text" id="address">
        </div>

        <div class="form-row">
            <label for="address2">상세주소</label>
            <input type="text" id="address2">
        </div>

        <div class="form-row">
            <label for="createdAt">등록일</label>
            <input type="text" id="createdAt" readonly>
        </div>
    </div>

    <div class="btn-area">
        <button type="button" class="btn primary" onclick="submitEdit()">저장</button>
        <button type="button" class="btn" onclick="moveToDetailPage()">취소</button>
    </div>
</div>

</body>
</html>