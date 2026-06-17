<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>결제 정보 관리</title>
    <link rel="stylesheet" href="/css/planConfirm/changeAccount.css">
    <script defer src="/js/planConfirm/changeAccount.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="account-container">
        <div class="header-section">
            <h1 class="main-title">결제 정보</h1>
            <p class="sub-title">결제 정보를 관리하세요.</p>
        </div>

        <div class="phone-tabs"></div>

        <div class="payment-card">
            <h2 class="card-name">삼성카드 (신용)</h2>
            <p class="card-number">123514 - 156158 - ****</p>
        </div>

        <button class="change-btn"  onclick="openPaymentModal()">+ 결제수단 변경하기</button>

        <ul class="info-list">
            <li>기본 매달 12일에 자동으로 요금이 청구됩니다.</li>
            <li>결제 수단 변경 시 다음 달 청구분부터 적용될 수 있습니다.</li>
            <li>회선당 본인명의 결제수단 1개만 등록이 가능합니다.</li>
        </ul>
    </div>
    <div class="modal-overlay" id="paymentModal">
        <div class="modal-content">
            <h3 class="modal-title">결제 정보 등록</h3>

            <div class="form-item">
                <label class="modal-label">결제 수단 선택</label>
                <select id="modal-pay-type" class="input-main">
                    <option value="">수단을 선택하세요</option>
                    <c:forEach var="pay" items="${paymentList}">
                        <option value="${pay.no}">${pay.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-item">
                <label class="modal-label">카드(계좌) 번호 (숫자만 입력)</label>
                <input type="text" id="modal-pay-num" class="input-main" placeholder="하이픈(-) 없이 입력해 주세요" maxlength="16">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-modal-close" onclick="closePaymentModal()">취소</button>
                <button type="button" class="btn-modal-save" onclick="savePaymentInfo()">정보 반영</button>
            </div>
        </div>
    </div>
</body>
</html>