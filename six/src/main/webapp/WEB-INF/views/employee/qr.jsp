<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<link rel="stylesheet" href="/css/employee/qr.css">

<main class="qr-page">

    <div class="qr-container">

        <h2 class="qr-title">직원 출근 QR 인증</h2>

        <p class="qr-desc">
            매장 태블릿 또는 직원 앱으로 아래 QR코드를 스캔하여<br>
            출근 인증을 진행하세요.
        </p>

        <div class="qr-box">

            <img src="/img/qr.png" alt="출근 QR코드">

        </div>

        <p class="qr-guide">
            QR코드는 일정 시간이 지나면 자동 갱신됩니다.
        </p>

    </div>

</main>