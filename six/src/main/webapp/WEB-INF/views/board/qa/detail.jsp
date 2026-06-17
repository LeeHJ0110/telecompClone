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
<script defer src="${pageContext.request.contextPath}/js/board/qa/detail.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/qa/detail.css">

</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>


<div class="notice-wrap">

<div class="notice-title">${(loginEmployeeVo != null || loginAdminVo != null) ? '고객문의' : '내 문의'}</div>

<div class="notice-header">
<div name="title"></div>
<div class="notice-date" id="createdAt"></div>
</div>
<div class="notice-content" id="notice-content"></div>

<div id="reply-list-area"></div>




<div class="notice-btn-area">
<button class="notice-btn" onclick = "location.href = `/board/qa/list/1`" >목록</button>


<c:if test="${not empty sessionScope.loginAdminVo or not empty sessionScope.loginEmployeeVo}">
<div id="reply-insert-area">
    <textarea name="reply-content" placeholder="댓글을 입력하세요"></textarea>
    <button onclick="insertReply();">댓글등록</button>
</div>
</c:if>

</div>

</div>

</body>
</html>