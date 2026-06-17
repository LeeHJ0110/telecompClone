<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>텔레콤푸 소식</title>
<script defer src="${pageContext.request.contextPath}/js/common/format.js"></script>
<script defer src="${pageContext.request.contextPath}/js/board/qa/list.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/qa/list.css">

</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="news-wrap">

<div class="news-tabs">
<span onclick = "location.href = `/board/notice/list/1`">텔레콤푸소식</span>
<span onclick = "location.href = `/board/faq/list/1`">FAQ</span>
<span class="active">${(loginEmployeeVo != null || loginAdminVo != null) ? '고객문의' : '내 문의'}</span>
</div>

<div class="news-search">

<c:choose>


    <c:when test="${not empty sessionScope.loginAdminVo or not empty sessionScope.loginEmployeeVo}">

        <select id="searchType">
                    <option value="name">이름</option>
                    <option value="category">카테고리</option>
                </select>

                <input type="text" id="searchValue"
                       placeholder="내용을 입력해주세요."
                       oninput="searchNotice();" value="${vo.name}">

    </c:when>


    <c:otherwise>

        <select id="searchType">
            <option value="title">제목</option>
            <option value="content">내용</option>
        </select>

        <input type="text" id="searchValue"
               placeholder="검색어를 입력해주세요."
               oninput="searchNotice();">

    </c:otherwise>

</c:choose>

</div>

<main>
<table class="news-table">

<thead>
<tr>
<th class="news-category">구분</th>
<th>문의</th>
<th class="news-date">작성일</th>
<th class="news-check">답변여부</th>
</tr>
</thead>

<tbody id="notice-tbody">

</tbody>

</table>
</main>
<div class="news-pagination">
<div id="page-area"></div>
</div>

</div>

<c:if test="${not empty sessionScope.loginMemberVo}">
<div class="notice-btn-area">
<button class="notice-btn" onclick = "location.href = `/board/qa/insert`" >추가문의하기</button>
</c:if>

</div>


</body>
</html>