<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 당첨 확인</title>

<link rel="stylesheet" href="/css/common/header.css">
<link rel="stylesheet" href="/css/common/event.css">

<link rel="stylesheet"
href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />

</head>

<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main class="event-win-page">

    <div class="event-win-box">

        <span class="material-symbols-outlined event-icon">
        emoji_events
        </span>

        <h2 class="event-title">
        아직 당첨된 이벤트가 없습니다
        </h2>

        <p class="event-desc">
        이벤트에 참여하면 이곳에서 당첨 결과를 확인할 수 있습니다.
        </p>

        <button class="event-btn"
        onclick="location.href=`https://www.lguplus.com/pogg/event?p=1`">
        이벤트 보러가기
        </button>

    </div>

</main>

</body>
</html>