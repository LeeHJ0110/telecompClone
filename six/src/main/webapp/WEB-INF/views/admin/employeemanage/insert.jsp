<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세미</title>

<script defer src="${pageContext.request.contextPath}/js/admin/employeemanage/insert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/employeemanage/insert.css">

</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">

<div class="title">직원 계정생성</div>

<div class="card">

<!-- action 필요 없음 (AJAX로 보내기 때문) -->
<form id="joinForm" enctype="multipart/form-data">

    <div class="flex">
        <input type="text" name="id" placeholder="아이디" required>
        <input type="button" class="small-btn" onclick="Duplicate_Check();" name="duplicate" value="중복확인">
    </div>
    <span id="idCheck"></span>

    <input type="password" name="pw" placeholder="비밀번호" required>

    <input type="text" name="agencyNo" placeholder="대리점 번호">
    <input type="text" name="name" placeholder="이름">
    <input type="text" name="phone" placeholder="휴대폰 번호">

    <div class="address">
        <input type="text" name="postcode" id="postcode" placeholder="우편번호" readonly style="flex: 1; background-color: #f5f5f5;">
        <input type="button" class="small-btn" onclick="execDaumPostcode()" value="주소 찾기">
    </div>
    <input type="text" name="address" id="address" placeholder="주소">

    <input type="text" name="address2" id="address2" placeholder="상세 주소">

    <input type="email" name="email" placeholder="이메일">

    <input type="text" name="resident" placeholder="주민등록 번호">



    <button type="button" class="submit-btn" onclick="join()">계정 생성</button>

</form>

</div>

</div>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</body>
</html>