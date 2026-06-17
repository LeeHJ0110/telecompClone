<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>고객 정보 수정</title>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/admin/membermanage/edit.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/membermanage/detail.css">
</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="wrap">
    <h2>고객정보 수정</h2>

    <div class="customer-card">
        <div class="top-area">
            <div class="customer-info edit-mode">

                <div class="form-row">
                    <label for="name">이름</label>
                    <input type="text" id="name">
                </div>

                <div class="form-row">
                    <label for="phoneSelect">전화번호</label>
                    <select id="phoneSelect"></select>
                </div>

                <div class="form-row">
                    <label for="resident">주민번호</label>
                    <input type="text" id="resident" readonly>
                </div>

                <div class="form-row">
                    <label for="postcode">우편번호</label>
                    <div class="address">
                        <input type="text" id="postcode" placeholder="우편번호" readonly style="flex: 1; background-color: #f5f5f5;">
                        <input type="button" class="small-btn" onclick="execDaumPostcode()" value="주소 찾기">
                    </div>
                </div>

                <div class="form-row">
                    <label for="address">주소</label>
                    <input type="text" id="address" readonly style="background-color: #f5f5f5;">
                </div>

                <div class="form-row">
                    <label for="address2">상세주소</label>
                    <input type="text" id="address2" placeholder="상세주소 입력">
                </div>

                <div class="form-row">
                    <label for="email">이메일</label>
                    <input type="text" id="email">
                </div>

                <div class="form-row">
                    <label for="memberCreatedAt">가입일</label>
                    <input type="text" id="memberCreatedAt" readonly>
                </div>

            </div>

            <div class="edit-btn-area">
                <button class="small-btn" type="button" onclick="submitEdit()">저장</button>
                <button class="small-btn" type="button" onclick="moveToDetailPage()">취소</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>