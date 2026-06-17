async function loadBoardVoList() {
    const pno = location.pathname.split("/").pop();

    const resp = await fetch(`/board/qa?currentPage=${pno}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("select board list fail ...");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList;

    setPageArea(pvo);
    renderQaList(voList);
}

function renderQaList(voList) {
    const listTag = document.querySelector("#notice-tbody");
    let str = "";

    if (!voList || voList.length === 0) {
        str = `
            <tr>
                <td colspan="4" class="empty-row">문의 내용이 없습니다.</td>
            </tr>
        `;
        listTag.innerHTML = str;
        return;
    }

    for (const vo of voList) {
        str += `
            <tr onclick="location.href='/board/qa/detail/${vo.no}'" style="cursor:pointer;">
                <td class="news-category">${vo.category ?? ""}</td>
                <td>${vo.title ?? ""}</td>
                <td class="news-date">${vo.createdAt ? formatDate(vo.createdAt) : ""}</td>
                <td class="news-check">${vo.replyCount > 0 ? "완료" : "대기"}</td>
            </tr>
        `;
    }

    listTag.innerHTML = str;
}

function setPageArea(pvo) {
    const pageArea = document.querySelector("#page-area");
    let str = '';

    if (pvo.startPage != 1) {
        str += `<button onclick="location.href='/board/qa/list/${pvo.startPage - 1}'">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; ++i) {
        str += `<button onclick="location.href='/board/qa/list/${i}'">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="location.href='/board/qa/list/${pvo.endPage + 1}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}

async function searchAdminQa() {
    const type = document.querySelector("#searchType")?.value ?? "";
    const keyword = document.querySelector("#searchValue")?.value ?? "";

    const resp = await fetch(
        `/board/qa/search?type=${encodeURIComponent(type)}&keyword=${encodeURIComponent(keyword)}`
    );

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("search fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    renderQaList(voList);
    document.querySelector("#page-area").innerHTML = "";
}

async function searchNotice() {
    const type = document.querySelector("#searchType")?.value ?? "";
    const keyword = document.querySelector("#searchValue")?.value ?? "";

    const resp = await fetch(
        `/board/qa/search?type=${encodeURIComponent(type)}&keyword=${encodeURIComponent(keyword)}`
    );

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("search fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    renderQaList(voList);
    document.querySelector("#page-area").innerHTML = "";
}

window.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(location.search);
    const type = params.get("type");
    const keyword = params.get("keyword");

    const searchTypeEl = document.querySelector("#searchType");
    const searchValueEl = document.querySelector("#searchValue");
    const searchCategoryEl = document.querySelector("#searchCategory");

    if (type && searchTypeEl) {
        searchTypeEl.value = type;
    }

    if (keyword && searchValueEl) {
        searchValueEl.value = keyword;

        if (searchCategoryEl) {
            searchAdminQa().catch(error => {
                console.log(error);
                alert("검색 실패 ...");
            });
        } else {
            searchNotice().catch(error => {
                console.log(error);
                alert("검색 실패 ...");
            });
        }
    } else {
        loadBoardVoList().catch(error => {
            console.log(error);
            alert("로그인 상태를 확인하세요");
            location.href = '/member/login';
        });
    }
});