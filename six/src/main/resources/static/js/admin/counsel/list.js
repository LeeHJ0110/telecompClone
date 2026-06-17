let counselVoList = [];

async function loadBoardVoList() {
    const pno = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/counsel?currentPage=${pno}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("select board list fail ...");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList;

    counselVoList = voList;

    setPageArea(pvo);
    renderCounselList(voList);

    // 첫 번째 상담내용 기본 출력
    if (voList.length > 0) {
        showCounselDetail(0);
    } else {
        clearCounselDetail();
    }
}

function renderCounselList(voList) {
    const listTag = document.querySelector("#consultList");
    let str = "";

    for (let i = 0; i < voList.length; i++) {
        const vo = voList[i];

        str += `
            <div class="consult-row ${i === 0 ? "selected" : ""}" onclick="showCounselDetail(${i})">
                <div>${vo.no ?? ""}</div>
                <div>${vo.memberName ?? ""}</div>
                <div>${vo.phone ?? ""}</div>
                <div>${vo.counselDate ?? ""}</div>
                <div>${vo.writerName ?? ""}</div>
            </div>
        `;
    }

    listTag.innerHTML = str;
}

function showCounselDetail(index) {
    const vo = counselVoList[index];
    if (!vo) {
        clearCounselDetail();
        return;
    }

    document.querySelectorAll(".consult-row").forEach(row => {
        row.classList.remove("selected");
    });

    const clickedRow = document.querySelectorAll(".consult-row")[index];
    if (clickedRow) {
        clickedRow.classList.add("selected");
    }

    document.querySelector("#consultContent").innerText = vo.content ?? vo.counselContent ?? "상담 내용이 없습니다.";
}

function clearCounselDetail() {
    const listTag = document.querySelector("#consultList");
    listTag.innerHTML = `<div class="empty-row">조회된 상담내역이 없습니다.</div>`;

    const contentTag = document.querySelector("#consultContent");
    contentTag.innerText = "상담 내용을 선택해주세요.";
}

function setPageArea(pvo) {
    const pageArea = document.querySelector("#page-area");
    let str = '';

    if (pvo.startPage != 1) {
        str += `<button onclick="location.href='/employee/counsel/list/${pvo.startPage - 1}'">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; ++i) {
        str += `<button onclick="location.href='/employee/counsel/list/${i}'">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="location.href='/employee/counsel/list/${pvo.endPage + 1}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}

async function searchCounsel() {
    const keyword = document.querySelector("#searchValue")?.value ?? "";

    const resp = await fetch(`/employee/counsel/search?keyword=${encodeURIComponent(keyword)}`);

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("search fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    counselVoList = voList;
    renderCounselList(voList);

    if (voList.length > 0) {
        showCounselDetail(0);
    } else {
        clearCounselDetail();
    }

    document.querySelector("#page-area").innerHTML = "";
}

window.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(location.search);
    const keyword = params.get("keyword");

    const searchValueEl = document.querySelector("#searchValue");

    if (keyword && searchValueEl) {
        searchValueEl.value = keyword;

        searchCounsel().catch(error => {
            console.log(error);
            alert("검색 실패 ...");
        });
    } else {
        loadBoardVoList().catch(error => {
            console.log(error);
            alert("상담내역 불러오기 실패 ...");
        });
    }
});