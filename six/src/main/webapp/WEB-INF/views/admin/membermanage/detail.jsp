<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>고객 상세정보</title>

<script>
const isAdmin = ${not empty sessionScope.loginAdminVo};
</script>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/admin/membermanage/detail.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/membermanage/detail.css">
</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="wrap">
    <h2>고객정보 상세조회</h2>

    <div class="customer-card">
        <div class="top-area">
            <div class="customer-info edit-mode">

                <div class="form-row">
                    <label for="name">이름</label>
                    <input type="text" id="name" readonly>
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
                    <label for="address">주소</label>
                    <input type="text" id="address">
                </div>

                <div class="form-row">
                    <label for="address2">상세주소</label>
                    <input type="text" id="address2">
                </div>

                <div class="form-row">
                    <label for="email">이메일</label>
                    <input type="text" id="email" readonly>
                </div>

                <div class="form-row">
                    <label for="memberCreatedAt">가입일</label>
                    <input type="text" id="memberCreatedAt" readonly>
                </div>

            </div>

            <div class="edit-btn-area">
                <button class="small-btn" type="button" onclick="moveToEditPage()">정보수정</button>
                <button class="small-btn danger-btn" type="button" onclick="deleteMember()">삭제</button>
            </div>
        </div>

        <hr />

        <div class="middle-area">
            <div class="plan-info">
                <div class="form-row">
                    <label for="planName">요금제</label>
                    <input type="text" id="planName" readonly>
                </div>

                <div class="form-row">
                    <label for="term">약정기간</label>
                    <input type="text" id="term" readonly>
                </div>

                <div class="form-row">
                    <label for="contractStartDate">개통일</label>
                    <input type="text" id="contractStartDate" readonly>
                </div>
            </div>

            <div class="action-btns" id="action-btns">
                <button class="small-btn" onclick="location.href='/confirm/plan?selectTable=insert'">요금제 추가</button>
                <button class="small-btn" onclick="location.href='/confirm/service'">부가서비스 추가</button>
                <button class="small-btn" onclick="location.href='/confirm/plan?selectTable=update'">요금제 변경</button>
                <button class="small-btn" onclick="location.href='/confirm/service?selectTable=delete'">부가서비스 삭제</button>
                <button class="small-btn" onclick="location.href='/confirm/plan?selectTable=delete'">요금제 삭제</button>
            </div>
        </div>

        <hr />

        <div class="row-link">
            <span id="addServiceCount"></span>
            <span class="arrow">➜</span>
        </div>

        <hr />

        <div class="row-link" id="qaCount"></div>

        <div class="row-link row-last">
            <span id="counselCount"></span>
            <span class="arrow">➜</span>
        </div>
    </div>
</div>

</body>
</html>