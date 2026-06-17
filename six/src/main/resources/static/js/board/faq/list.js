loadBoardVoList("서비스문의").catch(error => {
    console.log(error);
    alert("게시글 불러오기 실패 ...");
});

async function loadBoardVoList(category) {
    const pno = location.pathname.split("/").pop();

    const resp = await fetch(`/board/faq?currentPage=${pno}&category=${encodeURIComponent(category)}`);
    if (!resp.ok) {
        throw new Error("select faq list fail ...");
    }

    const data = await resp.json();
    const pvo = data.pvo;
    const voList = data.voList;

    setPageArea(pvo, category);

    const mainTag = document.querySelector("main");

    let str = "";

    for (const vo of voList) {
        str += `
            <div class="faq-item">
                <div class="faq-question">
                    <div>[${vo.category ?? ""}] ${vo.question ?? ""}</div>
                    <div class="faq-date">${vo.createdAt ? formatDate(vo.createdAt) : ""} ▼</div>
                </div>
                <div class="faq-answer">
                    ${vo.answer ?? ""}
                    ${isAdmin ? `
                        <button onclick="location.href='/board/faq/edit/${vo.no}'">
                            수정
                        </button>
                    ` : ``}
                </div>
            </div>
        `;
    }

    mainTag.innerHTML = str;

    document.querySelectorAll(".faq-question").forEach(item => {
        item.addEventListener("click", () => {
            const answer = item.nextElementSibling;
            const isOpen = answer.style.display === "block";

            document.querySelectorAll(".faq-answer").forEach(a => {
                a.style.display = "none";
            });

            answer.style.display = isOpen ? "none" : "block";
        });
    });
}

document.querySelectorAll(".faq-tabs span").forEach(tab => {
    tab.addEventListener("click", () => {
        document.querySelector(".faq-tabs .active").classList.remove("active");
        tab.classList.add("active");

        const category = tab.dataset.category;
        loadBoardVoList(category);
    });
});

function setPageArea(pvo, category) {
    const pageArea = document.querySelector("#page-area");
    let str = '';

    if (pvo.startPage != 1) {
        str += `<button onclick="location.href='/board/faq/list/${pvo.startPage - 1}?category=${encodeURIComponent(category)}'">이전</button>`;
    }

    for (let i = pvo.startPage; i <= pvo.endPage; ++i) {
        str += `<button onclick="location.href='/board/faq/list/${i}?category=${encodeURIComponent(category)}'">${i}</button>`;
    }

    if (pvo.endPage < pvo.maxPage) {
        str += `<button onclick="location.href='/board/faq/list/${pvo.endPage + 1}?category=${encodeURIComponent(category)}'">다음</button>`;
    }

    pageArea.innerHTML = str;
}