<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="stylesheet" href="/css/mypage/myEdit.css">
    <script src="/js/mypage/myEdit.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<header></header>

<main class="mypage-bg">
    <div class="mypage-container">

        <!-- 2. 메인 타이틀 -->
        <h1 class="page-title">계정 정보 수정</h1>

        <!-- 3. 기본 계정 정보 섹션 -->
        <div class="info-section">

            <div class="info-list">
                <div class="info-row">
                    <input type="hidden" name="no" value="${sessionScope.loginMemberVo.no}">
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
                    <input type="text" name="email" value="${sessionScope.loginMemberVo.email}" class="editBox"><br>
                </div>
                <!-- <div class="info-row">
                <span class="label">주소 :</span>
                <input type="text" name="address" value="${sessionScope.loginMemberVo.address}" class="editBox"><br>
              </div> -->

                <div class="info-row align-top">
                    <span class="label">주소 :</span>
                    <div class="address-wrap">

                        <!-- 우편번호와 버튼을 나란히 배치 -->
                        <div class="postcode-flex">
                            <input type="text" id="postcode" class="form-input readonly-input" placeholder="우편번호"
                                   readonly>
                            <button type="button" class="btn-search" onclick="execDaumPostcode()">주소 찾기</button>
                        </div>

                        <!-- 기본 주소 -->
                        <input type="text" name="address" id="address" class="form-input readonly-input"
                               placeholder="주소"
                               readonly>

                        <!-- 상세 주소 -->
                        <input type="text" name="address2" id="detailAddress" class="form-input" placeholder="상세주소 입력">
                    </div>
                </div>
                <!-- ======================================= -->

                <div class="info-row">
                    <span class="label">전화번호 : </span>
                    <span class="value">${not empty sessionScope.loginMemberVo ?
                            sessionScope.loginMemberVo.phone : '번호없음'}</span>
                </div>
                <div class="info-row">
                    <span class="label">주민번호 :</span>
                    <span class="value">
                    <c:choose>
                        <c:when test="${not empty sessionScope.loginMemberVo}">
                            ${fn:substring(sessionScope.loginMemberVo.resident, 0, 6)}-${fn:substring(sessionScope.loginMemberVo.resident, 6, 7)}******
                        </c:when>
                        <c:otherwise>
                            123456-1******
                        </c:otherwise>
                    </c:choose>
                  </span>
                </div>
            </div>
        </div>

        <!-- 4. 가입일 섹션 -->
        <div class="info-section">
            <h2 class="sub-section-title">가입일</h2>
            <div class="sub-section-content">
                <p>${not empty sessionScope.loginMemberVo ? sessionScope.loginMemberVo.createdAt :
                        '2017.05.02'}</p>
            </div>
        </div>

        <!-- 5. 가입대리점 섹션 -->
        <div class="info-section">
            <h2 class="sub-section-title">가입대리점</h2>
            <div class="sub-section-content">
                <!-- DB에서 가져오는 값으로 매핑 필요 -->
                <p>케이에이치컴퍼니 주식회사</p>
                <p>02-397-9880</p>
            </div>
        </div>

        <!-- 6. 상태 섹션 -->
        <div class="info-section last-border">
            <h2 class="sub-section-title">상태</h2>
            <div class="sub-section-content">
                <!-- DB 연동 필요 -->
                <p>8년 11개월 / 이용중</p>
            </div>
        </div>

        <!-- 7. 하단 바로가기 카드 영역 -->
        <div class="bottom-cards">
            <button onclick="save();" id="saveBtn">저장하기</button>
            <button onclick="quit();" id="escapeBtn">탈퇴하기</button>
        </div>

    </div>
</main>
</body>

</html>