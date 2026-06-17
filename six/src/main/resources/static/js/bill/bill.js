const calendarBtn = document.getElementById("calendarBtn");
const calendarPopup = document.getElementById("calendarPopup");

function toggleCalendar(e){
    e.stopPropagation();
    const popup = document.querySelector("#calendarPopup");

    if(popup.style.display === "block"){
        popup.style.display = "none";
    }else{
        popup.style.display = "block";
    }

}

function toggleYearPopup(e){

    e.stopPropagation();

    const popup = document.querySelector("#yearPopup");

    if(popup.style.display === "block"){
        popup.style.display = "none";
    }else{
        popup.style.display = "block";
    }

}

let currentBillMonth;
let monthData;
let currentBillYear;

async function loadBillVo(billMonth){
    const resp = await fetch(`/bill` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json" ,
        } ,
        body : JSON.stringify( {billMonth} ) ,
    });

    if(!resp.ok){
        throw new Error("청구서 불러오기 실패");
    }

    const data = await resp.json();
    const vo = data.vo;

    // 청구서가 있는지 확인하는 fetch함수
    const resp2 = await fetch(`/bill/getMonth/${vo.fixedNo}`,{
        method : "POST"
    });

    const monthList = await resp2.json();

    monthData = convertMonthYear(monthList);
    currentBillMonth = vo.billMonth;
    
    // 청구서가 있는 년도만 설정 가능하게 하기
    renderYearPopup();
    
    // 청구서가 없는 달 회색으로 비활성화
    // 지정된 달 주황색으로 활성화
    renderMonths();
    
    // 번호 불러오기
    renderPhones();

    document.querySelector("#billDate").innerText = renderBillDate();
    document.querySelector("#fullPriceValue").innerHTML = formatMoney(parseInt(vo.billTotal) + parseInt(vo.etcBil));
    document.querySelector("#billTotal").innerHTML = formatMoney(vo.billTotal);
    document.querySelector("#paymentName").innerHTML = vo.paymentName;
    document.querySelector("#accountNumber").innerHTML = formatCardNumber(vo.accountNumber);//암호화 하기
    document.querySelector("#monthBillPrice").innerHTML = parseInt(currentBillMonth.substring(4,6)) + "월 납부 금액"
    document.querySelector("#etcBill").innerHTML = formatMoney(vo.etcBil);
    document.querySelector("#payYn").innerHTML = vo.payYn === 'N' ? "미납" : "수납 완료";
    
}

function convertMonthYear(monthList){
    const result = {};

    monthList.forEach(month => {

        const year = month.substring(0,4);
        const m = month.substring(4,6);

        if(!result[year]){
            result[year] = {};
        }

        result[year][m] = true;

    });

    return result;
}

try{
    loadBillVo(null);
} catch (error) {
    console.log(error);
    alert("청구서 불러오기 실패");
}

// 번호 누르면 해당 데이터로 조회

function selectMonth(btn){
    if(btn.disabled){
        return;
    }
    const year = document.querySelector("#yearBtn").innerText;
    const month = btn.innerText.padStart(2,"0");

    currentBillMonth = year + month;

    loadBillVo(currentBillMonth);
}

function selectYear(e, year){

    e.stopPropagation();

    document.querySelector("#yearBtn").innerText = year;

    document.querySelector("#yearPopup").style.display = "none";

    currentBillYear = year;


    renderMonths();
}

async function renderPhones(){
    const popup = document.querySelector("#phone .phone-list");
    popup.innerHTML = "";
    const resp = await fetch(`/bill/selectPhones` , {
        method : "POST" ,
    });

    const fixedResp = await fetch(`/bill/getFixed`, {
        method : "POST",
    });

    if (!resp.ok) {
        console.error("번호 가져오기 실패");
        return;
    }

    const data = await fixedResp.json();
    const voList = await resp.json();

    
    voList.forEach(vo=>{
        vo.phone = formatPhone(vo.phone);
        popup.innerHTML += `<option value="${vo.no}">${vo.phone}</option>`;

        popup.value = data.no
    });
}

document.querySelector("#phone").addEventListener("change", (e) => {
    if(!e.target.classList.contains("phone-list")) return;

    
    const fixedNo = e.target.value;
    setFixedNo(fixedNo);
});

async function setFixedNo(num) {
    const resp = await fetch(`/bill/setFixed/${num}` , {
        method : "POST" ,
    });

    if(!resp.ok){
        throw new Error("세션 바꾸기 실패");
    }
    loadBillVo(null);
}

// 달력 구성 팝업

function renderYearPopup(){

    const popup = document.querySelector("#yearPopup");
    popup.innerHTML = "";
    const years = Object.keys(monthData).sort();
    years.forEach(year => {
        popup.innerHTML += `
            <button onclick="selectYear(event, '${year}')">
                ${year}
            </button>
        `;
    });
}

function renderMonths(){

    const months = document.querySelectorAll("#months button");
    if(currentBillYear == null){
        currentBillYear = currentBillMonth.substring(0,4);
    }
    
    months.forEach(btn => {

        const month = btn.innerText.padStart(2,"0");

        btn.classList.remove("active", "disabled");
        btn.disabled = false;

        if(!monthData[currentBillYear] || !monthData[currentBillYear][month]){
            btn.disabled = true;
            btn.classList.add("disabled");
        }

        const bYear = currentBillMonth.substring(0,4);
        const bMonth = currentBillMonth.substring(4,6);

        if(currentBillYear === bYear && month === bMonth){
            btn.classList.add("active");
        }

    });

}

// 데이터 표현식 바꾸기

function renderBillDate(){

    const year = parseInt(currentBillMonth.substring(0,4));
    const month = parseInt(currentBillMonth.substring(4,6));

    const thisMonthLast = new Date(year, month, 0).getDate();

    const prevMonthLast = new Date(year, month-1, 0).getDate();

    const prevYear = month === 1 ? year-1 : year;
    const prevMonth = month === 1 ? 12 : month-1;

    const start =
        prevYear + "." +
        String(prevMonth).padStart(2,"0") + "." +
        String(prevMonthLast).padStart(2,"0");

    const end =
        year + "." +
        String(month).padStart(2,"0") + "." +
        String(thisMonthLast).padStart(2,"0");

    return start + " ~ " + end;

}

function formatPhone(phone){

    const num = phone.replace(/\D/g,"");

    if(num.length === 11){
        return num.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
    }

    return phone;
}

function formatMoney(value){

    return Number(value).toLocaleString("ko-KR") + "원";

}

async function changebtn(){
    const fixedResp = await fetch(`/bill/getFixed`, {
        method : "POST",
    });
    const data = await fixedResp.json();
    location.href = "/planConfirm/mypage/changeAccount?phone=" + data.phone
}

document.addEventListener("click", function(){
    const popup = document.getElementById("calendarPopup");
    popup.style.display = "none";

});