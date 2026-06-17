<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8">
    <title>세미</title>
    <script defer src="/js/member/memberJoin.js"></script>
    <link rel="stylesheet" href="/css/member/memberJoin.css">
  </head>

  <body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

      <header>
        <img src="/src/main/resources/static/img/logo.png" alt="">
      </header>

      <main>
        <h1>회원가입</h1>
        <div id="login-area">
          <form>
            <div id="idPlace">
              <input type="text" name="id" placeholder="아이디입력">
              <input type="button" onclick="Duplicate_Check();" name="duplicate" value="중복확인">
            </div>
            <span id="idCheck"></span>
            <br>
            <div class="join-input-box">
              <input type="password" id="pw" name="pw" placeholder="비밀번호를 입력하세요." required>
            </div>

            <div class="join-input-box">
              <input type="password" id="pw2" name="pw2" placeholder="비밀번호를 다시 입력하세요." required>
              <span id="pwCheckMsg" class="validation-msg"></span>
            </div>
            <br>
            <input type="text" name="name" placeholder="이름">
            <br>

            <!-- ====== [1. 주소 찾기 영역 수정] ====== -->
            <div style="display: flex; gap: 10px; width: 100%;">
              <input type="text" id="postcode" placeholder="우편번호" readonly style="flex: 1; background-color: #f5f5f5;">
              <input type="button" onclick="execDaumPostcode()" value="주소 찾기"
                style="width: 100px; margin-top: 0; padding: 14px; background-color: #ff6a00; font-size: 14px;">
            </div>
            <!-- 찾은 주소가 들어갈 기본 주소 (읽기 전용) -->
            <input type="text" name="address" id="address" placeholder="주소" readonly style="background-color: #f5f5f5;">
            <!-- 사용자가 직접 칠 상세 주소 -->
            <input type="text" name="address2" id="detailAddress" placeholder="상세주소">
            <!-- ======================================= -->
            <div id="email_sec">
              <input type="text" name="email" placeholder="이메일" maxlength="20">

              <span id="goal">@</span>

              <input type="text" id="domain_txt">
              
              <select id="domain_list">
                <option value="none">직접 입력</option>
                <option value="gmail.com">gmail.com</option>
                <option value="naver.com">naver.com</option>
                <option value="daum.com">daum.com</option>
                <option value="han.com">hanmail.com</option>
                <option value="kakao.com">kakao.com</option>
                <option value="nate.com">nate.com</option>
                <option value="msn.com">msn.com</option>
              </select>
            </div>
            <input type="text" name="resident" placeholder="주민등록 번호">
            <br>
            <input type="button" value="회원가입" onclick="join();" id="joinBtn">
          </form>
        </div>
      </main>

      <!-- ====== [2. 카카오 주소 API 스크립트 추가] ====== -->
      <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

  </body>

  </html>