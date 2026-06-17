async function loadServiceConfirmInfo() {
    const url = new URL(window.location.href);
    const serviceNo = url.searchParams.get("serviceNo");

    const resp = await fetch(`/confirm/service/detail` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {serviceNo} ) ,
    });
    const data = await resp.json();
    const sjv = data.sjv;    

    //화면 구성

    //세션 확인
    const fixedResp = await fetch(`/bill/getFixed` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
    });
    //세션 비었으면
    if(fixedResp.status === 204){
        document.querySelector("#account-info").innerHTML = 
                `<section class="title-area">
                    <h2>요금제를 먼저 가입해 주세요</h2>
                </section>`;
        document.querySelector("#submitBtn").remove();
    }

    //이미 신청한 내용이면
    if(sjv.hasConfirm){
        document.querySelector("#submitBtn").remove();
        document.querySelector(".btn-group").innerHTML +=
            `<button type="button" class="btn-gray-full">처리중 입니다</button>`
    }

    document.querySelector("#memberId").innerHTML = sjv.memberId;
    document.querySelector("#memberName").innerHTML = sjv.memberName;
    document.querySelector("#phone-display").innerHTML = formatPhone(sjv.phone);
    document.querySelector("#resident-display").innerHTML = formatResident(sjv.resident);
    document.querySelector("#service-name").innerHTML = sjv.service;
    document.querySelector("#service-name").dataset.serviceNo = sjv.serviceNo;
    document.querySelector("#price").innerHTML = formatMoney(sjv.price);
    document.querySelector("#payment-name").innerHTML = sjv.paymentName;
    document.querySelector("#account-number").innerHTML = formatCardNumber(sjv.accountNumber);
}

// 시작시 불러오기
try{
    loadServiceConfirmInfo();
} catch (error) {
    console.log(error);
    alert("부가서비스 신청 페이지 불러오기 실패");
}

async function submitJoin() {
    const serviceNo = document.querySelector("#service-name").dataset.serviceNo;

    const resp = await fetch(`/confirm/service/insert`, {
        method : "PUT" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {serviceNo} ) ,
    });
    const data = await resp.json();

    if(!resp.ok){
        alert(data.message);
        throw new Error("서비스 해지 신청 실패")
    }

    alert("서비스 신규 가입 성공 !")
    localStorage.removeItem("choiceServiceNo");
    location.href='/home'; // 우선 홈으로 보내기
}