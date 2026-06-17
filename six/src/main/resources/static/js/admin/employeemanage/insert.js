let isIdVerified = false;

async function join(){

    if(!isIdVerified){
        alert("아이디 중복확인을 해주세요.");
        return;
    }

    const form = document.querySelector("#joinForm");
    const fd = new FormData(form);

    try{
        const resp = await fetch("/employee/employeemanage/admin/insert",{
            method:"POST",
            body:fd
        });

        const data = await resp.json();
        console.log("서버응답 :", data);

        if(!resp.ok){
            console.log("서버에러 :", data);
            alert("회원가입 실패");
            return;
        }

        if(data.x === "1"){
            alert("회원가입 성공");
            location.href="/employee/employeemanage/list/1";
        }else{
            alert("회원가입 실패");
        }

    }catch(err){
        console.log("에러 :", err);
        alert("회원가입 실패");
    }
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            let addr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            const postcodeInput = document.getElementById("postcode");
            if(postcodeInput){
                postcodeInput.value = data.zonecode;
            }

            const addressInput = document.getElementById("address");
            if(addressInput){
                addressInput.value = addr;
            }

            const address2Input = document.getElementById("address2");
            if(address2Input){
                address2Input.focus();
            }
        }
    }).open();
}

async function Duplicate_Check() {
    const idInput = document.querySelector('input[name="id"]');
    const userId = idInput.value.trim();

    if (userId === '') {
        alert('아이디를 입력해주세요.');
        idInput.focus();
        return;
    }

    try {
        const response = await fetch(`/employee/checkId?id=${encodeURIComponent(userId)}`);

        if (!response.ok) {
            throw new Error('서버 통신 오류');
        }

        const result = await response.json();
        const msgTag = document.querySelector("#idCheck");

        if (result.isDuplicate) {
            msgTag.innerText = "이미 사용중인 아이디입니다.";
            msgTag.style.color = "red";
            idInput.value = '';
            idInput.focus();
            isIdVerified = false;
        } else {
            msgTag.innerText = "사용 가능한 아이디입니다.";
            msgTag.style.color = "green";
            isIdVerified = true;
        }

    } catch (error) {
        console.error('에러 발생:', error);
        alert('중복 확인 중 오류가 발생했습니다.');
    }
}

document.querySelector('input[name="id"]').addEventListener('input', function() {
    isIdVerified = false;

    const msgTag = document.querySelector("#idCheck");
    if(msgTag){
        msgTag.innerText = "아이디 중복확인을 해주세요.";
        msgTag.style.color = "gray";
    }
});