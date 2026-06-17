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

async function loadEmployeeList() {
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
        str += `<option value="${vo.no}">${vo.name}</option>`;
    }

    managerSelect.innerHTML = str;
}

async function submitWrite() {
    try {
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

        // [추가] 연락처 자리수 검증 (9~11자)
        const phoneRegExp = /^\d{9,11}$/; 
        if (!phoneRegExp.test(phone)) {
            alert("연락처는 9~11자리의 숫자만 입력 가능합니다.");
            document.querySelector("#phone").focus();
            return;
        }

        const fullAddress = detailAddress ? `${address} ${detailAddress}` : address;

        const requestData = {
            name: name,
            managerNo: managerNo,
            phone: phone,
            address: fullAddress
        };

        const resp = await fetch("/employee/employeemanage/admin/store", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        });

        if (!resp.ok) {
            const errorText = await resp.text();
            console.log(errorText);
            throw new Error("agency insert fail...");
        }

        const data = await resp.json();

        if (data.result != 1) {
            alert("대리점 등록 실패...");
            return;
        }

        alert("대리점 등록 완료");
        location.href = "/employee/employeemanage/admin/store/list/1";

    } catch (error) {
        console.log(error);
        alert("등록 중 오류 발생...");
    }
}

window.addEventListener("DOMContentLoaded", () => {
    loadEmployeeList().catch(error => {
        console.log(error);
        alert("담당자 목록 불러오기 실패...");
    });
});