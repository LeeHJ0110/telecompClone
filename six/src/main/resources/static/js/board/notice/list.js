async function loadBoardVoList() {
    const pno = location.pathname.split("/").pop();

    const resp = await fetch(`/board/notice?currentPage=${pno}`);
    if (!resp.ok) {
        throw new Error("select board list fail ...");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList;

    setPageArea(pvo);

    const tbodyTag = document.querySelector("#notice-tbody");

    let str = "";
    let idx = 0;

    for (const vo of voList) {
        const badge = idx < 3 ? '<span class="news-badge">최신</span>' : '';

        str += `
        <tr>
            <td class="news-category">공지</td>
            <td>
                <a href="/board/notice/detail/${vo.no}">
                    ${badge}${vo.title ?? ""}
                </a>
            </td>
            <td class="news-date">${vo.createdAt ? formatDate(vo.createdAt) : ""}</td>
        </tr>
        `;

        idx++;
    }

    console.log(document.querySelector("main table tbody"));
    tbodyTag.innerHTML = str;
}

loadBoardVoList().catch(error => {
    console.log(error);
    alert("게시글 불러오기 실패 ...");
});

function setPageArea(pvo) {
    const pageArea = document.querySelector("#page-area");
    let str = '';

    if (pvo.startPage != 1) {
        str += `<button onclick="location.href='/board/notice/list/${pvo.startPage - 1}'">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; ++i) {
        str += `<button onclick="location.href='/board/notice/list/${i}'">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="location.href='/board/notice/list/${pvo.endPage + 1}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}

async function searchNotice() {
    const type = document.querySelector("#searchType").value;
    const keyword = document.querySelector("#searchValue").value;

    const resp = await fetch(`/board/notice/search?type=${type}&keyword=${encodeURIComponent(keyword)}`);

    if (!resp.ok) {
        throw new Error("search fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    const tbody = document.querySelector("#notice-tbody");

    let str = "";
    for (const vo of voList) {
        str += `
        <tr>
            <td class="news-category">공지</td>
            <td><a href="/board/notice/detail/${vo.no}">${vo.title ?? ""}</a></td>
            <td class="news-date">${vo.createdAt ? formatDate(vo.createdAt) : ""}</td>
        </tr>
        `;
    }

    tbody.innerHTML = str;
}