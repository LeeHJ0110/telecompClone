document.addEventListener("DOMContentLoaded", async () => {
    // --- 1. 기존 데이터 로드 (현재 요금제용) ---
    const createdAt = document.querySelector("#hiddenCreatedAt").value;
    const term = document.querySelector("#hiddenTerm").value;
    const phone = document.querySelector("#hiddenPhone").value;

    if (createdAt && term) {
        renderExpiry(createdAt, term);
    }

    const currentPlanNo = document.querySelector("#hiddenCurrentPlanNo").value;
    if (currentPlanNo) {
        try {
            const resp = await fetch(`/planConfirm/plan/details?planNo=${currentPlanNo}`);
            if (resp.ok) {
                const currentVo = await resp.json();
                // 현재 요금제 카드(current 클래스 내부)에 데이터 삽입
                document.querySelector(".current .current-data").innerText = currentVo.planData;
                document.querySelector(".current .current-voice").innerText = currentVo.planVoice;
                document.querySelector(".current .current-price").innerText = 
                    Number(currentVo.planPrice).toLocaleString() + " 원";
            }
        } catch (err) {
            console.error("현재 요금제 상세조회 실패:", err);
        }
    }

    // --- 2. 로컬스토리지에서 planNum 꺼내기 (변경할 요금제용) ---
    const planNo = localStorage.getItem("editPlanNum");
    
    if (!planNo) {
        alert("선택된 요금제 정보가 없습니다. 요금제 리스트로 돌아갑니다.");
        location.href = "/plan/mypage/editList";
        return;
    }

    // --- 3. DB에서 해당 요금제 정보 가져오기 (AJAX) ---
    try {
        const resp = await fetch(`/planConfirm/plan/details?planNo=${planNo}`);
        if (resp.ok) {
            const planVo = await resp.json(); // 서버에서 JSON으로 보내줘야 함
            renderTargetPlan(planVo);
        } else {
            console.error("요금제 정보를 불러오지 못했습니다.");
        }
    } catch (err) {
        console.error("통신 오류:", err);
    }

    // --- 4. 최종 선택 버튼 이벤트 ---
    const btn = document.querySelector("#confirmChangeBtn");
    if (btn) {
        btn.addEventListener("click", async () => {
            if (!confirm("정말로 요금제를 변경하시겠습니까?")) return;

            try {
                const resp = await fetch("/planConfirm/mypage/update", {
                    method: "put",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ planNo, phone })
                });

                if (resp.ok) {
                const result = await resp.json(); // 2. json으로 파싱
                
                if (result.data === "1") {
                    alert("요금제 변경 신청이 완료되었습니다.");
                    localStorage.removeItem("editPlanNum"); // 사용 후 삭제
                    location.href = "/home";
                } else if(result.data === 'pending'){
                    alert("이미 처리 대기 중인 변경 또는 해지 신청이 있습니다.\n기존 신청이 처리된 후 다시 시도해 주세요.");
                } else {
                    alert("변경 처리에 실패했습니다. (결과값: " + result.data + ")");
                }
            } else {
                alert("서버 응답 오류: ");
            }
            } catch (err) { alert("서버 통신 오류"); }
        });
    }
});

// 화면에 변경할 요금제 데이터 뿌리기
function renderTargetPlan(vo) {
    document.querySelector(".target h3").innerHTML = `${vo.planName} <span class="badge-change" onclick="location.href='/plan/mypage/editList'">요금제 바꾸기</span>`;
    document.querySelector(".plan-data-value").innerText = vo.planData || "정보 없음";
    document.querySelector(".plan-voice-value").innerText = vo.planVoice || "정보 없음";
    document.querySelector(".plan-price-value").innerText = Number(vo.planPrice).toLocaleString() + " 원";
}

function renderExpiry(start, termStr) {
    const startDate = new Date(start);
    startDate.setMonth(startDate.getMonth() + parseInt(termStr));
    const y = startDate.getFullYear();
    const m = String(startDate.getMonth() + 1).padStart(2, '0');
    const d = String(startDate.getDate()).padStart(2, '0');
    document.querySelector(".expiry-date-display").innerText = `${y} - ${m} - ${d}`;
}