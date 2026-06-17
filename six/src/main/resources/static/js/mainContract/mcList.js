async function loadMainContractList() {
    try {
        const tag = document.querySelector(".contract-list");
        const resp = await fetch(`/api/main-contract/list`);
        if(!resp.ok){
            throw new Error("리스트 불러오기 에러.. resp not ok");
        }
        const data = await resp.json(); 
        
        let str = "";
        for(const vo of data.voList){
            str += `<div class="contract-card">
                        <div class="info-section">
                            <span class="badge">선택가능</span>
                            <div class="category">선택약정 할인 ></div>
                            <div class="title">${vo.term}개월 요금할인 정책</div>
                            <div class="subtitle">전체 5G 요금제 대상</div>
                            <div class="price-row">
                                <span class="main-benefit">기본료 ${vo.discountRate}% 할인</span>
                                <span class="sub-text">결합 할인 중복 가능</span>
                            </div>
                        </div>
                        <div class="action-section">
                            <div class="details">
                                <div class="detail-row"><span class="detail-label">약정기간</span> <b>${vo.term}개월</b></div>
                                ${LOGIN_ADMIN ? `
                                <div class="detail-row"><span class="detail-label">위약금</span> <b>할인받은 금액의 ${vo.penalty}%</b></div>`:`
                                <div class="detail-row"><span class="detail-label">위약금</span> <b>반환금 구조</b></div>`}
                            </div>
                            
                            <div class="btn-group">
                            ${LOGIN_MEMBER ? `<div class="btn btn-edit" onclick="choice(${vo.no})">약정선택하기</div>`:``}
                            ${LOGIN_ADMIN ? `
                                <div class="btn btn-edit" onclick="update(${vo.no});">수정하기</div>
                                <div class="btn btn-delete" onclick="del(${vo.no})">삭제</div>`:``}
                            </div>
                        </div>
                    </div>`
        }
    tag.innerHTML = str;
    } catch (error) {
        alert(error);
    }
    
}
loadMainContractList();

function update(no){
    localStorage.setItem("mcNo",no);
    location.href = "/main-contract/admin/update"
}
async function del(no){
    try {
        const resp = await fetch(`/api/main-contract/admin/delete`,{
            method : "DELETE",
            headers : {
                "Content-Type" : "application/json"
            },
            body : JSON.stringify({no})
        })
        if(!resp.ok){
            throw new Error("delete fail.. resp not ok");
        }
        const data = await resp.json();
        if(data.result != 1){
            throw new Error("delete fail.. result not 1");
        }
        alert("삭제 성공!");
        location.href = `/main-contract/list`
    } catch (error) {
        console.log(error);
    }
}

function choice(no){
    localStorage.setItem("userMainContractNum",no);
    alert("약정 선택 완료!");
    history.go(-1);
}