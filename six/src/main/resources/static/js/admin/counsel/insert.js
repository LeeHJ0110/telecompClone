document.querySelector("#name").addEventListener("input", resetCheckedMember);
document.querySelector("#phone").addEventListener("input", resetCheckedMember);

function resetCheckedMember() {
    document.querySelector("#memberNo").value = "";
    const msgTag = document.querySelector("#member-check-msg");
    msgTag.innerText = "";
    msgTag.classList.remove("success");
    msgTag.classList.remove("fail");
}

async function check() {

    const name = document.querySelector("#name").value.trim();
    const phone = document.querySelector("#phone").value.trim();
    const msgTag = document.querySelector("#member-check-msg");
    const memberNoTag = document.querySelector("#memberNo");

    if (!name) {
        alert("회원 이름을 입력해주세요.");
        document.querySelector("#name").focus();
        return;
    }

    if (!phone) {
        alert("전화번호를 입력해주세요.");
        document.querySelector("#phone").focus();
        return;
    }

    try {
        const resp = await fetch(`/employee/counsel/check?name=${encodeURIComponent(name)}&phone=${encodeURIComponent(phone)}`);

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("member check fail ...");
        }

        const data = await resp.json();
        console.log(data);

        if (data.exists) {
            memberNoTag.value = data.memberNo;
            msgTag.innerText = "등록된 회원입니다.";
            msgTag.classList.remove("fail");
            msgTag.classList.add("success");
        } else {
            memberNoTag.value = "";
            msgTag.innerText = "등록된 회원이 아닙니다.";
            msgTag.classList.remove("success");
            msgTag.classList.add("fail");
        }

    } catch (error) {
        console.log(error);
        alert("회원 조회 실패 ...");
    }
}

async function insert() {

    const name = document.querySelector("#name").value.trim();
    const phone = document.querySelector("#phone").value.trim();
    const content = document.querySelector("#content").value.trim();
    const memberNo = document.querySelector("#memberNo").value;

    if (!name) {
        alert("회원 이름을 입력해주세요.");
        document.querySelector("#name").focus();
        return;
    }

    if (!phone) {
        alert("전화번호를 입력해주세요.");
        document.querySelector("#phone").focus();
        return;
    }

    if (!content) {
        alert("상담 내용을 입력해주세요.");
        document.querySelector("#content").focus();
        return;
    }

    if (!memberNo) {
        alert("먼저 회원조회를 해주세요.");
        return;
    }

    try {
        const resp = await fetch(`/employee/counsel`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name, phone, content, memberNo }),
        });

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("board insert fail ...");
        }

        alert("게시글 작성 성공 !");
        location.href = `/employee/counsel/list/1`;

    } catch (error) {
        console.log(error);
        alert("게시글 작성 실패 ...");
    }
}