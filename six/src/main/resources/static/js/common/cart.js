async function load() {
    
function getCartFromStorage() {

    const planNo = localStorage.getItem("planNo");
    const contractNo = localStorage.getItem("userMainContractNum");
    const addNo = localStorage.getItem("choiceServiceNo");

    return {
        plan: planNo ? {
            planId: Number(planNo),
            contractMonth: contractNo ? Number(contractNo) : null
        } : null,

        addServices: addNo ? [Number(addNo)] : []
    };
}


const cart = getCartFromStorage();

const planSection = document.querySelector(".plan-section");
const addSection = document.querySelector(".add-section");

/* =========================
   요금제 + 약정
========================= */
if(cart?.plan?.planId){

    const resp = await fetch(`/api/plan/detail/${cart.plan.planId}`);

    const data = await resp.json();
    const plan = data.vo;

    let contractHtml = "";

    if(cart.plan.contractMonth){
        const resp = await fetch(`/api/main-contract/${cart.plan.contractMonth}`)
        const data = await resp.json();
        const vo = data.vo;
        contractHtml = `
            <div class="contract-box active" onclick="location='/main-contract/list'">
                <div>${vo.term}개월</div>
                <div class="discount">${vo.discountRate}% 할인</div>
            </div>
        `;
    } else {
        contractHtml = `
            <button class="btn-contract" onclick="location='/main-contract/list'">약정 선택</button>
        `;
    }

    planSection.innerHTML = `
        <div class="plan-card">

            <!-- 뱃지 -->
            <div class="badge">추천</div>

            <!-- 헤더 -->
            <div class="plan-header">
                <h2>${plan.name}</h2>
                <p class="desc">${plan.description || ""}</p>
            </div>

            <!-- 가격 -->
            <div class="price-area">
                <span class="price">${Number(plan.price).toLocaleString()}</span>
                <span class="unit">원 / 월</span>
            </div>

            <!-- 혜택 -->
            <div class="benefit-area">
                <div class="benefit">
                    <span class="label">데이터</span><br>
                    <span class="value">${plan.dataTotal}</span>
                </div>
                <div class="benefit">
                    <span class="label">음성</span><br>
                    <span class="value">${plan.voiceTotal}</span>
                </div>
                <div class="benefit">
                    <span class="label">문자</span><br>
                    <span class="value">${plan.smsTotal}</span>
                </div>
            </div>

            <!-- 약정 -->
            <div class="contract-area">
                ${contractHtml}
            </div>

            <!-- CTA -->
            <div class="action-area">
                <button class="btn-primary" onclick="location='/planConfirm/mypage/join'">가입 이어가기</button>
                <button class="btn-secondary" onclick="location='/plan/list/1'">요금제 변경</button>
            </div>

        </div>
    `;
}

/* =========================
   부가서비스
========================= */
if(cart?.addServices?.length > 0){

    const resp = await fetch(`/api/add-service/${cart.addServices[0]}`);

    if(!resp.ok){
        console.error("add-service API 실패", resp.status);
        return;
    }

    const data = await resp.json();

    const vo = data.vo;

    if(!vo){
        console.error("vo 없음", data);
        return;
    }

    addSection.innerHTML = `
        <div class="add-card">
            <div class="add-left">
                <h4>${vo.service}</h4>
                <p>${vo.content}</p>
            </div>
            <div class="add-right">
                <span class="price">${Number(vo.price).toLocaleString()}원</span>
                <button class="btn-remove" onclick=delAdd();>삭제</button>
            </div>
        </div>
    `;
    }
    
}

function delAdd(){
    localStorage.removeItem("choiceServiceNo");
    location.reload();
}

if(LOGIN_MEMBER){
    window.addEventListener("pageshow", function () {
    load();
});
}
