async function loadPlanConfirmList(){
    const url = new URL(window.location.href);
    const selectTable = url.searchParams.get("selectTable");
    const currentPage = url.searchParams.get("currentPage") ;
    const sort = url.searchParams.get("sort") || 'latest';
    const memberNo = localStorage.getItem("memberNo");

    const resp = await fetch(`/confirm/plan` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {selectTable , memberNo, currentPage, sort} ) ,
    });
    const data = await resp.json();
    const voList = data.voList;
    const pvo = data.pvo;
    
    const memberName = data.memberName;
    const isAdmin = pvo.employee;

    const titleH2 = document.querySelector(".title-area h2");
    const titleP = document.querySelector(".title-area p");

    // 페이징처리
    setPageArea(pvo);

    //화면 채우기


    if(isAdmin){
        if(selectTable === "delete"){
            titleH2.innerHTML = `<span class="highlight">'${memberName}'</span> 님의 요금제 해지 신청 내역`;
            titleP.innerHTML = "고객의 요금제 해지 신청내역을 검토하고 승인/반려할 수 있습니다."
        }else if(selectTable === "update"){
            titleH2.innerHTML = `<span class="highlight">'${memberName}'</span> 님의 요금제 변경 신청 내역`;
            titleP.innerHTML = "고객의 요금제 변경 신청내역을 검토하고 승인/반려할 수 있습니다."
        }else{
            titleH2.innerHTML = `<span class="highlight">'${memberName}'</span> 님의 요금제 신규 신청 내역`;
            titleP.innerHTML = "고객의 요금제 신규 신청내역을 검토하고 승인/반려할 수 있습니다."
        } 
    }else{
        if(selectTable === "delete"){
            titleH2.innerHTML = `요금제 해지 신청 내역`;
            titleP.innerHTML = "신청하신 해지 신청 건의 처리 상태를 확인할 수 있습니다."
        }else if(selectTable === "update"){
            titleH2.innerHTML = `요금제 변경 신청 내역`;
            titleP.innerHTML = "신청하신 변경 신청 건의 처리 상태를 확인할 수 있습니다."
        }else{
            titleH2.innerHTML = `요금제 신규 신청 내역`;
            titleP.innerHTML = "신청하신 신규 신청 건의 처리 상태를 확인할 수 있습니다."
        } 
    }

    document.querySelector("#sortSelect").value = (sort == null || sort == "null"?"latest":sort);

    const tbodyTag = document.querySelector(".history-list");

    let str = "";
    if(voList.length == 0){
        str += `<p class="notice-text"> - 요금제 신청 내역이 없습니다. - </p>`
        console.log("비어있음");
    }

    for(const vo of voList){
        // 포맷팅
        vo.phone = formatPhone(vo.phone);
        vo.confirmAt = formatDate(vo.confirmAt);

        if(vo.confirmStatus === "A" && !pvo.employee){
            vo.confirmStatus = '<span class="status-badge waiting">검토 중</span>';
        }else if(vo.confirmStatus === "A" && pvo.employee){
            // 관리자 계정이면
            vo.confirmStatus = `
                <select class="status-badge waiting" onclick="event.stopPropagation()" data-no="${vo.no}" data-phone="${vo.phone}">
                    <option class="waiting" value="A">검토중</option>
                    <option class="rejected" value="N">반려</option>
                    <option class="complete" value="Y">승인</option>
                </select>
            `;
        }
        if(vo.confirmStatus === "N")vo.confirmStatus = '<span class="status-badge rejected">신청 반려</span>';
        if(vo.confirmStatus === "Y")vo.confirmStatus = '<span class="status-badge complete">승인 완료</span>';

        
        str += `<div class="history-card">
                <div class="card-left">
                    <span class="order-no">신청번호: ${vo.no}</span>
                    <h3 class="service-title">${vo.planName}</h3>
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
    tbodyTag.innerHTML = str;
}

// 서비스 컨펌 여부 바꾸기
async function confirmPlan(selectTable, confirmStatus, no, phone) {

    try{
        let typeName = '신규 요청';
        if(selectTable == 'delete'){
            typeName == '삭제 요청'
        }else if(selectTable == 'update'){
            typeName == '변경 요청'
        }
        const statusName = (confirmStatus === 'Y') ? '승인' : '반려';
    
        const resp = await fetch(`/confirm/plan/employee/confirming` , {
            method : "POST" ,
            headers : {
                "Content-Type" : "application/json" ,
            } ,
            body : JSON.stringify( {selectTable, confirmStatus, no, phone} ) ,
        });
    
        if(!resp.ok){
            throw new Error("서버 응답 에러 ~");
        }
    
        alert(`요금제 ${typeName} ${statusName} 완료 !`);
        
        loadPlanConfirmList();
        } catch (error) {
        console.log(error);
        alert("요금제 신청내역 불러오기 실패");
    }
}

// 정렬 기준 바뀔때
const sortSelect = document.querySelector("#sortSelect");

sortSelect.addEventListener("change", () => {
    const url = new URL(window.location.href);
    url.searchParams.set("sort", sortSelect.value);
    history.replaceState(null, "", url);
    loadPlanConfirmList();
});

// 신청 상태 바뀔때
document.querySelector(".history-list").addEventListener("change", (e) => {
    if(!e.target.classList.contains("status-badge")) return;

    const url = new URL(window.location.href);
    const prevStatus = e.target.dataset.prev;
    const selectTable = url.searchParams.get("selectTable");
    const confirmStatus = e.target.value;
    const no = e.target.dataset.no;
      const phone = e.target.dataset.phone.replace(/\D/g, ""); // dataset에서 phone 꺼내기

    const ok = confirm("상태를 변경하시겠습니까?");
    if(!ok){
        select.value = prevStatus;
        return;
    }
    

    confirmPlan(selectTable, confirmStatus, no, phone);
});

// 페이징 버튼 생성기
function setPageArea(pvo){
    const pageArea = document.querySelector("#page-area");

    let str = '';
    // 이전 버튼
    if(pvo.startPage != 1){
        str += `<div onclick="location.href='/confirm/plan?selectTable=${pvo.selectTable}&sort=${pvo.sort}&currentPage=${pvo.startPage-1}'">&#60;</div>`;
    }
    for(let i = pvo.startPage; i <= pvo.endPage; ++i){
        // 현재 페이지인 경우 active 클래스 추가
        const activeClass = (i == pvo.currentPage) ? 'active' : '';
        str += `<div onclick="location.href='/confirm/plan?selectTable=${pvo.selectTable}&sort=${pvo.sort}&currentPage=${i}'">${i}</div>`;
    }
    // 다음 버튼
    if(pvo.endPage < pvo.maxPage){
        str += `<div onclick="location.href='/confirm/plan?selectTable=${pvo.selectTable}&sort=${pvo.sort}&currentPage=${pvo.endPage+1}'">&#62;</div>`;
    }
    pageArea.innerHTML = str;
}

// 페이지 로드 시 즉시 목록 불러오기
document.addEventListener("DOMContentLoaded", () => {
    loadPlanConfirmList();
});