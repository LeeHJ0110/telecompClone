<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항 상세</title>
<script defer src="/js/board/notice/edit.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/notice/insert.css">

</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <header class="page-header">
        <h1 class="main-title">공지사항 수정,삭제</h1><input type="hidden" id="no" value="${noticeVo.no}">
    </header>

    <main class="content-body">
        <div class="input-group">
            <label for="title">제목</label>
            <input type="text" id="title" placeholder="제목을 작성해 주세요">
        </div>

        <div class="input-group">
            <label for="content">내용</label>
            <div class="textarea-container">
                <textarea id="content" placeholder="공지 내용을 입력해주세요"></textarea>
                <div class="dashed-box"></div>
            </div>
        </div>

        <div class="footer-row">
            <button type="button" class="submit-btn" onclick="edit();">수정하기</button>
            <button type="button" class="submit-btn" onclick="del();">삭제하기</button>
        </div>
    </main>
</div>
</body>
</html>