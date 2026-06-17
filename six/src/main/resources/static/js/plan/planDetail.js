async function loadPlanDetail(){
    const tag = document.querySelector(".plan-detail-page");
    const no = location.pathname.split("/").pop();
    const resp = await fetch(`/api/plan/detail/${no}`);
    if(!resp.ok){
        throw new Error("Detail load err.. resp not ok");
    }
    const data = await resp.json();
    const vo = data.vo;
    let str = "";
        str += `
            <div class="plan-detail-top">

                <!-- 요금제 정보 -->
                <div class="plan-info-area">

                    ${vo.category === 'popularity' ? `<div class="plan-badge">인기</div>` : ""}
                    ${vo.category === 'new' ? `<div class="plan-badge">최신</div>` : ""}

                    <h1 class="plan-name">
                        ${vo.name}
                    </h1>

                    <p class="plan-desc">
                        ${vo.description}
                    </p>

                </div>


                <!-- 가격 영역 -->
                <div class="plan-price-area">

                    <div class="plan-price-main">
                        월 <span class="plan-price-number">${formatPrice(Number(vo.price))}</span>원
                    </div>

                    <div class="plan-price-discount">
                        36개월 약정 할인 시 <b>${formatPrice(Math.floor((vo.price * 0.75) / 100) * 100)}원</b>
                    </div>

                    <div class="plan-tax">
                        부가세 포함 금액
                    </div>

                    ${LOGIN_ADMIN || loginEmployee ? ``:`
                    <div class="plan-btn-area">
                        <button class="plan-btn-compare" onclick = "location.href='/main-contract/list'">약정선택</button>
                        <button class="plan-btn-apply" onclick = "planConfirm(${vo.no})">신청하기</button>
                    </div>`}

                </div>

            </div>



            <!-- 요금제 특징 -->
            <div class="plan-feature-area">

                <div class="plan-feature-card">
                    <div class="feature-title">데이터</div>
                    <div class="feature-value">${vo.dataTotal}</div>
                </div>

                <div class="plan-feature-card">
                    <div class="feature-title">공유 데이터</div>
                    <div class="feature-value">
                        테더링+쉐어링
                        <div class="feature-highlight">${vo.dataShare}</div>
                    </div>
                </div>

                <div class="plan-feature-card">
                    <div class="feature-title">음성통화</div>
                    <div class="feature-value">
                        집/이동전화 무제한
                        <div class="feature-sub">
                            +부가통화 ${vo.voiceTotal}
                        </div>
                    </div>
                </div>

                <div class="plan-feature-card">
                    <div class="feature-title">문자메시지</div>
                    <div class="feature-value">
                        ${vo.smsTotal}
                    </div>
                </div>

            </div>
            <!-- 상세 혜택 -->
            <div class="plan-benefit-section">

                <div class="plan-section-title">요금제 혜택</div>

                <div class="plan-benefit-grid">

                    <div class="plan-benefit-card">
                        <div class="benefit-title">데이터 제공량</div>
                        <div class="benefit-value">${vo.dataTotal}</div>
                        <div class="benefit-sub">소진 시 속도 제한 적용</div>
                    </div>

                    <div class="plan-benefit-card">
                        <div class="benefit-title">테더링 / 데이터 쉐어링</div>
                        <div class="benefit-value">${vo.dataShare}</div>
                    </div>

                    <div class="plan-benefit-card">
                        <div class="benefit-title">음성 통화</div>
                        <div class="benefit-value">무제한</div>
                        <div class="benefit-sub">부가통화 ${vo.voiceTotal}</div>
                    </div>

                    <div class="plan-benefit-card">
                        <div class="benefit-title">문자</div>
                        <div class="benefit-value">${vo.smsTotal}</div>
                    </div>

                </div>

            </div>


            <!-- 이용 안내 -->
            <div class="plan-notice-section">

                <div class="plan-section-title">이용 안내</div>

                <ul class="plan-notice-list">
                    <li>36개월 약정 기준 요금입니다.</li>
                    <li>부가세 포함 금액입니다.</li>
                    <li>데이터 소진 시 속도 제한이 적용될 수 있습니다.</li>
                    <li>테더링 및 쉐어링 데이터는 월 제공량 내에서 사용 가능합니다.</li>
                </ul>

            </div>

            <div class="plan-bottom-space"></div>

        `
    tag.innerHTML = str;
}

loadPlanDetail();

function formatPrice(price){
    return price.toLocaleString();
}
function planConfirm(pno){
    if(!LOGIN_MEMBER){
        alert("로그인 후 진행해주세요.")
        location.href = `/member/login`
    }else{
        const mcNo = localStorage.getItem("userMainContractNum");
        if(!mcNo){
            alert("약정 선택후 진행 하세요.")
            location.href = `/main-contract/list`
        }else{
            localStorage.setItem("planNo",pno);
            location.href='/planConfirm/mypage/join'
        }
    }
}