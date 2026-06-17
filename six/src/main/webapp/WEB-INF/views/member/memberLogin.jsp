<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8">
    <title>헬로월드</title>
    <link rel="stylesheet" href="/css/member/memberLogin.css">
    <script defer src="/js/member/memberLogin.js"></script>

  </head>

  <body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
      <header></header>
      <main>
        <h1>로그인</h1>
        <div id="login-area">
          <form>
            <input type="text" name="id" placeholder="아이디입력">
            <input type="password" name="pw" placeholder="비밀번호">

            <input type="button" value="로그인" onclick="login();" id="loginButton">
            <!-- 📍 회원가입 버튼에 기존 CSS에 있던 join-btn 클래스 추가 -->
            <input type="button" value="회원가입" onclick="location.href=`/member/join`" id="joinButton" class="join-btn">

            <!-- 📍 ID/PW 찾기 영역 구조화 및 구분선 추가 -->
            <div class="link-area">
              <a href="">ID찾기</a>
              <span class="divider">|</span>
              <a href="">PW찾기</a>
            </div>
          </form>

          <!-- 📍 직원 로그인 링크 영역 분리 및 클래스 추가 -->
          <div class="employee-link-area">
            <a href="/employee/login" class="employee-link">직원이십니까? &gt;</a>
          </div>
        </div>

      </main>
  </body>

  </html>