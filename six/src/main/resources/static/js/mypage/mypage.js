async function loadMyPlans(page) {
    try {
        const response = await fetch(`/mypage/plans?page=${page}`);
        if (!response.ok) throw new Error("데이터를 불러오는데 실패했습니다.");

        const data = await response.json();

        const contentArea = document.querySelector('.info-section');

        let html = `
            <h2 class="sub-section-title">내 요금제 현황</h2>
            <div class="sub-section-content"">
                <table>
                    <tr>
                        <th">가입일</th>
                        <th">요금제명</th>
                        <th">상태</th>
                    </tr>`;

        if (data.planList.length === 0) {
            html += `<tr><td colspan="3";">가입된 요금제가 없습니다.</td></tr>`;
        } else {
            for (let plan of data.planList) {
                html += `
                    <tr style="border-bottom: 1px solid #ddd;">
                        <td">${plan.enrollDate}</td>
                        <td">${plan.planName}</td>
                        <td">${plan.status}</td>
                    </tr>`;
            }
        }
        html += `</table></div>`;

        contentArea.innerHTML = html;

    } catch (error) {
        console.error("에러 발생:", error);
        // alert("요금제 내역을 불러오지 못했습니다.");
    }
}


function member_phone() {
    const phoneSpan = document.querySelector("#member_phone_id");

    const a = phoneSpan.innerText.trim();

    const formatted = formatPhone(a);

    phoneSpan.innerText = formatted;
}

// 전화번호 문자열을 "010-1234-1234" 식으로 변경
function formatPhone(phone) {
    const num = phone.replace(/\D/g, "");

    if (num.length === 11) {
        return num.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
    }
    return phone;
}

function member_resident() {
    const residentSpan = document.querySelector("#member_resident_id");

    const a = residentSpan.innerText.trim();

    const formatted = formatResident(a);

    residentSpan.innerText = formatted;
}

// 주민등록번호 문자열을 "123456-1******"로 변경
function formatResident(rrn) {
    const num = String(rrn).replace(/\D/g, "");

    if (num.length === 13) {
        return num.replace(/(\d{6})(\d{1})(\d{6})/, "$1-$2******");
    }

    return rrn;
}

function myplan_hidden() {

}

function checkPlan() {
    let isPlanEmpty;
    const check = document.querySelector("#fixedInfoVoCheck").value;
    if (check == null) {
        isPlanEmpty = true;
    } else{
        isPlanEmpty = false;
    }

    if (isPlanEmpty) {
        alert("요금제가 없습니다. 신청을 먼저 해주세요.");
        return false;
    }

    return true;
}

// 🚨 추가할 함수: 가입일로부터 오늘까지 몇 년, 몇 개월인지 계산합니다.
function calculateDuration() {
    const statusElement = document.getElementById("member_status_id");
    if (!statusElement) return;

    // JSP에서 숨겨둔 가입일 날짜("YYYY-MM-DD")를 가져옵니다.
    const joinDateStr = statusElement.getAttribute("data-date");

    if (!joinDateStr || joinDateStr.trim() === "") {
        statusElement.innerText = "정보 없음 / 이용중";
        return;
    }

    // 날짜 계산 시작!
    const joinDate = new Date(joinDateStr);
    const today = new Date();

    let years = today.getFullYear() - joinDate.getFullYear();
    let months = today.getMonth() - joinDate.getMonth();

    // 만약 이번 달이 가입한 달보다 이전이라면 (ex: 가입 10월, 현재 5월)
    // 연도를 1년 빼고, 개월 수에 12를 더해줍니다.
    if (months < 0) {
        years--;
        months += 12;
    }

    // 결과 텍스트 조립 (예: 8년 11개월)
    let resultText = "";
    if (years > 0) resultText += years + "년 ";
    if (months > 0) resultText += months + "개월 ";

    // 만약 가입한 지 1달도 안 되었다면 '일(day)' 단위로 보여줍니다.
    if (years === 0 && months === 0) {
        const diffTime = Math.abs(today - joinDate);
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
        resultText += diffDays + "일 ";
    }

    // 화면에 최종 출력!
    statusElement.innerText = resultText + "/ 이용중";
}


document.addEventListener("DOMContentLoaded", function () {
    member_phone();
    member_resident();
    calculateDuration();
});