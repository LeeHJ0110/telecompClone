async function loadBoardVoList() {
    const pno = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/membermanage?currentPage=${pno}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("select board list fail ...");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList;

    console.log("pvo:", pvo);
    console.log("voList:", voList);
    console.log("voList length:", voList.length);

    setPageArea(pvo);
    renderList(voList);
}

function renderList(voList) {
    const listTag = document.querySelector("#list");

    let str = "";
    let idx = 1;

    for (const vo of voList) {
        str += `
        <div class="row" onclick="goDetail('${vo.no}', ${vo.phone ? `'${vo.phone}'` : 'null'})">
            <span class="no">${idx}</span>
            <span class="name">${vo.name ?? ""}</span>
            <span class="phone">${vo.phone == null ? "요금제 미가입 고객" : formatPhone(vo.phone)}</span>
            <span>
                ${
                    vo.activeYn === "Y"
                    ? "활성화 완료"
                    : vo.activeYn === "N"
                    ? "활성화 대기"
                    : "요금제 미신청"
                }
            </span>
            <span>${vo.createdAt == null ? "" : formatDate(vo.createdAt)}</span>
        </div>
        `;
        idx++;
    }

    listTag.innerHTML = str;
}

async function goDetail(no, phone) {
    try {
        console.log("js no =", no);
        console.log("js phone =", phone);

        const resp = await fetch("/employee/membermanage/select", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                no: no,
                phone: phone
            })
        });

        console.log("POST status =", resp.status);

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("select member fail...");
        }

        location.href = `/employee/membermanage/detail/${no}`;
    } catch (error) {
        console.log(error);
        alert("상세 페이지 이동 실패...");
    }
}

function setPageArea(pvo) {
    const pageArea = document.querySelector("#page-area");
    let str = '';

    if (pvo.startPage != 1) {
        str += `<button onclick="location.href='/employee/membermanage/list/${pvo.startPage - 1}'">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; ++i) {
        str += `<button onclick="location.href='/employee/membermanage/list/${i}'">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="location.href='/employee/membermanage/list/${pvo.endPage + 1}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}

async function searchNotice() {
    const type = document.querySelector("#searchType").value;
    const keyword = document.querySelector("#searchValue").value;

    const resp = await fetch(`/employee/membermanage/search?type=${type}&keyword=${encodeURIComponent(keyword)}`);

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("search fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    renderList(voList);
}

window.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(location.search);
    const type = params.get("type");
    const keyword = params.get("keyword");

    const searchTypeEl = document.querySelector("#searchType");
    const searchValueEl = document.querySelector("#searchValue");

    if (type && searchTypeEl) {
        searchTypeEl.value = type;
    }

    if (keyword && searchValueEl) {
        searchValueEl.value = keyword;
        searchNotice();
    } else {
        loadBoardVoList().catch(error => {
            console.log(error);
            alert("게시글 불러오기 실패 ...");
        });
    }
});