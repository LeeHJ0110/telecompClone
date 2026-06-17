<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈</title>


<link rel="stylesheet" href="/css/admin/adminhome.css">


</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="admin-wrap">

<div class="admin-title">관리자 페이지</div>

<div class="admin-menu">

<!-- 고객정보 -->
<div class="admin-card" onclick="location.href='/employee/membermanage/list/1'">
<div class="material-symbols-outlined admin-icon">group</div>
<div class="admin-card-title">고객정보 조회</div>
<div class="admin-card-desc">회원 정보 및 가입 정보를 조회합니다</div>
</div>

<!-- 직원정보 -->
<div class="admin-card" onclick="location.href='/employee/employeemanage/list/1'">
<div class="material-symbols-outlined admin-icon">badge</div>
<div class="admin-card-title">직원정보 조회</div>
<div class="admin-card-desc">사내 직원 정보를 조회합니다</div>
</div>
<c:if test="${not empty sessionScope.loginAdminVo}">
<!-- 대리점 -->
<div class="admin-card" onclick="location.href='/employee/employeemanage/admin/store/list/1'">
<div class="material-symbols-outlined admin-icon">store</div>
<div class="admin-card-title">대리점정보 조회</div>
<div class="admin-card-desc">대리점 및 지점 정보를 조회합니다</div>
</div>
</c:if>


<!-- 상담내역 -->
<div class="admin-card" onclick="location.href='/employee/counsel/list/1'">
<div class="material-symbols-outlined admin-icon">support_agent</div>
<div class="admin-card-title">상담내역 조회</div>
<div class="admin-card-desc">고객 상담 및 문의 내역을 조회합니다</div>
</div>

<!-- 신청내역 -->
<div class="admin-card" onclick="location.href='/employee/membermanage/confirm-list/1'">
<div class="material-symbols-outlined admin-icon">description</div>
<div class="admin-card-title">신청내역 조회</div>
<div class="admin-card-desc">요금제 및 서비스 신청 내역을 조회합니다</div>
</div>

<c:if test="${not empty sessionScope.loginAdminVo}">
<div class="admin-card" onclick="location.href='${pageContext.request.contextPath}/employee/employeemanage/admin/insert'">
<div class="material-symbols-outlined admin-icon">person_add</div>
<div class="admin-card-title">신규직원 가입</div>
<div class="admin-card-desc">신규 직원 계정을 생성합니다</div>
</div>
</c:if>
<!-- 신규직원 가입 -->




</div>

</div>



</body>
</html>