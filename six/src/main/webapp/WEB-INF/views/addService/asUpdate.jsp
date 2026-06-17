
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>부가서비스 수정</title>
    <link rel="stylesheet" href="/css/addService/asList.css">
    <script defer src="/js/addService/asUpdate.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="container">
        <header class="page-header">
            <h1>서비스 수정하기</h1>
        </header>
        <div class="service-item">
            <div class="info">
                <div class="tags">
                    <span class="tag purple">
                        <select name="category" id="">
                            <option value="DATA">데이터추가</option>
                            <option value="OTT">OTT콘텐츠</option>
                            <option value="SECURITY">보안서비스</option>
                            <option value="INSURANCE">보험</option>
                            <option value="ROAMING">로밍</option>
                        </select>
                    </span>
                </div>
                <h3> <input type="text" name="service" id="" placeholder="상품명"> <span class="arrow">></span></h3>
                <p><input type="text" name="content" id="" placeholder="상품설명"></p>
            </div>
            <div class="action">
                <div class="price"><input type="text" name="price" id="" placeholder="월 요금"> <span>부가세 포함 금액</span></div>
                <button class="btn-join" onclick="update();">저장하기</button>
            </div>
        </div>

</body>
</html>