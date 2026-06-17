// 전역 변수로 현재 조회 중인 년, 월 관리
let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth() + 1; // JS는 0부터 시작하므로 +1

document.addEventListener("DOMContentLoaded", () => {
    // 1. 초기 날짜 표시 및 데이터 로드
    updateDateDisplay();
    loadUsageData();
});

// 날짜 표시 업데이트 함수
function updateDateDisplay() {
    const dateText = document.querySelector('.date-text');
    // 월이 10보다 작으면 앞에 0을 붙여서 '02월' 형태로 표시
    const formattedMonth = currentMonth < 10 ? `0${currentMonth}` : currentMonth;
    dateText.innerText = `${currentYear}년 ${formattedMonth}월`;
}

// 이전 달 버튼 클릭
function prevMonth() {
    currentMonth--;
    if (currentMonth < 1) {
        currentMonth = 12;
        currentYear--;
    }
    updateDateDisplay();
    loadUsageData();
}

// 다음 달 버튼 클릭
function nextMonth() {
    currentMonth++;
    if (currentMonth > 12) {
        currentMonth = 1;
        currentYear++;
    }
    updateDateDisplay();
    loadUsageData();
}

// 2. 서버에서 데이터 가져오기 (AJAX)
async function loadUsageData() {
    const monthStr = currentMonth < 10 ? `0${currentMonth}` : currentMonth;
    const targetDate = `${currentYear}${monthStr}`; // 예: "202602"

    try {
        // 서버의 REST API 경로에 맞게 수정 필요
        const resp = await fetch(`/planContract/mypage/list?usedMonth=${targetDate}`);
        if(!resp.ok) throw new Error("데이터 로드 실패");

        const data = await resp.json(); // List<UsageVo> 형태를 기대
        renderTable(data.list);
    } catch (err) {
        console.error(err);
        // 에러 시 빈 화면 처리나 알림
        document.querySelector('#usageListBody').innerHTML = `<tr><td colspan="5">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>`;
    }
}

// 테이블 그리기 함수 (문자열 통째로 넣기)
function renderTable(list) {
    const tbody = document.querySelector('#usageListBody');
    
    // 1. 데이터가 없을 경우 처리
    if (!list || list.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" style="padding:100px 0; color:#999;">해당 월의 사용 내역이 없습니다.</td></tr>`;
        return;
    }

    // 2. 행 데이터를 담을 변수 선언
    let tableRows = "";

    // 3. for문으로 <tr>...</tr> 문자열 생성
    for (let i = 0; i < list.length; i++) {
        const vo = list[i];
        
        // 문자열 더하기(+=)를 사용해서 <tr> 태그를 통째로 생성
        tableRows += `
            <tr>
                <td class="phone-num">${formatPhone(vo.phone)}</td>
                <td>${Number(vo.planData).toLocaleString()} MB</td>
                <td>${vo.planVoice} 분</td>
                <td>${vo.planSms} 건</td>
                <td class="plan-name">${vo.planName}</td>
            </tr>
        `;
    }

    // 4. 생성된 모든 행을 한 번에 tbody에 주입
    tbody.innerHTML = tableRows;
}