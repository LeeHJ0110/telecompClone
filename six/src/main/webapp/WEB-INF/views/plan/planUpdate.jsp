
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신규요금제 등록</title>
    <link rel="stylesheet" href="/css/plan/plan.css">
    <script defer src="/js/plan/planUpdate.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
	
<!-- ===== 요금제 등록카드 V2 ===== -->
 <form>
  <div class="plan-card-v2">
    <div class="plan-v2-left">
      
      <div class="plan-v2-top">
        <span class="plan-v2-badge">
          <select name="category" id="">
            <option value="none">=== 선택 ===</option>
            <option value="popularity">인기</option>
            <option value="new">최신</option>
          </select>
        </span>
        <input lass="plan-v2-subtitle" type="text" name="name" id="" placeholder="요금제명 입력 >">
      </div>

      <h2 class="plan-v2-title">
        <input type="text" name="dataTotal" id="" placeholder="데이터 용량">
        <span class="plan-v2-speed">
          <select name="dataNo">
            <optgroup label="5G">
              <option value="1">400Kbps</option>
              <option value="2">1Mbps</option>
              <option value="3">3Mbps</option>
              <option value="4">5Mbps</option>
              <option value="5">무제한</option>
            </optgroup>

            <optgroup label="LTE">
              <option value="6">400Kbps</option>
              <option value="7">1Mbps</option>
              <option value="8">3Mbps</option>
              <option value="9">5Mbps</option>
              <option value="10">무제한</option>
            </optgroup>
          </select>
        </span>
      </h2>

      <div class="plan-v2-share">
        <input type="text" name="dataShare" id="" placeholder="테더링+쉐어링">
        <input type="text" name="description" placeholder="요금제 설명">
      </div>

      <div class="plan-v2-price-area">
        <span class="plan-v2-price"><input type="number" name="price" id="" placeholder="월 요금"></span>
        <span class="plan-v2-discount"></span>
        <button class="plan-v2-detail-btn"></button>
      </div>

    </div>

    <div class="plan-v2-divider"></div>

    <div class="plan-v2-right">

      <div class="plan-v2-benefit">
        <span class="plan-v2-label">음성통화</span>
        <span class="plan-v2-value"><input type="text" name="voiceTotal" id="" placeholder="부가통화시간(분),무제한일경우 '무제한'"></span>
      </div>

      <div class="plan-v2-benefit">
        <span class="plan-v2-label">문자메시지</span>
        <span class="plan-v2-value"><input type="text" name="smsTotal" id="" placeholder="문자량,무제한일경우 '무제한'"></span></span>
      </div>

      <div class="plan-v2-button-area">
        <button type="button" class="plan-v2-change-btn" onclick="update()" >요금제 수정</button>
      </div>

    </div>
  </div>
</form>
</body>
</html>