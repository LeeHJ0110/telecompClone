<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>청구 페이지</title>
<link rel="stylesheet" href="/css/bill/bill.css">
<script defer src="/js/common/format.js"></script>
<script defer src="/js/bill/bill.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container">

    <div class="top">
        <div class="date-box">

            <div class="calendar-wrapper">
                <button id="calendarBtn" onclick="toggleCalendar(event);">📅</button>
                <span id="billDate">2025.12.31 ~ 2026.01.31</span>

                <div id="calendarPopup" class="calendar-popup">

                    <div id="yearPopup"></div>
                    <div id="yearBtn" onclick="toggleYearPopup(event)">2026</div>

                    <div id="months">
                        <button onclick="selectMonth(this)">1</button>
                        <button onclick="selectMonth(this)">2</button>
                        <button onclick="selectMonth(this)">3</button>
                        <button onclick="selectMonth(this)">4</button>

                        <button onclick="selectMonth(this)">5</button>
                        <button onclick="selectMonth(this)">6</button>
                        <button onclick="selectMonth(this)">7</button>
                        <button onclick="selectMonth(this)">8</button>

                        <button onclick="selectMonth(this)">9</button>
                        <button onclick="selectMonth(this)">10</button>
                        <button onclick="selectMonth(this)">11</button>
                        <button onclick="selectMonth(this)">12</button>
                    </div>

                </div>
            </div>

        </div>

        <div id="phone">
            <select class="phone-list">
                
            </select>
        </div>
    </div>


    <div class="price">
        <h2 id="monthBillPrice">청구서가 없습니다</h2>
        <div id="fullPriceValue">0 원</div>
    </div>



    <div class="payment-box">

        <div class="payment-header">
            <span>납부 금액</span>
            <span class="red" id="billTotal">0 원</span>
        </div>

        <hr>

        <div class="payment-info">
            <div>
                <label>납부 방법</label>
                <h3 id="paymentName"></h3>
            </div>
            <div>
                <label>결제 정보</label>
                <h3 id="accountNumber"></h3>
            </div>
        </div>

        <button onclick="changebtn()" class="change-btn">변경</button>

    </div>



    <div class="payment-etc">

        <div>
            <p>추가 청구 금액</p>
            <p class="red" id="etcBill">0 원</p>
        </div>

        <div>
            <p>수납 상태</p>
            <p class="red" id="payYn">미납</p>
        </div>

    </div>



    <div class="notice">

        <b>꼭 확인하세요.</b>

        <ul>
            <li>명세서 선택시 기본 12개월 내역을 확인할 수 있습니다.</li>
            <li>사업자번호 등록된 멤버십 고객님은 부가세, 매입세금 공제가 가능합니다.</li>
            <li>결제 정보 변경시 멤버십 혜택은 변경되지 않습니다.</li>
            <li>납부 내역은 마이페이지에서 확인 가능합니다.</li>
        </ul>

    </div>


</div>

</body>
</html>