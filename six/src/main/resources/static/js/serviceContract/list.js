async function loadServiceConfirmList(){
    const url = new URL(window.location.href);
    const sort = url.searchParams.get("sort");

    const resp = await fetch(`/serviceContract` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {sort} ) ,
    });
    if(!resp.ok){
        throw new Error("가입내역 불러오기 실패")
    }
    const voList = await resp.json();


    //화면 채우기
    document.querySelector("#sortSelect").value = sort == null || sort == "null"?"latest":sort;
    
    console.log(voList);
    const tbodyTag = document.querySelector(".history-list");
    let str = "";
    for(const vo of voList){
        
        
        vo.phone = formatPhone(vo.phone);
        const del = formatDate(vo.delDate);

        console.log(vo.hasConfirm + "인 번호 "+ vo.no);
        

                    xSign = "";

        // 해지일과 처리여부
        if(!vo.delDate && !vo.hasConfirm){
            vo.delDate = '<span class="status-badge complete">사용 중</span>';
            xSign = `<div class="x-icon" onclick="deleteCheck(${vo.no})">
                        <svg viewBox="0 0 24 24" width="20" height="20">
                            <line x1="5" y1="5" x2="19" y2="19" stroke="red" stroke-width="3"/>
                            <line x1="19" y1="5" x2="5" y2="19" stroke="red" stroke-width="3"/>
                        </svg>
                    </div>`;
        }else if(vo.hasConfirm){
            vo.delDate = '<span class="status-badge waiting">처리 중</span>';
        }else if(vo.confirmStatus === 'N'){
            vo.delDate = '<span class="status-badge waiting">반려됨</span>';
        }else{
            vo.delDate = 
                `<span class="status-badge rejected">해지</span>
                <p class="process-date">처리 일 : ${del}</p>                            
                `;
        }
        
        str += `<div class="history-card" >
                <div class="card-left">
                    <span class="order-no">신청번호: ${vo.no}</span>
                    <h3 class="service-title">${vo.service}</h3>
                    <p class="phone-num">가입 번호 : ${vo.phone}</p>
                </div>
                <div class="card-right">
                    ${vo.delDate}
                </div>
                ${xSign}
            </div>
        `;
    }
    if(voList.length == 0){
        str += `<p class="notice-text"> - 부가서비스 신청 내역이 없습니다. - </p>`
        console.log("비어있음");
        
    }
    
    tbodyTag.innerHTML = str;
}

//페이지 로드되면
try{
    loadServiceConfirmList();
} catch (error) {
    console.log(error);
    alert("부가서비스 신청내역 불러오기 실패");
}

async function deleteCheck(no) {
    
    const ok = confirm("부가서비스를 해지 하겠습니까?")
    if(!ok){
        return;
    }
    
    const resp = await fetch(`/serviceContract` , {
        method : "DELETE" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {no} ) ,
    });
    const data = await resp.json();
    if(!resp.ok){
        alert(data.message);
        throw new Error("서비스 해지 신청 실패");
    }
    
    alert("서비스 해지 신청 성공");
    loadServiceConfirmList();
}

// 정렬 기준 바뀔때
const sortSelect = document.querySelector("#sortSelect");

sortSelect.addEventListener("change", () => {
    const url = new URL(window.location.href);
    url.searchParams.set("sort", sortSelect.value);
    history.replaceState(null, "", url);
    loadServiceConfirmList();
});