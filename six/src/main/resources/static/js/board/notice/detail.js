async function loadBoardVo() {
    const boardNo = location.pathname.split("/").pop();
    const resp = await fetch(`/board/notice/${boardNo}`);

    if (!resp.ok) {
        throw new Error("loadBoardVo fail ...");
    }

    const data = await resp.json();
    const vo = data.vo;
    const prev = data.prev;
    const next = data.next;

    document.querySelector("div[name=title]").innerText = vo.title ?? "";
    document.querySelector("#createdAt").innerHTML = vo.createdAt ? formatDate(vo.createdAt) : "";
    document.querySelector("#notice-content").innerHTML = vo.content ?? "";

    if (prev) {
        const before = document.querySelector("#beforelist");
        before.href = "/board/notice/detail/" + prev.no;
        before.innerText = prev.title ?? "";
    }

    if (next) {
        const after = document.querySelector("#afterlist");
        after.href = "/board/notice/detail/" + next.no;
        after.innerText = next.title ?? "";
    }
}

loadBoardVo().catch(error => {
    console.log(error);
    alert("게시글 상세조회 실패 ...");
});

function moveToEditPage() {
    const no = location.pathname.split("/").pop();
    location.href = `/board/notice/edit/${no}`;
}