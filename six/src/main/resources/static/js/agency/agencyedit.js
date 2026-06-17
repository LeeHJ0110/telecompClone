function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            let addr = "";

            if (data.userSelectedType === "R") {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            document.querySelector("#postcode").value = data.zonecode;
            document.querySelector("#address").value = addr;
            document.querySelector("#detailAddress").focus();
        }
    }).open();
}

async function loadEmployeeList(selectedManagerNo = "") {
    const resp = await fetch("/employee/employeemanage/admin/store/employee-list");

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("employee list load fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    const managerSelect = document.querySelector("#managerNo");
    let str = `<option value="">담당자를 선택하세요</option>`;

    for (const vo of voList) {
        const phoneText = vo.phone ? vo.phone : "-";
        const selected = String(vo.no) === String(selectedManagerNo) ? "selected" : "";
        str += `<option value="${vo.no}" ${selected}>${vo.name} / ${phoneText}</option>`;
    }

    managerSelect.innerHTML = str;
}

async function loadAgency() {
    const no = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/employeemanage/admin/store/${no}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("agency load fail...");
    }

    const data = await resp.json();
    const vo = data.vo;

    if (!vo) {
        alert("대리점 정보를 찾을 수 없습니다.");
        return;
    }

    document.querySelector("#name").value = vo.name ?? "";
    document.querySelector("#phone").value = vo.phone ?? "";
    document.querySelector("#address").value = vo.address ?? "";

    await loadEmployeeList(vo.managerNo);
}

async function submitEdit() {
    try {
        const no = location.pathname.split("/").pop();

        const name = document.querySelector("#name").value.trim();
        const managerNo = document.querySelector("#managerNo").value;
        const phone = document.querySelector("#phone").value.trim();
        const address = document.querySelector("#address").value.trim();
        const detailAddress = document.querySelector("#detailAddress").value.trim();

        if (!name) {
            alert("대리점명을 입력해주세요.");
            return;
        }

        if (!managerNo) {
            alert("담당자를 선택해주세요.");
            return;
        }

        if (!phone) {
            alert("연락처를 입력해주세요.");
            return;
        }

        if (!address) {
            alert("주소를 입력해주세요.");
            return;
        }

        const fullAddress = detailAddress ? `${address} ${detailAddress}` : address;

        const requestData = {
            no: no,
            name: name,
            managerNo: managerNo,
            phone: phone,
            address: fullAddress
        };

        const resp = await fetch("/employee/employeemanage/admin/store", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        });

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("agency update fail...");
        }

        const data = await resp.json();

        if (data.result != 1) {
            alert("대리점 수정 실패...");
            return;
        }

        alert("대리점 수정 완료");
        location.href = `/employee/employeemanage/admin/store/detail/${no}`;

    } catch (error) {
        console.log(error);
        alert("수정 중 오류 발생...");
    }
}

function moveToDetailPage() {
    const no = location.pathname.split("/").pop();
    location.href = `/employee/employeemanage/admin/store/detail/${no}`;
}

window.addEventListener("DOMContentLoaded", () => {
    loadAgency().catch(error => {
        console.log(error);
        alert("대리점 정보 불러오기 실패...");
    });
});