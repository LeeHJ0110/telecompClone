<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>요금제 변경 신청 | 치무Six 텔레콤</title>
    <link rel="stylesheet" href="/css/planConfirm/update.css">
    <script defer src="/js/planConfirm/update.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <div id="vo-data-store" style="display: none;">
        <input type="hidden" id="hiddenCreatedAt" value="${fixedInfoVo.createdAt}">
        <input type="hidden" id="hiddenTerm" value="${fixedInfoVo.term}">
        <input type="hidden" id="hiddenPhone" value="${fixedInfoVo.phone}">
        <input type="hidden" id="hiddenCurrentPlanNo" value="${fixedInfoVo.planNo}">
    </div>

    <main class="confirm-container">
        <section class="title-area">
            <h2>요금제 변경신청</h2>
            <p>현재 이용 중인 요금제와 변경할 요금제를 확인하세요.</p>
        </section>

        <div class="plan-card current">
            <div class="badge-usage">사용 중</div>
            <div class="phone-number">${fixedInfoVo.phone}</div>
            
            <div class="plan-header">
                <h3>${fixedInfoVo.planName}</h3>
            </div>

            <div class="plan-details">
                <div class="detail-item">
                    <span class="detail-label">데이터</span>
                    <span class="detail-value current-data">-</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">음성통화</span>
                    <span class="detail-value current-voice">-</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">월 이용료</span>
                    <span class="detail-value price current-price">-</span>
                </div>
            </div>

            <div class="plan-info-footer">
                개통일 : ${fixedInfoVo.createdAt}<br>
                약정 종료일 : <span class="expiry-date-display">계산 중...</span>
            </div>
        </div>

        <div class="arrow-indicator">
            <span class="material-symbols-outlined" style="font-size: 40px;">arrow_downward</span>
        </div>

        <div class="plan-card target">
            <div class="plan-header">
                <h3>${planVo.name} <span class="badge-change" onclick="location.href='/plan/mypage/editList'">요금제 바꾸기</span></h3>
            </div>
            
            <div class="plan-details">
                <div class="detail-item">
                    <span class="detail-label">데이터</span>
                    <span class="detail-value plan-data-value"></span> 
                </div>
                <div class="detail-item">
                    <span class="detail-label">음성통화</span>
                    <span class="detail-value plan-voice-value"></span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">월 이용료</span>
                    <span class="detail-value price plan-price-value"></span>
                </div>
                <div class="action-area">
                    <button type="button" class="btn-select-orange" id="confirmChangeBtn">선택</button>
                </div>
            </div>
        </div>

        <div class="change-notice">
            <p>※ 요금제 변경 시 이번 달 요금은 일할 계산되어 청구됩니다.</p>
            <p>※ 결합 할인 등 혜택 유지 여부를 반드시 확인하시기 바랍니다.</p>
        </div>
    </main>

</body>
</html>