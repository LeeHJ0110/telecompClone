<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>상담내역 작성</title>
<script defer src="${pageContext.request.contextPath}/js/admin/counsel/insert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/counsel/insert.css">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <header class="page-header">
        <h1 class="main-title">상담내역 작성</h1>
    </header>

    <main class="content-body">

        <div class="input-group">
            <label for="name">회원 이름</label>
            <input type="text" id="name" placeholder="회원 이름을 입력해주세요">

            <label for="phone">전화번호</label>
            <input type="text" id="phone" placeholder="전화번호를 입력해주세요">

            <!-- 조회된 회원 번호 저장 -->
            <input type="hidden" id="memberNo">

            <!-- 조회 결과 문구 -->
            <div id="member-check-msg" class="member-check-msg"></div>

            <button type="button" class="submit-btn" onclick="check();">회원조회</button>
        </div>

        <div class="input-group">
            <label for="content">내용</label>
            <div class="textarea-container">
                <textarea id="content" placeholder="상담내용을 입력해주세요"></textarea>
                <div class="dashed-box"></div>
            </div>
        </div>

        <div class="footer-row">
            <button type="button" class="submit-btn" onclick="insert();">등록하기</button>
        </div>
    </main>
</div>

</body>
</html>