let serviceList = [];

let currentPage = 1;
let category = "ALL";

window.addEventListener("DOMContentLoaded", () => {
    loadService();
    initTabs();
});


// 서비스 목록 불러오기
async function loadService(){

    const resp = await fetch(`/api/add-service?currentPage=${currentPage}&category=${category}`);

    if(!resp.ok){
        alert("서비스 목록 조회 실패");
        return;
    }

    const data = await resp.json();
    console.log(data);
    serviceList = data.voList;

    render(serviceList);
    renderPaging(data.pvo);
}



// 화면 렌더링
function render(voList){

    const tag = document.querySelector(".service-list");

    let str = "";

    for(const vo of voList){

        str += `
        <div class="service-item">

            <div class="info">

                <div class="tags">
                    <span class="tag purple">${vo.category}</span>
                </div>

                <h3>
                    ${vo.service}
                    <span class="arrow">></span>
                </h3>

                <p>${vo.content}</p>

            </div>

            <div class="action">

                <div class="price">
                    월 ${Number(vo.price).toLocaleString()}원
                    <span>부가세 포함 금액</span>
                </div>
                ${loginEmployee ? ``:`
                ${LOGIN_ADMIN ? `
                        <button class="btn-join" onclick="editService(${vo.no})">수정하기</button>
                        <button class="btn-join" onclick="deleteService(${vo.no})">삭제하기</button>
                    ` : `
                        <button class="btn-join" onclick="joinService(${vo.no})">서비스 가입</button>
                    `}`}
                

            </div>

        </div>
        `;
    }

    tag.innerHTML = str;
}
function joinService(no){
    if(!LOGIN_MEMBER){
        alert("로그인 후 진행해주세요.")
        location.href = `/member/login`
    }else{
        localStorage.setItem("choiceServiceNo",no);
        location.href = `/confirm/service/detail?serviceNo=${no}`
    }
}


// 페이징 렌더링
function renderPaging(pvo){

    const pagingTag = document.querySelector(".paging");

    if(!pvo){
        pagingTag.innerHTML="";
        return;
    }

    let str = "";

    // 처음
    if(pvo.currentPage > 1){
        str += `
        <a href="#" class="arrow" onclick="movePage(1)">«</a>
        `;
    }

    // 이전
    if(pvo.startPage > 1){
        str += `
        <a href="#" class="arrow" onclick="movePage(${pvo.startPage-1})">‹</a>
        `;
    }

    // 페이지 번호
    for(let i = pvo.startPage; i <= pvo.endPage; i++){

        str += `
        <a href="#"
           onclick="movePage(${i})"
           class="${i === pvo.currentPage ? 'active' : ''}">
           ${i}
        </a>
        `;
    }

    // 다음
    if(pvo.endPage < pvo.maxPage){
        str += `
        <a href="#" class="arrow" onclick="movePage(${pvo.endPage+1})">›</a>
        `;
    }

    // 마지막
    if(pvo.currentPage < pvo.maxPage){
        str += `
        <a href="#" class="arrow" onclick="movePage(${pvo.maxPage})">»</a>
        `;
    }
    pagingTag.innerHTML = str;
}

// 페이지 이동
function movePage(page){
    currentPage = page;
    loadService();
}



// 탭 필터
function initTabs(){
    const tabs = document.querySelectorAll(".tabs a");
    tabs.forEach(tab => {
        tab.addEventListener("click",(e)=>{
            e.preventDefault();
            category = tab.dataset.category;
            currentPage = 1;
            loadService();
        });
    });
}



// 수정 페이지 이동
function editService(no){

    localStorage.setItem("asNo",no);

    location.href="/add-service/admin/update";

}



// 삭제
async function deleteService(no){

    const check = confirm("정말 삭제하시겠습니까?");

    if(!check){
        return;
    }

    const resp = await fetch(`/api/add-service/admin/${no}`,{
        method:"DELETE"
    });

    if(!resp.ok){
        alert("삭제 실패");
        return;
    }

    alert("삭제 완료");

    loadService();

}