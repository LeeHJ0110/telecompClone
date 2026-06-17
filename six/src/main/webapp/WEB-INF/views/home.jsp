<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈</title>

<link rel="stylesheet" href="/css/common/header.css">
<link rel="stylesheet" href="/css/home.css">
<script defer src="/js/home.js"></script>

</head>

<body class="home-page">

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<!-- HERO 영역 -->
<section class="home-hero">

    <div class="hero-slider">

        <div class="hero-track">

            <!-- slide 1 -->
            <div class="hero-slide">

                <div class="hero-inner">

                    <div class="hero-left">
                        <h1 class="hero-title">Galaxy S26 Series</h1>

                        <p class="hero-desc">
                            S26도 텔레콤프에서 심플하게<br>
                            S26 단독 프로모션 확인
                        </p>

                        <button class="hero-btn" onclick="location.href=`https://www.samsung.com/sec/smartphones/galaxy-s26-ultra/buy/`">
                            자세히보기</button>
                    </div>

                    <div class="hero-right">
                        <img src="/img/s26banner.png">
                    </div>

                </div>

            </div>

            <!-- slide 2 -->
            <div class="hero-slide">

                <div class="hero-inner">

                    <div class="hero-left">
                        <h1 class="hero-title">iPhone 17 Series</h1>

                        <p class="hero-desc">
                            iPhone 17e<br>
                            실속 한가득 가심비 충전
                        </p>

                        <button class="hero-btn" onclick="location.href=`https://www.apple.com/kr/iphone-17e/`">
                            자세히 보기</button>
                    </div>

                    <div class="hero-right">
                        <img src="/img/17ebanner.png">
                    </div>

                </div>

            </div>

            <!-- slide 3 -->
            <div class="hero-slide">

                <div class="hero-inner">

                    <div class="hero-left">
                        <h1 class="hero-title">Heroes of the Storm</h1>

                        <p class="hero-desc">
                            ♚히어로즈 오브 더 스☆톰♚ 가입시$$전원 카드팩☜☜뒷면100%증정※ <br>
                            ♜월드오브 워크래프트♜펫 무료증정￥
                            특정조건 §§디아블로3§§★공허의유산★초상화획득기회@@@
                        </p>

                        <button class="hero-btn" onclick="location.href=`https://heroesofthestorm.blizzard.com/ko-kr/`">
                            이벤트 보기</button>
                    </div>

                    <div class="hero-right">
                        <img src="/img/hOs.png">
                    </div>

                </div>

            </div>

        </div>

        <!-- 화살표 -->
        <div class="hero-prev">‹</div>
        <div class="hero-next">›</div>

        <!-- dots -->
        <div class="hero-dots"></div>

    </div>

</section>

<script src="/js/home/hero-slider.js"></script>

</body>
</html>