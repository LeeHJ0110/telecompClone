//모달 열기 함수
function openPaymentModal() {
    const modal = document.querySelector('#paymentModal');
    if (modal) {modal.style.display = 'flex';}
}


//모달 닫기 함수
function closePaymentModal() {
    const modal = document.querySelector('#paymentModal');
    const numInput = document.querySelector('#modal-pay-num');
    const typeSelect = document.querySelector('#modal-pay-type');
    if (modal) {
        modal.style.display = 'none';
        // [추가] 닫을 때 입력값 초기화
        if (numInput) numInput.value = ''; 
        if (typeSelect) typeSelect.value = '';
    }
}

//결제 정보 반영 함수
function savePaymentInfo() {
    const typeSelect = document.querySelector('#modal-pay-type');
    const numInput = document.querySelector('#modal-pay-num');
    const displayArea = document.querySelector('#payment-display-area');

    // Hidden Fields (서버 전송용)
    const hiddenNo = document.querySelector('#hidden-card-no'); // 실제 서버 전송용 NO 
    const hiddenNum = document.querySelector('#hidden-card-number');

    const no = typeSelect.value; // option의 value값 (NO)
    const name = typeSelect.options[typeSelect.selectedIndex].text; // 선택된 옵션의 글자 (NAME)
    const num = numInput.value;

    if (!no || !num) {
        alert('결제 수단과 번호를 정확히 입력해 주세요.');
        return;
    }

    // 카드/계좌 번호 길이 체크 (15자 또는 16자만 허용)
    if (num.length !== 15 && num.length !== 16) {
        alert('카드(계좌) 번호는 15자리 또는 16자리여야 합니다.\n현재 입력된 글자 수: ' + num.length + '자');
        numInput.focus(); // 입력창으로 포커스 이동
        return; // 함수 종료 (반영되지 않음)
    }

    //마스킹처리
    const maskedNum = formatCardNumber(num);

    // 화면 UI 업데이트 (변경 버튼 포함)
    displayArea.innerHTML = `
        <div class="info-display" style="display: flex; align-items: center;">
            <strong style="color: #FF7E41; font-size: 16px;">[${name}]</strong>
            <span style="margin-left: 8px; font-weight: bold;">${maskedNum}</span>
            <button type="button" class="btn-reg-mini" style="margin-left: 15px;" onclick="openPaymentModal()">변경</button>
        </div>
    `;

    // 서버로 보낼 Hidden Input에 값 저장
    hiddenNo.value = no;
    hiddenNum.value = num;

    // 모달 닫기
    closePaymentModal();
}

// 카드 번호 문자열을 "1234-XXXX-XXXX-4321" 식으로 변경(15자도 가능)
function formatCardNumber(card){
    const num = String(card).replace(/\D/g,"");
    if(num.length === 16){
        return num.replace(/(\d{4})(\d{4})(\d{4})(\d{4})/, "$1-XXXX-XXXX-$4");
    }
    if(num.length === 15){
        return num.replace(/(\d{4})(\d{6})(\d{5})/, "$1-XXXXXX-$3");
    }
    return card;
}



//랜덤번호 생성
async function getNewPhoneNumber() {
    const lastDigitInput = document.querySelector('#user-last-digit');
    const lastDigit = lastDigitInput.value;

    // 유효성 검사: 4자리 숫자인지 확인
    if (!/^\d{4}$/.test(lastDigit)) {
        alert("원하시는 뒷자리 숫자 4자리를 입력해주세요.");
        lastDigitInput.focus();
        return;
    }

    try {
        const resp = await fetch(`/planConfirm/generate-phone?lastDigit=${lastDigit}`);
        if (!resp.ok) {throw new Error("번호 생성 실패");}

        const newPhone = await resp.text(); // 예: "01012345678"

        // 1. 전송용 hidden input에 저장 (숫자만)
        document.querySelector('#phone-raw').value = newPhone;

        // 2. 화면 표시 (하이픈 추가: 010-1234-5678)
        const formatted = newPhone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        document.querySelector('#phone-display').innerText = formatted;

    } catch (err) {
        console.error(err);
        alert("번호 생성 중 오류가 발생했습니다.");
    }
}

