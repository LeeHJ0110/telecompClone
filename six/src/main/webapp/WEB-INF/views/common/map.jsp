<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>매장 실시간 검색하기</title>
    
    <style>
        /* 검색창 스타일링 */
        .search-container {
            margin-bottom: 10px;
        }
        #keyword {
            width: 300px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: 'Pretendard', sans-serif;
    }

    body {
        background-color: #ffffff;
        color: #222;
    }

</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <div class="search-container">
        <!-- 엔터키로도 검색되도록 onkeypress 추가 -->
        <input type="text" id="keyword" placeholder="장소를 입력하세요 (예: 강남역)" onkeypress="if(event.keyCode==13) {searchPlaces();}">
        <button onclick="searchPlaces()">검색하기</button>
    </div>

    <div id="map" style="width:100%;height:700px;"></div>

    <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=38b8e215e218eb2ac682f4e3b46421e7&libraries=services&"></script>
    <script>
        var map, ps, infowindow;
        var markers = []; // 마커를 관리할 배열

        kakao.maps.load(function() {
            var container = document.getElementById('map');
            var options = {
                center: new kakao.maps.LatLng(37.5006, 127.0363),
                level: 3
            };

            map = new kakao.maps.Map(container, options);
            ps = new kakao.maps.services.Places(); 
            infowindow = new kakao.maps.InfoWindow({zIndex:1});

            // 초기 실행 시 역삼역 SKT 검색 결과 보여주기
            searchPlaces();
        });

        // 1. 검색 함수
        function searchPlaces() {
            var keyword = document.getElementById('keyword').value;

            if (!keyword.replace(/^\s+|\s+$/g, '')) {
                // 아무것도 입력 안했을 때 기본값 설정
                keyword = "역삼역";
            }

            // 장소 검색 객체를 통해 키워드로 장소검색을 요청합니다
            ps.keywordSearch(keyword+'skt', placesSearchCB); 
        }

        // 2. 검색 완료 콜백 함수
        function placesSearchCB(data, status, pagination) {
            if (status === kakao.maps.services.Status.OK) {
                
                // 검색 성공 시 기존 마커 모두 제거
                removeMarkers();

                var bounds = new kakao.maps.LatLngBounds();

                for (var i=0; i<data.length; i++) {
                    displayMarker(data[i]);    
                    bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
                }       

                map.setBounds(bounds);
            } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
                alert('검색 결과가 존재하지 않습니다.');
            } else if (status === kakao.maps.services.Status.ERROR) {
                alert('검색 중 오류가 발생했습니다.');
            }
        }

        // 3. 마커를 생성하고 지도에 표시하는 함수
        function displayMarker(place) {
            var marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(place.y, place.x) 
            });

            // 생성된 마커를 배열에 추가 (나중에 지우기 위함)
            markers.push(marker);

            kakao.maps.event.addListener(marker, 'click', function() {
                infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
                infowindow.open(map, marker);
            });
        }

        // 4. 지도 위에 표시되고 있는 마커를 모두 제거하는 함수
        function removeMarkers() {
            for (var i = 0; i < markers.length; i++) {
                markers[i].setMap(null);
            }   
            markers = [];
        }
    </script>
</body>
</html>