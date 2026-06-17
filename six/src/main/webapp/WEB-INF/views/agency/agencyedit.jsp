<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>대리점 수정</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/agency/agencyedit.css">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script defer src="${pageContext.request.contextPath}/js/agency/agencyedit.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="agency-edit-container">
    <div class="page-header">
        <h2>대리점 수정</h2>
    </div>

    <div class="edit-card">
        <div class="form-row">
            <label for="name">대리점명</label>
            <input type="text" id="name" placeholder="대리점명을 입력하세요">
        </div>

        <div class="form-row">
            <label for="managerNo">담당자</label>
            <select id="managerNo"></select>
        </div>

        <div class="form-row">
            <label for="phone">연락처</label>
            <input type="text" id="phone" placeholder="연락처를 입력하세요">
        </div>

        <div class="form-row address-row">
            <label for="postcode">우편번호</label>
            <div class="address-box">
                <input type="text" id="postcode" placeholder="우편번호" readonly>
                <button type="button" class="address-btn" onclick="execDaumPostcode()">주소 찾기</button>
            </div>
        </div>

        <div class="form-row">
            <label for="address">기본주소</label>
            <input type="text" id="address" placeholder="기본주소" readonly>
        </div>

        <div class="form-row">
            <label for="detailAddress">상세주소</label>
            <input type="text" id="detailAddress" placeholder="상세주소를 입력하세요">
        </div>
    </div>

    <div class="btn-area">
        <button type="button" class="btn primary" onclick="submitEdit()">저장</button>
        <button type="button" class="btn"
                onclick="moveToDetailPage()">
            취소
        </button>
    </div>
</div>

</body>
</html>