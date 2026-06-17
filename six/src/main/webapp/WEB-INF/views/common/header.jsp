
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/common/header.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script>
    const loginEmployee = "${loginEmployeeVo != null}";
    const loginAdmin = "${loginAdminVo != null}";
    </script>
<script defer src="/js/member/logout.js"></script>
<script defer src="/js/common/header.js"></script>
<header class="header">
    <div id="searchDropdown" class="search-dropdown">

    <div class="search-inner">

        <!-- input -->
        <div class="search-top">
            <input type="text" id="searchInput" placeholder="검색어를 입력하세요">
            <span class="material-symbols-outlined icon-black" onclick="doSearch()">search</span>
        </div>

        <!-- 콘텐츠 -->
        <div class="search-body">

            <div class="recent">
                <h4>최근 검색어</h4>
                <ul id="recentList"></ul>
            </div>

            <div class="popular">
                <h4>인기검색어</h4>
                <ul>
                    <li onclick="quickSearch('요금제')">요금제</li>
                    <li onclick="quickSearch('유튜브')">유튜브</li>
                    <li onclick="quickSearch('로밍')">로밍</li>
                    <li onclick="instar()">SWY</li>
                </ul>
            </div>

        </div>

    </div>

</div>

    <!-- 로고 -->
    <div class="logo">
        <a href="/home">
            <img src="/img/logo.png"
                 alt="LOGO"
                 height="70"
                 onerror="this.outerHTML='ERP LOGO'">
        </a>
    </div>

    <!-- 메뉴 -->
    <nav class="nav">

        <!-- 관리자 메뉴 -->
        <c:if test="${not empty sessionScope.loginEmployeeVo or not empty sessionScope.loginAdminVo}">
        <div class="nav-item mega-parent">

            <a href="/employee/adminhome">관리자페이지</a>

            <div class="mega-menu">
                <div class="mega-inner">

                    <div class="mega-column">
                        <h4>고객관리</h4>
                        <a href="/employee/membermanage/list/1">고객정보 조회</a>
                    </div>

                    <div class="mega-column">
                        <h4>직원관리</h4>
                        <a href="/employee/employeemanage/list/1">직원정보 조회</a>
                    </div>

                    <div class="mega-column">
                        <h4>상담관리</h4>
                        <a href="/employee/counsel/list/1">상담내역 조회</a>
                    </div>

                    <div class="mega-column">
                        <h4>신청관리</h4>
                        <a href="/employee/membermanage/confirm-list/1">신청내역 조회</a>
                    </div>

                </div>
            </div>

        </div>
        </c:if>

        <div class="nav-item">
            <a href="/plan/list/1">요금제</a>
        </div>

        <div class="nav-item">
            <a href="/add-service/list/1">부가서비스</a>
        </div>

        <!-- 고객지원 -->
        <div class="nav-item mega-parent">

            <a href="/board/notice/list/1">고객지원</a>

            <div class="mega-menu">
                <div class="mega-inner">

                    <div class="mega-column">
                        <h4>고객지원</h4>
                        <a href="/board/notice/list/1">공지사항</a>
                        <a href="/board/qa/list/1">
                            ${(loginEmployeeVo != null || loginAdminVo != null) ? '고객의 소리' : '문의하기'}
                        </a>
                    </div>

                </div>
            </div>

        </div>

        <div class="nav-item">
            <a href="/event/attendance">이벤트/혜택</a>
        </div>

    </nav>

    <!-- 우측 아이콘 -->
    <div class="user-actions">

        <a href="/map"
           class="material-symbols-outlined icon-btn">
           storefront
        </a>

        <span class="material-symbols-outlined icon-btn" onclick="openSearch()">search</span>

        <span class="material-symbols-outlined icon-btn" onclick="location.href='/cart'">shopping_cart</span>

        <!-- 유저 메뉴 -->
        <div class="user-menu-container">

            <div class="user-icon-group">
                <span class="material-symbols-outlined" style="font-size:30px;">person</span>
                <span class="material-symbols-outlined expand-more">expand_more</span>
            </div>

            <div class="dropdown-content">

                <!-- 회원 로그인 -->
                <c:if test="${not empty sessionScope.loginMemberVo}">

                    <div class="user-profile-header">
                        <div class="user-name-info">
                            <b>${sessionScope.loginMemberVo.name}님</b><br>
                            반갑습니다.
                        </div>

                        <form>
                            <button class="logout-btn" onclick="logout();">로그아웃</button>
                        </form>
                    </div>

                    <a href="/event" class="menu-icon-item">
                        <span class="material-symbols-outlined">emoji_events</span>
                        이벤트 당첨 확인하기</a>
                    <a href="https://www.samsung.com/sec/smartphones/galaxy-s26-ultra/buy/" class="menu-icon-item">
                        <span class="material-symbols-outlined">smartphone</span>
                        휴대폰 신상 보러가기</a>
                    <a href="/board/qa/list/1" class="menu-icon-item">
                        <span class="material-symbols-outlined">support_agent</span>
                        내 문의</a>

                    <div class="divider"></div>

                    <a href="/bill" class="menu-icon-item">
                        <span class="material-symbols-outlined">description</span>
                        청구요금 및 납부
                    </a>

                    <a href="/mypage" class="menu-icon-item">
                        <span class="material-symbols-outlined">edit_note</span>
                        MyPage
                    </a>

                    <a href="/planContract/mypage/history" class="menu-icon-item">
                        <span class="material-symbols-outlined">history_toggle_off</span>
                        사용 내역
                    </a>

                </c:if>

                <!-- 직원 로그인 -->
                <c:if test="${not empty sessionScope.loginEmployeeVo}">

                    <div class="user-profile-header">
                        <div class="user-name-info">
                            <b>${sessionScope.loginEmployeeVo.name} 직원님</b>
                        </div>

                        <form>
                            <button class="logout-btn" onclick="logout()";>로그아웃</button>
                        </form>
                    </div>

                    <a href="/qr" class="menu-icon-item">
                        <span class="material-symbols-outlined">qr_code</span>
                        출근QR
                    </a>

                </c:if>

                <!-- 관리자 로그인 -->
                <c:if test="${not empty sessionScope.loginAdminVo}">

                    <div class="user-profile-header">
                        <div class="user-name-info">
                            <b>${sessionScope.loginAdminVo.name} 관리자님</b>
                        </div>

                        <form>
                            <button class="logout-btn" onclick="logout();">로그아웃</button>
                        </form>
                    </div>

                    <a href="/employee/employeemanage/admin/store/list/1" class="menu-icon-item">
                        <span class="material-symbols-outlined">store</span>
                        대리점정보 조회
                    </a>

                    <a href="/employee/employeemanage/admin/insert" class="menu-icon-item">
                        <span class="material-symbols-outlined">person_add</span>
                        신규직원 가입
                    </a>

                </c:if>

                <!-- 비로그인 -->
                <c:if test="${empty sessionScope.loginMemberVo 
                            and empty sessionScope.loginEmployeeVo 
                            and empty sessionScope.loginAdminVo}">

                    <a href="/member/login"
                       class="menu-icon-item">
                       <span class="material-symbols-outlined">login</span>
                       로그인</a>

                </c:if>

            </div>

        </div>

    </div>

</header>