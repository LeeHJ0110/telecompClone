async function loadEmployeeDetail() {
    const no = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/employeemanage/${no}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("detail load fail...");
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
    document.querySelector("#agencyName").value = vo.agencyName ?? "";
    document.querySelector("#jobName").value = vo.jobName ?? "";
    document.querySelector("#resident").value = vo.resident ? formatResident(vo.resident) : "";
    document.querySelector("#address").value = vo.address ?? "";
    document.querySelector("#address2").value = vo.address2 ?? "";
    document.querySelector("#createdAt").value = vo.createdAt ? formatDate(vo.createdAt) : "";
}

function moveToEditPage() {
    const no = location.pathname.split("/").pop();
    location.href = `/employee/employeemanage/edit/${no}`;
}

async function deleteEmployee() {
    try {
        const no = location.pathname.split("/").pop();

        if (!confirm("삭제하시겠습니까?")) return;

        const resp = await fetch(`/employee/employeemanage`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ no })
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
        location.href = `/employee/employeemanage/list/1`;

    } catch (error) {
        console.log(error);
        alert("삭제 중 오류 발생...");
    }
}

window.addEventListener("DOMContentLoaded", () => {
    loadEmployeeDetail().catch(error => {
        console.log(error);
        alert("상세조회 실패...");
    });
});