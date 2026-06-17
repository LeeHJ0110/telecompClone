<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>요금제 가입 신청</title>
    <link rel="stylesheet" href="/css/common/header.css">
    <link rel="stylesheet" href="/css/planConfirm/join.css">
    <script defer src="/js/common/header.js"></script>
    <script defer src="/js/planConfirm/join.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <main class="confirm-container">
        <section class="title-area">
            <h2>요금제 가입 신청</h2>
            <p>가입 신청 전, 아래의 정보를 최종 확인해주세요.</p>
        </section>

        <form >

            <div class="info-section">
                <h3>가입자 정보</h3>
                <hr>
                <div class="info-row">
                    <span class="label">아이디</span>
                    <span class="value" id="memberId">${loginMemberVo.id}</span>
                </div>
                <div class="info-row">
                    <span class="label">이름</span>
                    <span class="value" id="memberName">${loginMemberVo.name}</span>
                </div>
                <div class="info-row">
                    <div class="label-group">
                        <span class="label">전화번호</span>
                        <div class="phone-input-box">
                            <input type="text" id="user-last-digit" 
                                placeholder="뒷4자리" 
                                maxlength="4" 
                                oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                            <button type="button" class="btn-reg-mini" onclick="getNewPhoneNumber()">번호 생성</button>
                        </div>
                    </div>

                    <div class="value">
                        <span id="phone-display" class="accent">번호를 생성해주세요</span>
                        <input type="hidden" id="phone-raw">
                    </div>
                </div>
                <div class="info-row">
                    <span class="label">주민등록번호</span>
                    <span class="value" id="resident-display"></span>
                    <input type="hidden" id="resident-raw" value="${loginMemberVo.resident}">
                </div>
            </div>

            <div class="info-section">
                <h3>요금제 및 약정</h3>
                <hr>
                <div class="info-row">
                    <span class="label">가입할 요금제</span>
                    <span class="value accent" id="plan-name">${planVo.name}</span>
                    <input type="hidden" id="hidden-plan-no" value="${planVo.no}">
                </div>
                <div class="info-row">
                    <span class="label">가입날짜</span>
                    <span class="value" id="createdAt">${fixedInfoVo.createdAt}</span>
                </div>
                <div class="info-row">
                    <span class="label">약정기간</span>
                    <!-- 약정종료 날짜 어떻게 가져오는지 확인 필요 -->
                    <span class="value" id="term-display" style="font-weight: bold; color: #333;">
                        ${mainContract.term}개월 약정
                    </span>

                    <input type="hidden" id="hidden-contract-no" value="${mainContract.no}">
                    <input type="hidden" id="hidden-term" value="${mainContract.term}">
                </div>
            </div>

            <div class="info-section">
                <h3>결제 정보</h3>
                <hr>
                <div class="info-row">
                    <span class="label">결제수단</span>
                    <div class="value" id="payment-display-area">
                        <button type="button" class="btn-reg-mini" onclick="openPaymentModal()">결제 수단 등록</button>
                    </div>
                </div>
                <div class="info-row">
                    <span class="label">결제일</span>
                    <span class="value"><Strong>매 달 12일</Strong></span>
                </div>
                <input type="hidden" id="hidden-card-no">
                <input type="hidden" id="hidden-card-number">
            </div>

            <div class="btn-group">
                <button type="button" class="btn-white" onclick="history.back();">취소</button>
                <button type="button" class="btn-orange-full" onclick="submitJoin()">신청완료 &rarr;</button>
            </div>
        </form>
    </main>

    <div class="modal-overlay" id="paymentModal">
        <div class="modal-content">
            <h3 class="modal-title">결제 정보 등록</h3>

            <div class="form-item">
                <label class="modal-label">결제 수단 선택</label>
                <select id="modal-pay-type" class="input-main">
                    <option value="">수단을 선택하세요</option>
                    <%-- ViewController에서 보낸 paymentList를 순회 --%>
                    <c:forEach var="pay" items="${paymentList}">
                        <option value="${pay.no}">${pay.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-item">
                <label class="modal-label">카드(계좌) 번호 (숫자만 입력)</label>
                <input type="text" id="modal-pay-num" class="input-main" placeholder="하이픈(-) 없이 입력해 주세요" maxlength="20">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn-modal-close" onclick="closePaymentModal()">취소</button>
                <button type="button" class="btn-modal-save" onclick="savePaymentInfo()">정보 반영</button>
            </div>
        </div>
    </div>
</body>
</html>