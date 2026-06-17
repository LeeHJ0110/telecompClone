async function loadJobList(selectedJobNo = "") {
    const resp = await fetch(`/employee/employeemanage/job-list`);

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("job list load fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    const jobSelect = document.querySelector("#jobNo");
    let str = `<option value="">직책 선택</option>`;

    for (const vo of voList) {
        const selected = String(vo.no) === String(selectedJobNo) ? "selected" : "";
        str += `<option value="${vo.no}" ${selected}>${vo.jobName}</option>`;
    }

    jobSelect.innerHTML = str;
}

async function loadAgencyList(selectedAgencyNo = "") {
    const resp = await fetch(`/employee/employeemanage/agency-list`);

    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("agency list load fail...");
    }

    const data = await resp.json();
    const voList = data.voList;

    const agencySelect = document.querySelector("#agencyNo");
    let str = `<option value="">소속 선택</option>`;

    for (const vo of voList) {
        const selected = String(vo.no) === String(selectedAgencyNo) ? "selected" : "";
        str += `<option value="${vo.no}" ${selected}>${vo.name}</option>`;
    }

    agencySelect.innerHTML = str;
}

async function loadEmployee() {
    const no = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/employeemanage/${no}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("load fail...");
    }

    const data = await resp.json();
    const vo = data.vo;

    if (!vo) {
        alert("직원 정보를 찾을 수 없습니다.");
        return;
    }

    document.querySelector("#name").value = vo.name ?? "";
    document.querySelector("#email").value = vo.email ?? "";
    document.querySelector("#phone").value = vo.phone ? formatPhone(vo.phone) : "";
    document.querySelector("#resident").value = vo.resident ? formatResident(vo.resident) : "";
    document.querySelector("#address").value = vo.address ?? "";
    document.querySelector("#address2").value = vo.address2 ?? "";
    document.querySelector("#createdAt").value = vo.createdAt ? formatDate(vo.createdAt) : "";

    await loadAgencyList(vo.agencyNo);
    await loadJobList(vo.jobNo);
}

async function submitEdit() {
    try {
        const no = location.pathname.split("/").pop();

        const requestData = {
            no: no,
            name: document.querySelector("#name").value.trim(),
            email: document.querySelector("#email").value.trim(),
            phone: document.querySelector("#phone").value.replace(/\D/g, ""),
            agencyNo: document.querySelector("#agencyNo").value,
            jobNo: document.querySelector("#jobNo").value,
            address: document.querySelector("#address").value.trim(),
            address2: document.querySelector("#address2").value.trim()
        };

        if (!requestData.agencyNo) {
            alert("소속을 선택해주세요.");
            return;
        }

        if (!requestData.jobNo) {
            alert("직책을 선택해주세요.");
            return;
        }

        const resp = await fetch(`/employee/employeemanage`, {
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
        location.href = `/employee/employeemanage/detail/${no}`;

    } catch (error) {
        console.log(error);
        alert("수정 중 오류 발생...");
    }
}

function moveToDetailPage() {
    const no = location.pathname.split("/").pop();
    location.href = `/employee/employeemanage/detail/${no}`;
}

window.addEventListener("DOMContentLoaded", () => {
    loadEmployee().catch(error => {
        console.log(error);
        alert("수정페이지 조회 실패...");
    });
});