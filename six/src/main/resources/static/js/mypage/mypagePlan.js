async function togglePlanDropdown() {
    const planList = document.getElementById("planList"); // 전화번호가 들어갈 <ul> 또는 <div>
    const printPhoneNum = document.getElementById("printPhoneNum"); // 전화번호가 들어갈 <ul> 또는 <div>
    if (!planList) return;

    // 드롭다운 열기/닫기 애니메이션
    planList.classList.toggle("show");

    // 만약 열릴 때만 데이터를 새로 가져오고 싶다면?
    if (planList.classList.contains("show")) {
        const resp = await fetch('/mypage/phoneToggle');

        if (resp.ok) {
            const data = await resp.json(); // Controller에서 보낸 map을 받음
            const phoneListArray = data.phoneList; // ["010-1234-5678", "010-9876-5432"] 형태

            // 기존 목록 지우기
            planList.innerHTML = "";

            // 가져온 전화번호 개수만큼 HTML 생성
            phoneListArray.forEach(phone => {
                const li = document.createElement("li");

                // 1. 휴대폰 번호 가독성 처리 (정규표현식 활용)
                // 숫자를 3자리-4자리-4자리 조각으로 나누어 하이픈을 끼워넣습니다.
                const formattedPhone = phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');

                li.textContent = formattedPhone; // 화면에는 010-1234-5678 로 표시
                li.style.cursor = "pointer";

                li.onclick = () => {
                    console.log(phone + " 선택됨"); // 로그는 원본 데이터 유지

                    // 2. 선택 함수 호출 시
                    // 만약 선택된 값도 하이픈이 필요하다면 formattedPhone을,
                    // 숫자만 필요하다면 phone을 인자로 넘기면 됩니다.
                    selectPhoneNum(phone);
                };

                planList.appendChild(li);
            });
        }
    }
}

// 2. 드롭다운에서 번호를 클릭했을 때 선택한 번호로 텍스트 변경 
async function selectPhoneNum(phone) {
    try {
        // 서버에 선택한 전화번호를 보냄
        const resp = await fetch(`/mypage/getPlanByPhone?phone=${phone}`, {
            method: 'GET'
        });

        if (resp.ok) {
            const data = await resp.json(); // FixedInfoVo 객체가 넘어옴

            // 1. 현재 화면에 표시된 요금제 이름 변경
            document.getElementById("currentPlanName").innerHTML = `
                ${data.planName} <i class="fas fa-caret-down" style="color: #666;"></i>
            `;

            // 2. 가입 날짜, 결제 정보 등도 화면에 있다면 ID로 찾아서 변경해야 함!
            // 예시 (HTML에 해당 ID들이 있다고 가정):
            if (document.getElementById("createdAt")) {
                document.getElementById("createdAt").innerText = data.createdAt;
            }
            if (document.getElementById("accountNumber")) {
                document.getElementById("accountNumber").innerText = data.accountNumber;
            }
            if (document.getElementById("paymentName")) {
                document.getElementById("paymentName").innerText = data.paymentName;
            }
            if (document.getElementById("accountNumber")) {
                document.getElementById("accountNumber").innerText = data.accountNumber;
            }
            location.reload();


            // 3. 드롭다운 닫기
            document.getElementById("planList").classList.remove("show");

            console.log("요금제 정보 업데이트 완료:", data);

        }
    } catch (error) {
        console.error("요금제 정보를 가져오는데 실패했습니다.", error);
    }
}

// 3. 요금제 해지 기능 (기존 유지)
async function planCancle() {
    const phoneEl = document.querySelector("#printPhoneNum");
    if (!phoneEl) return;

    const phone = phoneEl.innerText.trim().replace(/[^0-9]/g, "");

    // 번호가 없는 상태에서 해지를 누를 경우 방어 로직 추가
    if (!phone || phone === "등록된 번호 없음") {
        alert("해지할 번호가 없습니다.");
        return;
    }

    if (!confirm(phone + " 번호의 요금제를 정말 해지하시겠습니까?")) {
        return;
    }

    try {
        const resp = await fetch(`/planConfirm/mypage/delete`, {  //x 알아보기
            method: "delete",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({phone})
        });

        if (!resp.ok) {
            alert("해지 실패.");
        } else {
            const data = await resp.json();
            if (data.result == 1) {
                alert("정상적으로 해지신청이 완료되었습니다.");
                location.reload();
            } else if (data.result === 'pending') {
                // 중복신청 방어
                alert("이미 처리 대기 중인 변경 또는 해지 신청이 있습니다.\n기존 신청이 완료된 후 다시 시도해주세요.");
            } else {
                alert("해지 처리 결과가 올바르지 않습니다.");
            }
        }
    } catch (error) {
        console.error("해지 중 오류 발생:", error);
        alert("서버와 통신 중 문제가 발생했습니다.");
    }
}

async function movePageChangeAccount(phone) {
    location.href = `/planConfirm/mypage/changeAccount?phone=${phone}`;
}


