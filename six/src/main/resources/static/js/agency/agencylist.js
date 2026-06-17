async function loadAgencyList() {
    const pno = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/employeemanage/admin/store?currentPage=${pno}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("agency list load fail ...");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList;

    console.log("pvo =", pvo);
    console.log("voList =", voList);

    renderList(voList);
    setPageArea(pvo);
}

function renderList(voList) {
    const listTag = document.querySelector("#list");

    let str = "";
    let idx = 1;

    if (!voList || voList.length === 0) {
        str = `<div class="empty">대리점 정보가 없습니다.</div>`;
        listTag.innerHTML = str;
        return;
    }

    for (const vo of voList) {
        str += `
        <div class="row" onclick="location.href='/employee/employeemanage/admin/store/detail/${vo.no}'">
            <span class="no">${idx}</span>
            <span class="name">${vo.name ?? ""}</span>
            <span class="manager">${vo.managerName ?? "미지정"}</span>
            <span class="phone">${vo.phone ?? "-"}</span>
            <span class="address">${vo.address ?? "-"}</span>
        </div>
        `;
        idx++;
    }

    listTag.innerHTML = str;
}

function setPageArea(pvo) {
    const pageArea = document.querySelector("#page-area");
    let str = "";

    if (pvo.startPage != 1) {
        str += `<button onclick="location.href='/employee/employeemanage/admin/store/list/${pvo.startPage - 1}'">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; ++i) {
        str += `<button onclick="location.href='/employee/employeemanage/admin/store/list/${i}'">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="location.href='/employee/employeemanage/admin/store/list/${pvo.endPage + 1}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}

async function searchNotice() {
    const type = document.querySelector("#searchType").value;
    const keyword = document.querySelector("#searchValue").value;

    const resp = await fetch(
        `/employee/employeemanage/admin/store/search?type=${type}&keyword=${encodeURIComponent(keyword)}`
    );

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("search fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    renderList(voList);
    document.querySelector("#page-area").innerHTML = "";
}

window.addEventListener("DOMContentLoaded", () => {
    loadAgencyList().catch(error => {
        console.log(error);
        alert("대리점 목록 불러오기 실패...");
    });
});