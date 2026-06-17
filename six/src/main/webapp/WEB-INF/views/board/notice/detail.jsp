<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항 작성</title>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/board/notice/detail.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/notice/detail.css">

</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!-- ===== 공지사항 상세 ===== -->

<div class="notice-wrap">

<div class="notice-title">공지사항</div>

<div class="notice-header">
<div name="title"></div>
<div class="notice-date" id="createdAt"></div>
</div>
<div class="notice-content" id="notice-content"></div>


<!-- 이전글 다음글 -->
<div class="notice-nav">

 <div class="notice-nav-item">
 <div class="notice-nav-label">이전글</div>
 <a href="#" id="afterlist"></a>
 </div>

 <div class="notice-nav-item">
 <div class="notice-nav-label">다음글</div>
 <a href="#" id="beforelist"></a>
 </div>

 </div>


<div class="notice-btn-area">
<button class="notice-btn" onclick = "location.href = `/board/notice/list/1`" >목록</button>
<c:if test="${not empty sessionScope.loginAdminVo or not empty sessionScope.loginEmployeeVo}">
<button class="notice-btn" onclick = "moveToEditPage();" >수정하기</button>
</c:if>
</div>

</div>

</body>
</html>