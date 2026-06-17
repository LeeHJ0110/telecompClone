<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/css/common/cart.css">
<script defer src="/js/common/cart.js"></script>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
  <script>
    const LOGIN_MEMBER = ${loginMemberVo != null};
  </script>

<main class="cart-container">

    <h1 class="cart-title">장바구니</h1>

    <!-- 로그인 안내 -->
    <c:if test="${empty sessionScope.loginMemberVo}">
        <div class="cart-login-box">
            로그인하시면 상품을 확인할 수 있습니다.
            <a href="/member/login">로그인 ></a>
        </div>
    </c:if>

    <!-- 탭 -->
    <div class="cart-tab">
        <span class="active">상품 리스트</span>

    </div>

    <!-- 핵심 영역 -->
    <div class="cart-list">

        <!-- JS 렌더링 -->
        <div class="plan-section"></div>
        <div class="add-section"></div>

        <!-- empty -->
        <div class="cart-empty" style="display:none;">
            <span class="material-symbols-outlined">error</span>
            <p>선택된 상품이 없습니다.</p>
        </div>

    </div>

    <div class="cart-info">
        <p>· 선택한 상품은 임시 저장됩니다.</p>
        <p>· 브라우저 종료 시 사라질 수 있습니다.</p>
    </div>

</main>