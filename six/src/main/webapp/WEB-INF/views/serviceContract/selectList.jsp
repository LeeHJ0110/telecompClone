<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>부가서비스 신청 내역</title>
    <link rel="stylesheet" href="/css/serviceContract/list.css">
    <script defer src="/js/common/format.js"></script>
    <script defer src="/js/serviceContract/list.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <main class="history-container">
        <section class="title-area">
            <h2>부가서비스 가입 내역</h2>
            <p>가입하신 부가서비스를 확인합니다.</p>
        </section>

        <div class="sort-box">
            <select id="sortSelect" class="sort-select">
                <option value="latest">최신순</option>
                <option value="name">이름순</option>
                <option value="phone">전화번호순</option>
            </select>
        </div>

        <div class="history-list">

        </div>

        <p class="notice-text">
            * 부가서비스는 신청 상태에 따라 적용 시점이 달라질 수 있으며, 요금은 다음 청구 시 반영될 수 있습니다.
        </p>
    </main>
</body>
</html>