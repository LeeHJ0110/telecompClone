<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>요금제 신청 내역</title>
    <link rel="stylesheet" href="/css/confirm/plan/plan.css">
    <script defer src="/js/common/format.js"></script>
    <script defer src="/js/confirm/plan/plan.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <main class="history-container">
        <section class="title-area">
            <h2>요금제 신규 신청 내역</h2>
            <p>신청하신 신규 신청 건의 처리 상태를 확인할 수 있습니다.</p>
        </section>

        <div class="sort-box">
            <select id="sortSelect" class="sort-select">
                <option value="latest">최신순</option>
                <option value="oldest">오래된순</option>
                <option value="confirm">처리순</option>
            </select>
        </div>

        <div class="history-list">

        </div>
        <div id="page-area"></div>
        <p class="notice-text">
            * 요금제 신청 상태에 따라 적용 시점이 달라질 수 있으며, 요금은 다음 청구 시 반영될 수 있습니다.
        </p>
    </main>
</body>
</html>