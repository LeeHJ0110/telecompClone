<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FAQ 작성</title>
<script defer src="${pageContext.request.contextPath}/js/board/faq/edit.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/faq/insert.css">

</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">
    <header class="page-header">
        <h1 class="main-title">FAQ 작성</h1><input type="hidden" id="no">
    </header>



    <main class="content-body">
        <div class="input-group">
                    <label for="category">카테고리</label>
                        <select id="category" value="${vo.category}">
                            <option value="">카테고리 선택</option>
                            <option value="서비스문의">서비스문의</option>
                            <option value="요금제문의">요금제문의</option>
                            <option value="약정문의">약정문의</option>
                            <option value="부가서비스문의">부가서비스문의</option>
                            <option value="대리점문의">대리점문의</option>
                        </select>
        </div>

        <div class="input-group">
            <label for="title">질문</label>
            <input type="text" id="question" value="${vo.question}">
        </div>

        <div class="input-group">
            <label for="content">답변</label>
            <div class="textarea-container">
                <textarea id="answer" >${vo.answer}</textarea>
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