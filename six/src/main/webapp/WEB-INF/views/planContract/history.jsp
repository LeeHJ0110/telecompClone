<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>요금제 사용내역 - 치무SIX 텔레콤</title>
    <link rel="stylesheet" href="/css/planContract/history.css">
    <script defer src="/js/planContract/history.js"></script>
    <script defer src="/js/common/format.js"></script>
</head>
<body>

    <%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="main-container">
    <div class="title-area">
        <h1 class="main-title">요금제 사용내역</h1>
        <p class="sub-title">고객님의 월별 실시간 데이터 및 통화 사용량입니다.</p>
    </div>

    <div class="date-selector-container">
        <div class="date-underline-view">
            <button class="arrow-btn left" onclick="prevMonth()">◀</button>
            <div class="current-date">
                <span class="date-text">2026년 02월</span>
                <div class="accent-line"></div> </div>
            <button class="arrow-btn right" onclick="nextMonth()">▶</button>
        </div>
    </div>

    <div class="usage-list-area">
        <table class="usage-table">
            <thead>
                <tr>
                    <th>전화번호</th>
                    <th>사용데이터(MB)</th>
                    <th>사용통화(분)</th>
                    <th>사용문자(건)</th>
                    <th>요금제</th>
                </tr>
            </thead>
            <tbody id="usageListBody"></tbody>
        </table>
    </div>
</div>

</body>
</html>