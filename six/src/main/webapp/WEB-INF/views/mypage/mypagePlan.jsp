<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
      <!DOCTYPE html>
      <html>

      <head>
        <meta charset="UTF-8">
        <title>마이페이지</title>
        <link rel="stylesheet" href="/css/mypage/mypagePlan.css">
        <script>
          const endday = "${sessionScope.fixedInfoVo.mainContractEndDate}";
        </script>
        <!-- <script defer src="/js/mypage/mypage.js"></script> -->
        <script defer src="/js/mypage/mypagePlan.js"></script>
        <script defer src="/js/common/format.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
      </head>

      <body>

        <%@ include file="/WEB-INF/views/common/header.jsp" %>

          <main>
            <div class="mypage-wrapper">
              <div class="mypage-tabs">
                <a href="/mypage" class="tab-btn">계정 정보</a>
                <a onclick="location.reload();" class="tab-btn active">내 요금제</a>
              </div>

              <h1 class="page-title" onclick="selectServiceCount();">내 요금제</h1>
              <h1 class="page-title" id="printPhoneNum">${sessionScope.fixedInfoVo.phone}</h1>
              <div class="my-section">
                <div class="plan-top-row">
                  <span class="plan-label">내 요금제 :</span>

                  <div class="plan-dropdown-wrap" onclick="togglePlanDropdown()">
                    <div class="current-plan" id="currentPlanName">
                      ${not empty sessionScope.fixedInfoVo ? sessionScope.fixedInfoVo.planName : '가입된 요금제 없음'}
                      <i class="fas fa-caret-down" style="color: #666;"></i>
                    </div>

                    <div class="dropdown-list" id="planList">
                      <c:choose>
                        <c:when test="${empty sessionScope.phoneList or fn:length(sessionScope.phoneList) == 0}">
                          <div class="drop-item">
                            <span class="drop-num">등록된 번호 없음</span>
                          </div>
                        </c:when>
                        <c:otherwise>
                          <c:forEach var="phone" items="${sessionScope.phoneList}">
                            <div class="drop-item ${phone == sessionScope.fixedInfoVo.phone ? 'active' : ''}"
                            onclick="selectPhoneNum('${phone}')" style="cursor: pointer;">
                              <span class="drop-num">${phone} </span>
                              <span class="drop-name">${sessionScope.fixedInfoVo.planName}</span>
                            </div>
                          </c:forEach>
                        </c:otherwise>
                      </c:choose>
                    </div>
                  </div>

                  <div class="plan-btn-group">
                    <button class="btn btn-change" onclick="editPlan(); ">변경</button>
                    <button class="btn btn-cancel" onclick="planCancle();">해지</button>
                  </div>
                </div>
              </div>

              <div class="my-section">
                <div class="sec-header" onclick="location.href='/add-service/list/1'">
                  <a href="http://127.0.0.1/serviceContract" class="sec-link-row">
                    <h2 class="sec-title">이용중인 부가서비스</h2>
                    <span class="sec-arrow"><i class="fas fa-arrow-right"></i></span>
                  </a>
                </div>
                <div class="info-row">
                  <span>유료 서비스</span>
                  <span id="paidServiceCount">0 건</span>
                   <%--
                   <span id="paidServiceCount">${not empty sessionScope.loginUsage.paidServiceCount ?
                    sessionScope.loginUsage.paidServiceCount : 0} 건</span>
                    --%>
                </div>
                <div class="info-row">
                  <span>무료 서비스</span>
                  <span id="freeServiceCount">0 건</span>
                  <%--
                  <span id="freeServiceCount">${not empty sessionScope.loginUsage.freeServiceCount ?
                    sessionScope.loginUsage.freeServiceCount : 0} 건</span>
                    --%>
                </div>
              </div>

              <div class="my-section">
                <div class="sec-header" onclick="movePageChangeAccount('${sessionScope.fixedInfoVo.phone}');">
                  <h2 class="sec-title">결제정보</h2>
                  <span class="sec-arrow"><i class="fas fa-arrow-right"></i>
                  </span>
                </div>
                <div class="info-row">
                  <span id="payMethodInfo">
                    <c:choose>
                      <c:when test="${not empty sessionScope.fixedInfoVo.paymentName}">
                        <span>${sessionScope.fixedInfoVo.paymentName}</span>
                        <span id="member_accountNumber_id">${sessionScope.fixedInfoVo.accountNumber}</span>
                      </c:when>
                      <c:otherwise>
                        등록된 결제수단 없음
                      </c:otherwise>
                    </c:choose>
                  </span>
                </div>
              </div>

              <div class="my-section">
                <h2 class="sec-title" style="margin-bottom: 25px;" onclick="location.href='/main-contract/list'">가입기간 및
                  약정</h2>
                <div class="contract-wrap">
                  <div class="contract-text" id="contractDates">
                    요금제 가입 : <span class="member_at_class">${not empty sessionScope.fixedInfoVo.createdAt ?
                      fn:substring(sessionScope.fixedInfoVo.createdAt, 0 , 10) : '정보없음'}</span> ~

                    <span class="member_at_class" id="endDate">${not empty sessionScope.fixedInfoVo.mainContractEndDate ?
                      fn:substring(sessionScope.fixedInfoVo.mainContractEndDate, 0, 10) : '정보 없음'}</span>
                  </div>
                  <button class="btn btn-outline" onclick="location.href='/bill'">납부/청구정보</button>
                </div>
              </div>

              <div class="my-section">
                <div class="sec-header" style="margin-bottom: 35px;"
                  onclick="location.href='/planContract/mypage/history'">
                  <h2 class="sec-title">데이터 현황</h2>
                  <span class="sec-arrow"><i class="fas fa-arrow-right"></i></span>
                </div>

                <div class="usage-item">
                  <div class="usage-info">
                    <div><strong>사용한 데이터</strong> <span class="light">(무제한)</span></div>
                    <span class="amount" id="planData">사용 : ${not empty sessionScope.loginUsage.planData ?
                      sessionScope.loginUsage.planData : 0} GB</span>
                  </div>
                  <div class="bar-bg">
                    <div class="bar-fill fill-green"></div>
                  </div>
                </div>

                <div class="usage-item">
                  <div class="usage-info">
                    <div><strong>사용한 문자</strong> <span class="light">(기본제공)</span></div>
                    <span class="amount" id="planSms">사용 : ${not empty sessionScope.loginUsage.planSms ?
                      sessionScope.loginUsage.planSms : 0} 회</span>
                  </div>
                  <div class="bar-bg">
                    <div class="bar-fill fill-yellow"></div>
                  </div>
                </div>

                <div class="usage-item">
                  <div class="usage-info">
                    <div><strong>사용한 음성(분)</strong> <span class="light">(기본제공)</span></div>
                    <span class="amount" id="planVoice">사용 : ${not empty sessionScope.loginUsage.planVoice ?
                      sessionScope.loginUsage.planVoice : 0} (분)</span>
                  </div>
                  <div class="bar-bg">
                    <div class="bar-fill fill-blue"></div>
                  </div>
                </div>
              </div>

              <hr style="border:0; border-top:1px solid #e0e0e0; margin:0;">

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
                    <a href="http://127.0.0.1/confirm/service">부가서비스 신청</a>
                    <a href="http://127.0.0.1/confirm/plan?selectTable=update">요금제 변경</a>
                    <a href="http://127.0.0.1/confirm/service?selectTable=delete">부가서비스 해지</a>
                    <a href="http://127.0.0.1/confirm/plan?selectTable=delete">요금제 해지</a>
                  </div>
                </div>
              </div>
            </div>
          </main>

      </body>

      </html>