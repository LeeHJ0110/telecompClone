async function loadBoardVoList() {
    const pno = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/employeemanage?currentPage=${pno}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("select board list fail ...");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList;

    setPageArea(pvo);

    const listTag = document.querySelector("#list");

    let str = "";
    let idx = 1;

    for (const vo of voList) {
        str += `

        <div class="row" onclick="location.href='/employee/employeemanage/detail/${vo.no}'">
                        <span class="no">${idx}</span>
                        <span class="name">${vo.name}</span>
                        <span class="email">${vo.email}</span>
                        <span class="jobName">${vo.jobName}</span>
                        <span class="agencyName">${vo.agencyName}</span>
        </div>
        `;
        idx++;
    }

    listTag.innerHTML = str;
}

loadBoardVoList().catch(error => {
    console.log(error);
    alert("게시글 불러오기 실패 ...");
});

function setPageArea(pvo) {
    const pageArea = document.querySelector("#page-area");
    let str = '';

    if (pvo.startPage != 1) {
        str += `<button onclick="location.href='/employee/employeemanage/list/${pvo.startPage - 1}'">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; ++i) {
        str += `<button onclick="location.href='/employee/employeemanage/list/${i}'">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="location.href='/employee/employeemanage/list/${pvo.endPage + 1}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}

async function searchNotice() {
    const type = document.querySelector("#searchType").value;
    const keyword = document.querySelector("#searchValue").value;

    const resp = await fetch(`/employee/employeemanage/search?type=${type}&keyword=${keyword}`);

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("search fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    const listTag = document.querySelector("#list");

    let str = "";
    let idx = 1;

    for (const vo of voList) {
        str += `
        <div class="row">
            <span class="no">${idx}</span>
            <span class="name">${vo.name}</span>
            <span class="email">${vo.email}</span>
            <span class="jobName">${vo.jobName}</span>
            <span class="agencyName">${vo.agencyName}</span>
        </div>
        `;
        idx++;
    }

    listTag.innerHTML = str;
}