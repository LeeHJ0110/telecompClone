<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="stylesheet" href="/css/mypage/mypage.css">
    <script defer src="/js/mypage/mypage.js"></script>
    <script defer src="/js/common/format.js"></script>

</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<header></header>

<main class="mypage-bg">
    <div class="mypage-container">

        <!-- 1. 상단 탭 메뉴 (이미지 반영) -->
        <div class="top-tabs">
            <a href="javascript:void(0);" onclick="location.reload();" class="active">계정 정보</a>

            <c:choose>

                <c:when test="${not empty fixedInfoVo}">
                    <a href="/mypage/mypagePlan" id="fixdvo_yn" onclick="loadMyPlans(1);">내 요금제</a>
                </c:when>

                <c:otherwise>
                    <a href="/plan/list/1">요금제 신청</a>
                </c:otherwise>

            </c:choose>
        </div>

        <!-- 2. 메인 타이틀 -->
        <h1 class="page-title">계정 정보</h1>

        <!-- 3. 기본 계정 정보 섹션 -->
        <div class="info-section">
            <button type="button" class="btn-edit" onclick="location.href='/mypage/myedit'">수정하기</button>

            <div class="info-list">
                <div class="info-row">
                    <span class="label">이름 :</span>
                    <span class="value">${not empty sessionScope.loginMemberVo ?
                            sessionScope.loginMemberVo.name : '홍길동'}</span>
                </div>
                <div class="info-row">
                    <span class="label">아이디 :</span>
                    <span class="value">${not empty sessionScope.loginMemberVo ?
                            sessionScope.loginMemberVo.id : 'user0304'}</span>
                </div>
                <div class="info-row">
                    <span class="label">이메일 :</span>
                    <span class="value">${not empty sessionScope.loginMemberVo.email ?
                            sessionScope.loginMemberVo.email : 'kh0623@gmail.com'}</span>
                </div>
                <div class="info-row">
                    <span class="label">주소 :</span>
                    <span class="value">
                  ${not empty sessionScope.loginMemberVo ? sessionScope.loginMemberVo.address : '서울
                          서초구 효령로68길 81'}
                  ${not empty sessionScope.loginMemberVo.address2 ?
                          sessionScope.loginMemberVo.address2 : ''}
                </span>
                </div>
                <div class="info-row">
                    <span class="label">전화번호 :</span>
                    <span class="value" id="member_phone_id"
                          onclick="member_phone()">${not empty sessionScope.fixedInfoVo
                            ?
                            sessionScope.fixedInfoVo.phone : '010-1234-5678'}</span>
                </div>
                <div class="info-row">
                    <span class="label">주민번호 :</span>
                    <span class="value" id="member_resident_id">${not empty sessionScope.loginMemberVo ?
                            sessionScope.loginMemberVo.resident : '123456-1******'}</span>
                </div>
            </div>
        </div>

        <!-- 4. 가입일 섹션 -->
        <div class="info-section">
            <h2 class="sub-section-title">가입일</h2>
            <div class="sub-section-content">
                <p>
                    가입일 : <span>${fn:substring(loginMemberVo.createdAt, 0, 10)}</span>
                </p>
            </div>
        </div>

        <!-- 5. 가입대리점 섹션 -->
        <div class="info-section">
            <h2 class="sub-section-title">가입대리점</h2>
            <div class="sub-section-content">
                <p>케이에이치컴퍼니 주식회사</p>
                <p>02-397-9880</p>
            </div>
        </div>

        <!-- 6. 상태 섹션 -->
        <div class="info-section last-border">
            <h2 class="sub-section-title">상태</h2>
            <div class="sub-section-content">
                <p id="member_status_id" data-date="${fn:substring(loginMemberVo.createdAt, 0, 10)}"></p>
            </div>
        </div>

        <!-- 7. 하단 바로가기 카드 영역 -->
        <div class="bottom-cards">
            <!-- 첫번째 카드: 바로가기 -->
            <div class="card">
                <h3>바로가기</h3>
                <div class="card-grid">
                    <a href="/board/qa/list/1">나의 문의내역</a>
                    <a href="/board/notice/list/1">공지사항</a>
                    <a href="/map">찾아오시는 길</a>
                    <a href="/event/attendance">이벤트</a>
                    <a href="/bill">나의 청구내역</a>
                </div>
            </div>

            <!-- 두번째 카드: 나의 신청내역 -->
            <div class="card">
                <h3>나의 신청내역</h3>
                <div class="card-grid">
                    <a href="http://127.0.0.1/confirm/plan?selectTable=insert">요금제 신청</a>

                    <span id="fixedInfoVoCheck" hidden>${sessionScope.fixedInfoVo.no}</span>
                    <a href="http://127.0.0.1/confirm/service" onclick="return checkPlan()">부가서비스 신청</a>
                    <a href="http://127.0.0.1/confirm/plan?selectTable=update" onclick="return checkPlan()">요금제 변경</a>
                    <a href="http://127.0.0.1/confirm/service?selectTable=delete" onclick="return checkPlan()">부가서비스
                        해지</a>
                    <a href="http://127.0.0.1/confirm/plan?selectTable=delete" onclick="return checkPlan()">요금제 해지</a>
                </div>
            </div>
        </div>

    </div>
</main>
</body>

</html>