window.addEventListener("DOMContentLoaded", () => {

    // 기본 카테고리 (첫 탭)
    const activeTab = document.querySelector(".faq-tabs .active");
    const category = activeTab ? activeTab.dataset.category : "planInsert";

    loadBoardVoList(category);

    // 탭 클릭 이벤트
    document.querySelectorAll(".faq-tabs span").forEach(tab => {
        tab.addEventListener("click", () => {

            const active = document.querySelector(".faq-tabs .active");
            if (active) active.classList.remove("active");

            tab.classList.add("active");

            const category = tab.dataset.category;
            loadBoardVoList(category);
        });
    });
});


// =============================
// 메인 데이터 로딩
// =============================
async function loadBoardVoList(category) {
    try {
        const currentPage = getCurrentPage();

        const resp = await fetch(`/employee/membermanage/confirm-list?currentPage=${currentPage}`);
        if (!resp.ok) {
            throw new Error("select confirm list fail ...");
        }

        const data = await resp.json();
        console.log("response data =", data);

        const pvo = data.pvo;

        let voList = [];

        switch (category) {
            case "planInsert":
                voList = data.planInsertList ?? [];
                break;
            case "planUpdate":
                voList = data.planUpdateList ?? [];
                break;
            case "planDelete":
                voList = data.planDeleteList ?? [];
                break;
            case "serviceInsert":
                voList = data.serviceInsertList ?? [];
                break;
            case "serviceDelete":
                voList = data.serviceDeleteList ?? [];
                break;
            default:
                voList = [];
        }

        renderList(voList, category);
        setPageArea(pvo, category);

    } catch (error) {
        console.error(error);
        alert("게시글 불러오기 실패 ...");
    }
}


// =============================
// 리스트 렌더링
// =============================
function renderList(voList, category) {

    const mainTag = document.querySelector("main");

    if (!voList || voList.length === 0) {
        mainTag.innerHTML = `<div class="empty">조회된 내역이 없습니다.</div>`;
        return;
    }

    const isService =
        category === "serviceInsert" || category === "serviceDelete";

    let str = "";

    // ===== 헤더 =====
    if (isService) {
        str += `
            <div class="confirm-row header">
                <div>이름</div>
                <div>날짜</div>
                <div>서비스이름</div>
                <div>전화번호</div>
            </div>
        `;
    } else {
        str += `
            <div class="confirm-row header plan">
                <div>이름</div>
                <div>날짜</div>
                <div>서비스이름</div>
                <div>전화번호</div>
            </div>
        `;
    }

    // ===== 데이터 =====
    for (const vo of voList) {


        if (isService) {
            str += `
                <div class="confirm-row"
                     onclick="moveDetail('${vo.no}', '${category}')">
                    <div>${vo.name ?? ""}</div>
                    <div>${formatDate(vo.createdAt)}</div>
                    <div>${vo.service ?? "-"}</div>
                    <div><span class="status-badge">${vo.phone}</span></div>
                </div>
            `;
        } else {
            str += `
                <div class="confirm-row plan"
                     onclick="moveDetail('${vo.no}', '${category}')">
                    <div>${vo.name ?? ""}</div>
                    <div>${formatDate(vo.createdAt)}</div>
                    <div>${vo.plan ?? "-"}</div>
                    <div><span class="status-badge">${vo.phone}</span></div>
                </div>
            `;
        }
    }

    mainTag.innerHTML = str;
}


// =============================
// 상태 텍스트 변환
// =============================
function getStatusText(status) {
    if (status === "A") return "승인대기";
    if (status === "Y") return "승인";
    if (status === "N") return "반려";
    return status ?? "";
}


// =============================
// 날짜 포맷
// =============================
function formatDate(dateStr) {
    if (!dateStr) return "";

    const date = new Date(dateStr);

    if (isNaN(date)) return dateStr;

    return date.getFullYear() + "-" +
        String(date.getMonth() + 1).padStart(2, "0") + "-" +
        String(date.getDate()).padStart(2, "0") + " " +
        String(date.getHours()).padStart(2, "0") + ":" +
        String(date.getMinutes()).padStart(2, "0");
}


// =============================
// 페이징
// =============================
function setPageArea(pvo, category) {

    const pageArea = document.querySelector("#page-area");

    if (!pvo) return;

    let str = "";

    if (pvo.startPage > 1) {
        str += `<button onclick="movePage(${pvo.startPage - 1}, '${category}')">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; i++) {
        str += `<button onclick="movePage(${i}, '${category}')">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="movePage(${pvo.endPage + 1}, '${category}')">다음</button>`;
    }

    pageArea.innerHTML = str;
}


// =============================
// 페이지 이동
// =============================
function movePage(page, category) {
    location.href = `/employee/membermanage/confirm-list/${page}?category=${category}`;
}


// =============================
// 현재 페이지 계산
// =============================
function getCurrentPage() {
    const last = location.pathname.split("/").pop();
    const page = Number(last);
    return Number.isNaN(page) ? 1 : page;
}

async function moveDetail(no, category) {
    try {
        const resp = await fetch(`/employee/membermanage/confirm-list/select-one?no=${no}&category=${category}`, {
            method: "POST"
        });

        if (!resp.ok) {
            throw new Error("select one fail");
        }

        let moveUrl = "";

        switch (category) {
            case "planInsert":
                moveUrl = "/confirm/plan?selectTable=insert";
                break;
            case "planUpdate":
                moveUrl = "/confirm/plan?selectTable=update";
                break;
            case "planDelete":
                moveUrl = "/confirm/plan?selectTable=delete";
                break;
            case "serviceInsert":
                moveUrl = "/confirm/service";
                break;
            case "serviceDelete":
                moveUrl = "/confirm/service?selectTable=delete";
                break;
            default:
                throw new Error("invalid category");
        }

        location.href = moveUrl;

    } catch (error) {
        console.error(error);
        alert("상세 조회 이동 실패");
    }
}