async function loadBoardVo() {
    const boardNo = location.pathname.split("/").pop();

    const url = `/employee/membermanage/${boardNo}`;
    console.log("request url =", url);

    const resp = await fetch(url);

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("loadBoardVo fail ...");
    }

    const data = await resp.json();
    console.log("response data =", data);

    const vo = data.vo;
    const fixedInfoVo = data.fixedInfoVo;
    const fixedInfoList = data.fixedInfoList;

    if (!vo) {
        alert("회원 정보를 찾을 수 없습니다.");
        return;
    }

    renderMemberInfo(vo);
    renderPhoneSelect(fixedInfoList, fixedInfoVo);
    renderFixedInfo(fixedInfoVo);
    await loadCount();
}

function renderMemberInfo(vo) {
    document.querySelector("#name").value = vo.name ?? "";
    document.querySelector("#resident").value = vo.resident ? formatResident(vo.resident) : "";
    document.querySelector("#address").value = vo.address ?? "";
    document.querySelector("#address2").value = vo.address2 ?? "";
    document.querySelector("#email").value = vo.email ?? "";
    document.querySelector("#memberCreatedAt").value = vo.createdAt ? formatDate(vo.createdAt) : "";
}

function renderPhoneSelect(fixedInfoList, fixedInfoVo) {
    const phoneSelect = document.querySelector("#phoneSelect");
    let str = "";

    if (!fixedInfoList || fixedInfoList.length === 0) {
        str = `<option value="null">요금제 미가입 고객</option>`;
    } else {
        for (let i = 0; i < fixedInfoList.length; i++) {
            const item = fixedInfoList[i];
            let selected = "";

            if (fixedInfoVo && fixedInfoVo.phone === item.phone) {
                selected = "selected";
            } else if (!fixedInfoVo && i === 0) {
                selected = "selected";
            }

            str += `<option value="${item.phone}" ${selected}>${formatPhone(item.phone)}</option>`;
        }
    }

    phoneSelect.innerHTML = str;
}

function renderFixedInfo(fixedInfoVo) {
    document.querySelector("#planName").value = fixedInfoVo?.planName ?? "요금제 미가입";
    document.querySelector("#term").value = fixedInfoVo?.term ?? "";
    document.querySelector("#contractStartDate").value = fixedInfoVo?.createdAt ? formatDate(fixedInfoVo.createdAt) : "";
}

async function loadCount() {
    const no = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/membermanage/count/${no}`);

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("count load fail...");
    }

    const data = await resp.json();
    const vo = data.vo;

    document.querySelector("#addServiceCount").innerText = `부가서비스 신청 내역 ${vo.addServiceCount ?? 0}건`;
    document.querySelector("#qaCount").innerHTML = `
        <span>문의 내역 ${vo.qaCount ?? 0}건</span>
        <span class="arrow">➜</span>
    `;
    document.querySelector("#counselCount").innerText = `상담 내역 ${vo.counselCount ?? 0}건`;
}

async function changePhone() {
    try {
        const no = location.pathname.split("/").pop();
        const phone = document.querySelector("#phoneSelect").value;

        console.log("selected phone =", phone);

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

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("phone select fail...");
        }

        await loadBoardVo();
    } catch (error) {
        console.log(error);
        alert("전화번호 변경 조회 실패...");
    }
}

function moveToEditPage() {
    const no = location.pathname.split("/").pop();
    const phone = document.querySelector("#phoneSelect")?.value ?? "null";
    location.href = `/employee/membermanage/edit/${no}?phone=${encodeURIComponent(phone)}`;
}

async function deleteMember() {
    try {
        const no = location.pathname.split("/").pop();

        if (!confirm("정말 삭제하시겠습니까?")) {
            return;
        }

        const resp = await fetch(`/employee/membermanage`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ no }),
        });

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("delete fail...");
        }

        const data = await resp.json();

        if (data.result != 1) {
            alert("삭제 실패...");
            return;
        }

        alert("삭제 완료");
        location.href = "/employee/membermanage/list/1";

    } catch (error) {
        console.log(error);
        alert("삭제 중 오류 발생");
    }
}

window.addEventListener("DOMContentLoaded", async () => {
    try {
        await loadBoardVo();

        const phoneSelect = document.querySelector("#phoneSelect");
        phoneSelect.addEventListener("change", changePhone);

    } catch (error) {
        console.log(error);
        alert("상세조회 실패...");
    }
});