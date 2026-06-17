// 모달 열기
function openPaymentModal() {
    const modal = document.querySelector('#paymentModal');
    if (modal) modal.style.display = 'flex';
}

// 모달 닫기
function closePaymentModal() {
    const modal = document.querySelector('#paymentModal');
    if (modal) {
        modal.style.display = 'none';
        // 입력 필드 초기화 (기록 삭제)
        document.querySelector('#modal-pay-type').value = '';
        document.querySelector('#modal-pay-num').value = '';
    }
}

// 정보 반영 (화면 업데이트)
async function savePaymentInfo() {
    const typeSelect = document.querySelector('#modal-pay-type');
    const numInput = document.querySelector('#modal-pay-num');
    
    // 화면상의 텍스트 요소들
    const cardNameDisplay = document.querySelector('.card-name');
    const cardNumberDisplay = document.querySelector('.card-number');

    const no = typeSelect.value;
    const name = typeSelect.options[typeSelect.selectedIndex].text;
    const num = numInput.value;

    //주소에 있는 phone 번호 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const currentPhone = urlParams.get('phone');
    if (!currentPhone) {
        alert("핸드폰 번호 정보가 없습니다.");
        return;
    }

    if (!no || !num) {
        alert('결제 수단과 번호를 정확히 입력해 주세요.');
        return;
    }

    if (num.length !== 15 && num.length !== 16) {
        alert('번호는 15자리 또는 16자리여야 합니다.');
        return;
    }
    

    try{
        const resp = await fetch("/account/updateAccount" , {
            method : 'post',
            headers : {
                'Content-Type' : 'application/json'
            },
            body : JSON.stringify({
                paymentName : name ,
                accountNumber : num ,
                phone : currentPhone
            })
        })

        if(!resp.ok){
            throw new Error("db 업데이트 실패");
        }
        const data = await resp.json();

        if(data.result != '1'){
            alert("결제수단 저장 실패 !")
            throw new Error("결제수단 저장 실패 !");
        }
        
        // 마스킹 처리 및 화면 반영
        const maskedNum = formatCardNumber(num);
        cardNameDisplay.innerText = name; // 선택한 카드사 이름 반영
        cardNumberDisplay.innerText = maskedNum;
    
        alert('정보가 반영되었습니다.');
        closePaymentModal();
    }catch(err){
        console.log(err);
        alert("서버 통신 중 오류");
        throw new Error("서버통신에러");
        
    }

}

// 카드 번호 마스킹 함수
function formatCardNumber(card){
    const num = String(card).replace(/\D/g,"");
    if(num.length === 16){
        return num.replace(/(\d{4})(\d{4})(\d{4})(\d{4})/, "$1 - XXXX - XXXX - $4");
    }
    if(num.length === 15){
        return num.replace(/(\d{4})(\d{6})(\d{5})/, "$1 - XXXXXX - $3");
    }
    return card;
}

// 숫자만 입력되도록 제어
document.addEventListener('input', (e) => {
    if (e.target.id === 'modal-pay-num') {
        e.target.value = e.target.value.replace(/[^0-9]/g, '');
    }
});

// 배경 클릭 시 닫기
window.onclick = function(event) {
    const modal = document.querySelector('#paymentModal');
    if (event.target == modal) closePaymentModal();
}


// 초기 로딩 시 정보 불러오기
document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const currentPhone = urlParams.get('phone');

    if (currentPhone) {
        // 1. 해당 번호의 결제 정보 불러오기
        loadAccountInfo(currentPhone);
        // 2. 멤버가 보유한 전체 번호 목록 불러오기
        loadMemberPhoneList(currentPhone);
    }
});

// 특정 번호의 결제 정보 로드
async function loadAccountInfo(phone) {
    try {
        const resp = await fetch(`/account/mypage/showAccount?phone=${phone}`);
        if(!resp.ok) throw new Error("정보 로드 실패");
        
        const data = await resp.json();
        const account = data.accountVo;

        if(account) {
            document.querySelector('.card-name').innerText = account.paymentName || '등록된 수단 없음';
            document.querySelector('.card-number').innerText = formatCardNumber(account.accountNumber);
        }
    } catch (err) {
        console.error(err);
    }
}

// 멤버의 모든 번호 목록 로드
async function loadMemberPhoneList(currentPhone) {
    try {
        const resp = await fetch(`/account/mypage/getPhoneList`); // 서버에 리스트 요청
        const phoneList = await resp.json(); // ['01012345678', '01045087777', ...]

        const tabContainer = document.querySelector('.phone-tabs');
        tabContainer.innerHTML = ''; // 기존 정적 태그 비우기

        phoneList.forEach(phone => {
            const btn = document.createElement('button');
            btn.className = 'tab-btn';
            // [체크!] 숫자만 남겨서 비교 (하이픈이나 공백 무시)
            const purePhone = phone.replace(/\D/g, "");
            const pureCurrent = currentPhone ? currentPhone.replace(/\D/g, "") : "";
            if (purePhone === pureCurrent) btn.classList.add('active'); // 현재 번호 강조
            
            // 하이픈 추가 포맷팅
            btn.innerText = phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            
            // 클릭 시 URL 변경 및 페이지 이동
            btn.onclick = () => {
                location.href = `/planConfirm/mypage/changeAccount?phone=${phone}`;
            };

            tabContainer.appendChild(btn);
        });
    } catch (err) {
        console.error("번호 목록 로드 실패", err);
    }
}