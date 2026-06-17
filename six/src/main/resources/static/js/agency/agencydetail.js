async function loadAgencyDetail() {
    const no = location.pathname.split("/").pop();

    const resp = await fetch(`/employee/employeemanage/admin/store/${no}`);
    if (!resp.ok) {
        const errorText = await resp.text();
        console.log(errorText);
        throw new Error("detail load fail...");
    }

    const data = await resp.json();
    const vo = data.vo;

    if (!vo) {
        alert("대리점 정보 없음");
        return;
    }

    document.querySelector("#name").value = vo.name ?? "";
    document.querySelector("#managerName").value = vo.managerName ?? "";
    document.querySelector("#phone").value = vo.phone ? formatPhone(vo.phone) : "";
    document.querySelector("#address").value = vo.address ?? "";
    document.querySelector("#createdAt").value = vo.createdAt ? formatDate(vo.createdAt) : "";
}

function moveToEditPage() {
    const no = location.pathname.split("/").pop();
    location.href = `/employee/employeemanage/admin/store/edit/${no}`;
}

async function deleteAgency() {
    try {
        const no = location.pathname.split("/").pop();

        if (!confirm("삭제하시겠습니까?")) return;

        const resp = await fetch(`/employee/employeemanage/admin/store`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ no })
        });

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("delete fail");
        }

        const data = await resp.json();

        if (data.result != 1) {
            alert("삭제 실패");
            return;
        }

        alert("삭제 완료");
        location.href = `/employee/employeemanage/admin/store/list/1`;

    } catch (error) {
        console.log(error);
        alert("삭제 오류");
    }
}

window.addEventListener("DOMContentLoaded", () => {
    loadAgencyDetail().catch(error => {
        console.log(error);
        alert("상세조회 실패...");
    });
});