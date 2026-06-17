async function loadBoardVo(selectedPhone = "null") {
    const boardNo = location.pathname.split("/").pop();

    let url = `/employee/membermanage/${boardNo}?phone=${encodeURIComponent(selectedPhone)}`;
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
    const fixedInfoList = data.fixedInfoList;
    const fixedInfoVo = data.fixedInfoVo;

    if (!vo) {
        alert("회원 정보를 찾을 수 없습니다.");
        return;
    }

    renderMemberInfo(vo);
    renderPhoneSelect(fixedInfoList, fixedInfoVo, selectedPhone);
}

function renderMemberInfo(vo) {
    document.querySelector("#name").value = vo.name ?? "";
    document.querySelector("#resident").value = vo.resident ? formatResident(vo.resident) : "";
    document.querySelector("#address").value = vo.address ?? "";
    document.querySelector("#address2").value = vo.address2 ?? "";
    document.querySelector("#email").value = vo.email ?? "";
    document.querySelector("#memberCreatedAt").value = vo.createdAt ? formatDate(vo.createdAt) : "";
}

function renderPhoneSelect(fixedInfoList, fixedInfoVo, selectedPhone) {
    const phoneSelect = document.querySelector("#phoneSelect");
    let str = "";

    if (!fixedInfoList || fixedInfoList.length === 0) {
        str = `<option value="null">요금제 미가입 고객</option>`;
    } else {
        for (const item of fixedInfoList) {
            let selected = "";

            if (fixedInfoVo && fixedInfoVo.phone === item.phone) {
                selected = "selected";
            } else if (!fixedInfoVo && selectedPhone === item.phone) {
                selected = "selected";
            }

            str += `<option value="${item.phone}" ${selected}>${formatPhone(item.phone)}</option>`;
        }
    }

    phoneSelect.innerHTML = str;
}

async function changePhone() {
    try {
        const phone = document.querySelector("#phoneSelect").value;
        await loadBoardVo(phone);
    } catch (error) {
        console.log(error);
        alert("전화번호 변경 조회 실패...");
    }
}

async function submitEdit() {
    try {
        const no = location.pathname.split("/").pop();

        const requestData = {
            no: no,
            name: document.querySelector("#name").value,
            address: document.querySelector("#address").value,
            address2: document.querySelector("#address2").value,
            email: document.querySelector("#email").value
        };

        const resp = await fetch(`/employee/membermanage`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        });

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("update fail...");
        }

        const data = await resp.json();

        if (data.result != 1) {
            alert("수정 실패...");
            return;
        }

        alert("수정 완료");
        location.href = `/employee/membermanage/detail/${no}`;

    } catch (error) {
        console.log(error);
        alert("수정 중 오류 발생");
    }
}

function moveToDetailPage() {
    const no = location.pathname.split("/").pop();
    const phone = document.querySelector("#phoneSelect")?.value ?? "null";
    location.href = `/employee/membermanage/detail/${no}?phone=${encodeURIComponent(phone)}`;
}
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            let addr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            document.getElementById("postcode").value = data.zonecode;
            document.getElementById("address").value = addr;

            const address2 = document.getElementById("address2");
            if (address2) {
                address2.focus();
            }
        }
    }).open();
}

window.addEventListener("DOMContentLoaded", async () => {
    try {
        const params = new URLSearchParams(location.search);
        const phone = params.get("phone") ?? "null";

        await loadBoardVo(phone);

        const phoneSelect = document.querySelector("#phoneSelect");
        phoneSelect.addEventListener("change", changePhone);

    } catch (error) {
        console.log(error);
        alert("수정페이지 조회 실패...");
    }
});