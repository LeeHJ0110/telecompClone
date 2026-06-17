<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 신청 내역 조회</title>

<script>
const isAdmin = ${not empty sessionScope.loginAdminVo};
</script>

<script defer src="${pageContext.request.contextPath}/js/admin/memberconfirm/list.js"></script>
<script defer src="/js/common/format.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/memberconfirm/list.css">
</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="news-wrap">
    <div class="news-tabs">
        <span class="active">회원신청 내역 조회</span>
    </div>

    <br>

    <div class="faq-tabs">
        <span class="active" data-category="planInsert">요금제추가</span>
        <span data-category="planUpdate">요금제변경</span>
        <span data-category="planDelete">요금제삭제</span>
        <span data-category="serviceInsert">부가서비스추가</span>
        <span data-category="serviceDelete">부가서비스삭제</span>
    </div>

    <main></main>

    <div class="news-pagination">
        <div id="page-area"></div>
    </div>
</div>

</body>
</html>