function member_phone() {
    const phoneSpan = document.querySelector("#printPhoneNum");

    const a = phoneSpan.innerText.trim();

    const formatted = formatPhone(a);

    phoneSpan.innerText = formatted;
}

//우선순위 하 토글시 보이는 전화번호 하이픈 추가기능
// function member_phone_toggle() {
//   const phoneSpans = document.querySelectorAll(".drop-num");

//   const a = phoneSpan.innerText.trim();

//   const formatted = formatPhone(a);

//   phoneSpan.innerText = formatted;
// }

// 전화번호 문자열을 "010-1234-1234" 식으로 변경
function formatPhone(phone) {
    const num = phone.replace(/\D/g, "");

    if (num.length === 11) {
        return num.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
    }
    return phone;
}

function member_accountNumber() {
    const accountNumberSpan = document.querySelector("#member_accountNumber_id");

    const a = accountNumberSpan.innerText.trim();

    const formatted = formatCardNumber(a);

    accountNumberSpan.innerText = formatted;
}


// 카드 번호 문자열을 "1234-XXXX-XXXX-4321" 식으로 변경(15자도 가능)
function formatCardNumber(card) {
    const num = String(card).replace(/\D/g, "");
    if (num.length === 16) {
        return num.replace(/(\d{4})(\d{4})(\d{4})(\d{4})/, "$1-XXXX-XXXX-$4");
    }
    if (num.length === 15) {
        return num.replace(/(\d{4})(\d{6})(\d{5})/, "$1-XXXXXX-$3");
    }
    return card;
}

function member_at() {
    const dateDiv = document.querySelector(".member_at_class");

    const a = dateDiv.innerText.trim();

    const formatted = formatDate();

    dateDiv.innerText = formatted;
}


// 날짜 문자열을 "2026년 03월 13일" 로 변경, 6자리는 "2026년 03월" 로 변경
function formatDate(date) {
    const str = String(date).trim();

    // yyyyMM
    if (/^\d{6}$/.test(str)) {
        const year = str.substring(0, 4);
        const month = str.substring(4, 6);
        return `${year}년 ${month}월`;
    }

    // yyyy-MM-dd HH:mm:ss 또는 yyyy-MM-dd
    const match = str.match(/^(\d{4})-(\d{2})-(\d{2})/);
    if (match) {
        const year = match[1];
        const month = match[2];
        const day = match[3];
        return `${year}년 ${month}월 ${day}일`;
    }

    return "-";
}


//부가서비스 받아오기?
async function selectServiceCount() {
    try {
        const resp = await fetch(`/serviceContract/selectCount`, {
            method: "post",
            headers: {
                'Content-Type': 'application/json'
            },
        });

        if (resp.ok) {
            const data = await resp.json();

            console.log("유료 서비스 개수:", data.serviceCount);
            console.log("무료 서비스 개수:", data.freeServiceCount);

            // 🚨 핵심: HTML에서 아이디로 요소를 찾아서 서버에서 온 데이터(숫자)를 넣어줍니다!
            document.querySelector("#paidServiceCount").innerHTML = data.serviceCount + " 건";
            document.querySelector("#freeServiceCount").innerHTML = data.freeServiceCount + " 건";
            // document.getElementById("paidServiceCount").innerText = 
            // document.getElementById("freeServiceCount").innerText = 
        }
    } catch (error) {
        console.error("부가서비스 개수를 가져오는 중 오류 발생:", error);
    }
}

// async function selectServiceCount() {
//   const resp = await fetch(`/serviceContract/selectCount`, {
//     method: "post",
//     headers: {
//       'Content-Type': 'application/json'
//     },
//   });
//
//   const data = await resp.json();
//   data.serviceCount;
//   data.freeServiceCount;
//   console.log(data.serviceCount);
//
// }

let formatted = "-";

if (endday && endday.length === 8) {
    formatted =
        endday.substring(0, 4) + "-" +
        endday.substring(4, 6) + "-" +
        endday.substring(6, 8);
}

document.querySelector("#endDate").innerHTML = formatted;

// 3. 요금제 해지 기능 (기존 유지)
async function editPlan() {

    try {
        const resp = await fetch(`/planConfirm/mypage/pending`);

        if (!resp.ok) {
            alert("변경 실패.");
        } else {
            const data = await resp.json();
            if (data.result == 0) {
                location.href = "/plan/mypage/editList"
            } else if (data.result === 'pending') {
                // 중복신청 방어
                alert("이미 처리 대기 중인 변경 또는 해지 신청이 있습니다.\n기존 신청이 완료된 후 다시 시도해주세요.");
            } else {
                alert("변경 처리 결과가 올바르지 않습니다.");
            }
        }
    } catch (error) {
        console.error("해지 중 오류 발생:", error);
        alert("서버와 통신 중 문제가 발생했습니다.");
    }
}

member_phone();
// member_phone_toggle();
member_accountNumber();
// member_at();
selectServiceCount();
