<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>공지사항 상세</title>
<script defer src="${pageContext.request.contextPath}/js/board/qa/insert.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/qa/insert.css">

</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <header class="page-header">
        <h1 class="main-title">문의 작성</h1>
    </header>

    <main class="content-body">
        <div class="input-group">
                            <label for="category">카테고리</label>
                                <select id="category">
                                    <option value="">카테고리 선택</option>
                                    <option value="서비스문의">서비스문의</option>
                                    <option value="요금제문의">요금제문의</option>
                                    <option value="약정문의">약정문의</option>
                                    <option value="부가서비스문의">부가서비스문의</option>
                                    <option value="대리점문의">대리점문의</option>
                                </select>
                </div>

        <div class="input-group">
            <label for="title">제목</label>
            <input type="text" id="title" placeholder="제목을 작성해 주세요">
        </div>

        <div class="input-group">
            <label for="content">내용</label>
            <div class="textarea-container">
                <textarea id="content" placeholder="문의 사항을 입력해주세요"></textarea>
                <div class="dashed-box"></div>
            </div>
        </div>

        <div class="footer-row">

            <button type="submit" class="submit-btn" onclick="insert();">등록하기</button>
        </div>
    </main>
</div>
</body>
</html>