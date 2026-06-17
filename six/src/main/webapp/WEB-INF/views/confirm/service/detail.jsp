<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>요금제 가입 신청</title>
    <link rel="stylesheet" href="/css/confirm/service/detail.css">
    <script defer src="/js/common/format.js"></script>
    <script defer src="/js/confirm/service/detail.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <main class="confirm-container">
        <section class="title-area">
            <h2>부가서비스 가입 신청</h2>
            <p>가입 신청 전, 아래의 정보를 최종 확인해주세요.</p>
        </section>

        <form >

            <div class="info-section">
                <h3>가입자 정보</h3>
                <hr>
                <div class="info-row">
                    <span class="label">아이디</span>
                    <span class="value" id="memberId">${sessionScope.loginMemberVo.id}</span>
                </div>
                <div class="info-row">
                    <span class="label">이름</span>
                    <span class="value" id="memberName">${sessionScope.loginMemberVo.name}</span>
                </div>
                <div class="info-row">
                    <div class="label-group">
                        <span class="label">전화번호</span>
                    </div>

                    <div class="value">
                        <span id="phone-display">요금제를 먼저 가입해주세요</span>
                    </div>
                </div>
                <div class="info-row">
                    <span class="label">주민등록번호</span>
                    <span class="value" id="resident-display">-</span>
                </div>
            </div>
            <div id="account-info">
                <div class="info-section">
                    <h3>부가 서비스</h3>
                    <hr>
                    <div class="info-row">
                        <span class="label">가입할 서비스</span>
                        <span class="value accent" id="service-name"></span>
                    </div>
                    <div class="info-row">
                        <span class="label">가격</span>
                        <span class="value" id="price"></span>
                    </div>
                </div>
                
                <div class="info-section">
                    <h3>결제 정보</h3>
                    <hr>
                    <div class="info-row">
                        <span class="label">결제수단</span>
                        <div class="value" id="payment-display-area">
                            <div class="info-display" style="display: flex; align-items: center;">
                                <strong style="color: #FF7E41; font-size: 16px;" id="payment-name">[${name}]</strong>
                                <span style="margin-left: 8px; font-weight: bold;" id="account-number">${num}</span>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <span class="label">결제일</span>
                        <span class="value"><Strong>매 달 12일</Strong></span>
                    </div>
                </div>
            </div>
                
            <div class="btn-group">
                <button type="button" class="btn-white" onclick="history.back();">취소</button>
                <button type="button" id="submitBtn" class="btn-orange-full" onclick="submitJoin()">신청완료 &rarr;</button>
            </div>
        </form>
    </main>
</body>
</html>