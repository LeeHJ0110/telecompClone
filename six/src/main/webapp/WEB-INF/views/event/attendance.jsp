<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>출석 체크 이벤트</title>
    <link rel="stylesheet" href="/css/event/attendance.css">
    <script>
        const loginMember = ${loginMemberVo != null};
        const loginAdmin = ${loginAdminVo != null};
        const loginEmployee = ${loginEmployeeVo != null};
    </script>
    <script defer src="/js/event//attendance.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <main>
        <div class="container">

            <!-- 출석 체크 영역 -->
            <section class="attendance-box">
                <div class="img-area">
                    <img src="/img/attendance.png" alt="">
                </div>

                <div class="attendance-status">
                    <p>이번 달 출석일수</p>
                    <strong id="attendanceCount">출석하기를 눌러주세요</strong>
                </div>

                <button id="checkBtn" class="check-btn" onclick="attendance();">출석하기</button>
            </section>


            <!-- 유의사항 영역 -->
            <section class="notice-box">
                <h3>꼭 확인하세요!</h3>

                <ul>
                    <li>해당 이벤트는 매일 1회 참여 가능합니다.</li>
                    <li>1일 1회 참여 가능 이벤트이며, 별도의 응모 없이 출석 시 자동 응모됩니다.</li>
                    <li>출석 조건 달성 시 이벤트 종료 후 순차적으로 경품이 지급됩니다.</li>
                    <li>이벤트 참여 기록이 비정상적인 경우 참여가 제한될 수 있습니다.</li>
                    <li>경품은 사정에 따라 동일 금액의 다른 상품으로 변경될 수 있습니다.</li>
                </ul>
            </section>

        </div>
    </main>

</body>
</html>