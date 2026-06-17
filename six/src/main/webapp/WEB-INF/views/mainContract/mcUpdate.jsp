
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>약정정보</title>
    <link rel="stylesheet" href="/css/mainContract/mcList.css">
    <script defer src="/js/mainContract/mcUpdate.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="contract-page">     
        <div class="page-header">
            <h1>약정 정책 수정하기</h1>
        </div>
        <div class="contract-list">
            <div class="contract-card">
                <div class="info-section">
                    <span class="badge">사용중</span>
                    <div class="category">선택약정 할인 ></div>
                    <div class="title">
                        <input type="number" name="term" placeholder="약정단위(개월)">
                    </div>
                    <div class="subtitle">전체 5G 요금제 대상</div>
                    <div class="price-row">
                        <span class="main-benefit">
                            <input type="number" name="discountRate" placeholder="할인율">
                        </span>
                        <span class="sub-text">결합 할인 중복 가능</span>
                    </div>
                </div>
                <div class="action-section">
                    <div class="details">
                        <div class="detail-row"><span class="detail-label">약정기간</span> <b>기본12월단위</b></div>
                        <div class="detail-row"><span class="detail-label">위약금</span> 
                        <input type="number" name="penalty" placeholder="위약금퍼센테이지"></div>
                    </div>
                    <div class="btn-group">
                        <div class="btn btn-edit" onclick="update();">수정하기</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>