document.querySelector("#sort-select").addEventListener("change", (e)=>{

    const sort = e.target.value;

    let pno = Number(location.pathname.split("/").pop());
    if(!pno){
        pno = 1;
    }

    location.href = `/plan/list/1?sort=${sort}`;
});

async function loadBoardVoList(){
    const params = new URLSearchParams(location.search);
    const sort = params.get("sort") ?? "latest";
    document.querySelector("#sort-select").value = sort;
    let pno = Number(location.pathname.split("/").pop());
    if(!pno){
        pno = 1;
    }
    const resp = await fetch(`/api/plan?currentPage=${pno}&sort=${sort}`);

    if(!resp.ok){
        throw new Error("select plan list fail..");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList

    console.log(voList);
    

    const Tag = document.querySelector("main #plan-area");

    let str = "";
    for(const vo of voList){
        str += `
            <div class="plan-card">
                <div class="plan-left">

                    ${vo.category === 'popularity' ? `<div class="badge-popular">인기</div>` : ""}
                    ${vo.category === 'new' ? `<div class="badge-new">최신</div>` : ""}  

                    <a href = '/plan/detail/${vo.no}'>    
                    <div class="plan-subtitle">${vo.name} ></div>

                        <h2 class="plan-title">
                            데이터 ${vo.dataTotal}
                            ${vo.dataTotal === '무제한' ? "":'<span class="plan-v2-speed">+다 쓰면 최대 1Mbps</span>'} 
                            <br />
                            <span>테더링+쉐어링 ${vo.dataShare}</span>
                        </h2></a>

                        <div class="price-area">
                            <span class="price">월 ${Number(vo.price).toLocaleString()}원</span>
                            <span class="discount">36개월 약정시 월 ${(Math.floor((vo.price * 0.75) / 100) * 100).toLocaleString()}원</span>
                            <button class="detail-btn" onclick="location.href='/main-contract/list'">할인 상세보기</button>
                        </div>
                    </div>

                    <div class="plan-divider"></div>

                    <div class="plan-right">
                    <div class="benefit">
                        <span class="label">음성통화</span>
                        <span class="value">${vo.voiceTotal}</span>
                    </div>
                    <div class="benefit">
                        <span class="label">문자메시지</span>
                        <span class="value">${vo.smsTotal}</span>
                    </div>

                    <div class="button-area">
                        <button class="compare-btn" onclick="planEdit(${vo.no});">요금제 변경하기</button>
                    
                </div>
                </div>
            </div>
            <br>
        `;
    }
    Tag.innerHTML = str;
    setPageArea(pvo);
}

loadBoardVoList().catch(error=>{
    console.log(error);
    alert("게시글 불러오기 실패...");
});

function setPageArea(pvo){
    const sort = document.querySelector("#sort-select")?.value ?? "latest";
    const pageArea = document.querySelector("#page-area");
    let str = "";
    if(pvo.startPage != 1){
        str += `<button onclick="location.href='/plan/list/${pvo.startPage-1}?sort=${sort}'">이전</button>`;
    }

    for(let i = pvo.startPage; i<=pvo.endPage; ++i){
        str += `<button onclick="location.href='/plan/list/${i}?sort=${sort}'">${i}</button>`;
    }

    if(pvo.endPage < pvo.maxPage){
        str += `<button onclick="location.href='/plan/list/${pvo.endPage+1}?sort=${sort}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}

function planEdit(no){
    localStorage.setItem("editPlanNum",no)
    location.href = `/planConfirm/mypage/update`
}