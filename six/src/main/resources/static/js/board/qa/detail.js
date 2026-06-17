async function loadBoardVo() {
    const qaNo = location.pathname.split("/").pop();
    const resp = await fetch(`/board/qa/${qaNo}`);

    if (!resp.ok) {
        throw new Error("loadBoardVo fail ...");
    }

    const data = await resp.json();
    console.log(data);

    const vo = data.vo;
    console.log(vo);

    const prev = data.prev;
    const next = data.next;

    document.querySelector("div[name=title]").innerText = vo.title ?? "";
    document.querySelector("#createdAt").innerHTML = vo.createdAt ? formatDate(vo.createdAt) : "";
    document.querySelector("#notice-content").innerHTML = vo.content ?? "";

    if (prev) {
        const before = document.querySelector("#beforelist");
        before.href = "/board/qa/detail/" + prev.no;
        before.innerText = prev.title ?? "";
    }

    if (next) {
        const after = document.querySelector("#afterlist");
        after.href = "/board/qa/detail/" + next.no;
        after.innerText = next.title ?? "";
    }
}

loadBoardVo().catch(error => {
    console.log(error);
    alert("게시글 상세조회 실패 ...");
});

function moveToEditPage() {
    const no = location.pathname.split("/").pop();
    location.href = `/board/qa/edit/${no}`;
}

async function insertReply() {
    try {
        const content = document.querySelector("textarea[name=reply-content]").value;
        const qaNo = location.pathname.split("/").pop();

        const resp = await fetch(`/board/qa/reply`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ content, qaNo }),
        });

        if (!resp.ok) {
            throw new Error("reply insert error");
        }

        const data = await resp.json();

        if (data.result != 1) {
            alert("fail ... ");
            return;
        }

        alert("댓글 등록 성공 !");
        loadReply();

    } catch (error) {
        console.log(error);
    }
}

async function loadReply() {
    const qaNo = location.pathname.split("/").pop();

    const resp = await fetch(`/board/qa/reply?qaNo=${qaNo}`);

    if (!resp.ok) {
        throw new Error("laod reply error");
    }

    const voList = await resp.json();
    let str = "";

    for (const vo of voList) {
        str += `
        <div class="reply-item">
            <div class="reply-header">
                <span class="reply-writer admin">안녕하십니까 문의 답변드릴 ${vo.employeeName ?? ""}입니다</span>
                <span class="reply-date">${vo.createdAt ? formatDate(vo.createdAt) : ""}</span>
            </div>

            <div class="reply-content">
                ${vo.content ?? ""}
            </div>
        </div>
        `;
    }

    const replyListArea = document.querySelector("#reply-list-area");
    replyListArea.innerHTML = str;
}

loadReply().catch(error => {
    console.log(error);
});