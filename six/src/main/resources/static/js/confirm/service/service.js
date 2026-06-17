async function loadServiceConfirmList(){
    const url = new URL(window.location.href);
    const selectTable = url.searchParams.get("selectTable");
    const currentPage = url.searchParams.get("currentPage");
    const sort = url.searchParams.get("sort");

    const resp = await fetch(`/confirm/service` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {selectTable, currentPage, sort} ) ,
    });

    if (!resp.ok) {
        console.error("서비스 신청 패이지 실패");
        return;
    }

    const data = await resp.json();
    const voList = data.voList;
    const pvo = data.pvo;
    const memberName = data.name;

    setPageArea(pvo);

    //화면 채우기

    const titleH2 = document.querySelector(".title-area h2");
    const titlep = document.querySelector(".title-area p");

    if(pvo.employee){
        if(selectTable === "delete"){
            titleH2.innerHTML = `<span class="highlight">'${memberName}'</span> 님의 부가서비스 해지 신청 내역`;
            titlep.innerHTML = "고객의 부가서비스 해지 신청내역을 검토하고 승인/반려할 수 있습니다."
        }else{
            titleH2.innerHTML = `<span class="highlight">'${memberName}'</span> 님의 부가서비스 신규 신청 내역`;
            titlep.innerHTML = "고객의 부가서비스 신규 신청내역을 검토하고 승인/반려할 수 있습니다."
        }
    }else{
        if(selectTable === "delete"){
            titleH2.innerHTML = "부가서비스 해지 신청 내역";
            titlep.innerHTML = "신청하신 해지 신청 건의 처리 상태를 확인할 수 있습니다."
        }else{
            titleH2.innerHTML = "부가서비스 신규 신청 내역";
            titlep.innerHTML = "신청하신 신규 신청 건의 처리 상태를 확인할 수 있습니다."
        }
    }
    document.querySelector("#sortSelect").value = sort == null || sort == "null"?"latest":sort;
        
    const tbodyTag = document.querySelector(".history-list");
    let str = "";
    for(const vo of voList){
        
        vo.phone = formatPhone(vo.phone);
        vo.confirmAt = formatDate(vo.confirmAt);

        if(vo.confirmStatus === "A" && !pvo.employee){
            vo.confirmStatus = '<span class="status-badge waiting">검토 중</span>';
        }else if(vo.confirmStatus === "A" && pvo.employee){
            // 관리자 계정이면
            vo.confirmStatus = `
                <select class="status-badge waiting" onclick="event.stopPropagation()" data-no="${vo.no}">
                    <option class="waiting" value="A">검토중</option>
                    <option class="rejected" value="N">반려</option>
                    <option class="complete" value="Y">승인</option>
                </select>
            `;
        }
        if(vo.confirmStatus === "N")vo.confirmStatus = '<span class="status-badge rejected">신청 반려</span>';
        if(vo.confirmStatus === "Y")vo.confirmStatus = '<span class="status-badge complete">승인 완료</span>';

        str += `<div class="history-card" >
                <div class="card-left">
                    <span class="order-no">신청번호: ${vo.no}</span>
                    <h3 class="service-title">${vo.serviceName}</h3>
                    <p class="phone-num">가입 번호 : ${vo.phone}</p>
                </div>
                <div class="card-right">
                    ${vo.confirmStatus}
                    <p class="process-date">처리 일 : ${vo.confirmAt}</p>
                </div>
                <div class="arrow-icon">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#ccc" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M9 18l6-6-6-6"/></svg>
                </div>
            </div>
        `;
    }
    if(voList.length == 0){
        str += `<p class="notice-text"> - 부가서비스 신청 내역이 없습니다. - </p>`
        console.log("비어있음");
        
    }
    
    tbodyTag.innerHTML = str;
}

// 서비스 컨펌 여부 바꾸기
async function confirmService(selectTable, confirmStatus, no) {
    console.log(selectTable, confirmStatus, no);
    
    const resp = await fetch(`/confirm/service/confirming` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {selectTable, confirmStatus, no} ) ,
    });

    if (!resp.ok) {
        console.error("서비스 컨펌 실패");
        return;
    }
    alert("변경 성공")
    loadServiceConfirmList();
}

//페이지 로드되면
try{
    loadServiceConfirmList();
} catch (error) {
    console.log(error);
    alert("부가서비스 신청내역 불러오기 실패");
}

// 정렬 기준 바뀔때
const sortSelect = document.querySelector("#sortSelect");

sortSelect.addEventListener("change", () => {
    const url = new URL(window.location.href);
    url.searchParams.set("sort", sortSelect.value);
    history.replaceState(null, "", url);
    loadServiceConfirmList();
});

// 신청 상태 바뀔때
document.querySelector(".history-list").addEventListener("change", (e) => {
    if(!e.target.classList.contains("status-badge")) return;

    const url = new URL(window.location.href);
    const prevStatus = e.target.dataset.prev;
    const selectTable = url.searchParams.get("selectTable");
    const confirmStatus = e.target.value;
    const no = e.target.dataset.no;

    const ok = confirm("상태를 변경하시겠습니까?");
    if(!ok){
        select.value = prevStatus;
        return;
    }

    confirmService(selectTable, confirmStatus, no);
});

// 페이징 버튼 생성기
function setPageArea(pvo){
    const pageArea = document.querySelector("#page-area");

    let str = '';
    if(pvo.startPage != 1){
        str += `<div onclick="location.href='/confirm/service?selectTable=${pvo.selectTable}&sort=${pvo.sort}&currentPage=${pvo.startPage-1}'">&#60;</div>`;
    }
    for(let i = pvo.startPage; i <= pvo.endPage; ++i){
        str += `<div onclick="location.href='/confirm/service?selectTable=${pvo.selectTable}&sort=${pvo.sort}&currentPage=${i}'">${i}</div>`;
    }
    if(pvo.endPage < pvo.maxPage){
        str += `<div onclick="location.href='/confirm/service?selectTable=${pvo.selectTable}&sort=${pvo.sort}&currentPage=${pvo.endPage+1}'">&#62;</div>`;
    }
    pageArea.innerHTML = str;
}