//전송
async function submitJoin() {
    const paymentNo = document.querySelector('#hidden-card-no').value; // 결제사 번호(1, 2...)
    const accountNumber = document.querySelector('#hidden-card-number').value; // 계좌/카드번호
    const phoneNumber = document.querySelector('#phone-raw').value; // 생성된 전화번호
    const planName = document.querySelector('#plan-name').innerText;

    const planNo = localStorage.getItem('planNo');
    const mainContractNo = localStorage.getItem('userMainContractNum');
    const term = document.querySelector('#hidden-term').value;

    if (!accountNumber) {
        alert('결제 정보 등록이 필요합니다.');
        return false;
    }
    if (!phoneNumber) {
        alert('전화번호 생성이 필요합니다.');
        return;
    }
 
    // 최종 확인 알림 (선택 사항)
    if(!confirm('정말로 신청하시겠습니까?')){
        return;
    }

    const requestData = {
        phone : phoneNumber,
        paymentNo,  // FIXED_INFO 테이블의 ACCOUNT_NO 컬럼 매칭
        accountNumber, 
        planName,    // 가입할 요금제 식별용
        planNo,
        mainContractNo,
        term
        
    };

    try{
        //// 추가로 수정해야함 ~~
        const resp = await fetch(`/planConfirm/mypage/join`, {
            method : 'POST' ,
            headers : {
                'Content-Type' : 'application/json'
            } ,
            body : JSON.stringify(requestData)
        });

        if(!resp.ok){
            throw new Error("요금제 신규 가입 실패...")
        }

        const data = await resp.json();
        if(data.result != 1){
            throw new Error("요금제 신규 가입 실패 ...")
        }

        alert("요금제 신규 가입 신청 성공 !")
        localStorage.removeItem("planNo");
        localStorage.removeItem("userMainContractNum");
        location.href='/home'; // 우선 홈으로 보내기

    }catch(err){
        console.log(err);
        localStorage.removeItem("planNo");
        localStorage.removeItem("userMainContractNum");
        alert('요금제 신규 가입 중 실패  확인필요 ~~')
    }

}

document.addEventListener("DOMContentLoaded", async() => {

    // 로컬스토리지에서 PK 값들 꺼내기
    const planNo = localStorage.getItem('planNo');
    const userMainContractNum = localStorage.getItem('userMainContractNum'); // 약정 PK

    if (planNo && userMainContractNum) {
        await loadStoredData(planNo, userMainContractNum);
    }

    //주민번호 갖고올 시 마스킹처리
    const rawInput = document.querySelector('#resident-raw');
    const displaySpan = document.querySelector('#resident-display');

    if (rawInput && displaySpan) {
        // 모든 공백과 하이픈을 제거하고 순수 숫자만 추출 (예: 1812253123456)
        const pureNumbers = rawInput.value.replace(/[^0-9]/g, '');
        
        if (pureNumbers.length >= 7) {
            // 1. 앞 6자리 추출
            const front = pureNumbers.substring(0, 6);
            // 2. 7번째 성별 숫자 추출 (1, 2, 3, 4 등)
            const genderDigit = pureNumbers.substring(6, 7);
            
            // 3. 화면에 하이픈을 포함하여 조합
            displaySpan.innerText = `${front}-${genderDigit}******`;
        } else {
            // 데이터가 7자리 미만으로 불완전할 경우 처리
            displaySpan.innerText = rawInput.value;
        }
    }

    // 서버에서 PK로 상세 정보를 가져오는 함수
    async function loadStoredData(planNo, userMainContractNum) {
        try {
            // 서버의 조회 엔드포인트로 전송 (예시 주소: /plan/details)
            const resp = await fetch(`/planConfirm/plan/details?planNo=${planNo}&mainContractNo=${userMainContractNum}`);
            
            if (!resp.ok) throw new Error("데이터 조회 실패");

            const data = await resp.json(); // { planName: "무제한 요금제", term: "24" }

            // 화면에 반영
            document.querySelector('#plan-name').innerText = data.planName;
            document.querySelector('#term-display').innerText = `${data.term}개월`;

            // 전송용 hidden input에도 저장 (필요시)
            if(document.querySelector('#hidden-term')) {
                document.querySelector('#hidden-term').value = data.term;
            }

        } catch (err) {
            console.error("로컬스토리지 데이터 로드 중 오류:", err);
            alert("선택하신 요금제 정보를 불러올 수 없습니다.");
        }
    }

    // 화면에 오늘 날짜 처리
    const createdAtSpan = document.querySelector('#createdAt');
    if (createdAtSpan) {
        const now = new Date();
        const y = now.getFullYear();
        const m = String(now.getMonth() + 1).padStart(2, '0');
        const d = String(now.getDate()).padStart(2, '0');
        
        createdAtSpan.innerText = `${y}년 ${m}월 ${d}일`;
    }

});

// 숫자 전용 입력 제어 (하이픈 제거)
document.addEventListener('input', (e) => {
    if (e.target.id === 'modal-pay-num') {
        // 숫자가 아닌 모든 문자를 실시간 제거
        e.target.value = e.target.value.replace(/[^0-9]/g, '');
    }
});

// 배경 클릭 시 모달 닫기 (사용자 편의)
window.onclick = function(event) {
    const modal = document.querySelector('#paymentModal');
    if (event.target == modal) {
        closePaymentModal();
    }
}