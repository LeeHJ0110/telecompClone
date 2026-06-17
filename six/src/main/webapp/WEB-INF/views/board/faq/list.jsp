<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FAQ</title>
<script>
const isAdmin = ${not empty sessionScope.loginAdminVo};
</script>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/board/faq/list.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/faq/list.css">

</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="news-wrap">
 <!-- 탭 -->
    <div class="news-tabs">
        <span onclick = "location.href = `/board/notice/list/1`">텔레콤푸소식</span>
        <span class="active">FAQ</span>
        <span onclick = "location.href = `/board/qa/list/1`">${(loginEmployeeVo != null || loginAdminVo != null) ? '고객문의' : '내 문의'}</span>
    </div>
    <!-- 탭 -->
     <br>
    <div class="faq-tabs">
        <span class="active" data-category="서비스문의">서비스문의</span>
        <span data-category="요금제문의">요금제문의</span>
        <span data-category="약정문의">약정문의</span>
        <span data-category="부가서비스문의">부가서비스문의</span>
        <span data-category="대리점문의">대리점문의</span>
    </div>
    <main></main>

</div>
</div>


<c:if test="${not empty sessionScope.loginAdminVo}">
<div class="notice-btn-area">
<button class="notice-btn" onclick = "location.href = `/board/faq/insert`" >추가작성</button>
</div>
</c:if>
</main>
<div class="news-pagination">
<div id="page-area"></div>
</div>

</div>


</body>
</html>
