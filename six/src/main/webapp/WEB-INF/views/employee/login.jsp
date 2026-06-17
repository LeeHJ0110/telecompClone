<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>직원 로그인</title>
<script defer src="/js/employee/login.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/employee/join.css">

</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">

<div class="title">직원 로그인</div>

<div class="card">

<form>

    <input type="text" name="agencyNo" placeholder="대리점 코드" required>

    <input type="text" name="id" placeholder="아이디" required>

    <input type="password" name="pw" placeholder="비밀번호" required>

    <button type="button" class="submit-btn" onclick="login()">로그인</button>


</form>

</div>

</div>

</body>
</html>