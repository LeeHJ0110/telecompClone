function join(){

    const form = document.querySelector("form");
    const fd = new FormData(form);

    fetch("/employee/join",{
        method:"POST",
        body:fd
    })
    .then(resp=>resp.json())
    .then(data=>{

        console.log("서버응답 :", data);

        if(data.x === "1"){
            alert("회원가입 성공");
            location.href = "/employee/adminhome";
        }else{
            alert("회원가입 실패");
        }

    })
    .catch(err=>{
        console.log("에러 :", err);
        alert("회원가입 실패");
    });
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {

            // 도로명 주소
            let addr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;   // 도로명
            } else {
                addr = data.jibunAddress;  // 지번
            }

            // 우편번호
            document.getElementById("postcode").value = data.zonecode;

            // 주소 (👉 이게 핵심)
            document.getElementById("address").value = addr;

            // 포커스 이동 (선택)
            document.getElementById("address2")?.focus();
        }
    }).open();
}
async function Duplicate_Check() {
    const idInput = document.querySelector('input[name="id"]');
    const userId = idInput.value.trim();

    // 1. 빈 값 체크
    if (userId === '') {
        alert('아이디를 입력해주세요.');
        idInput.focus();
        return;
    }

    try {
        const response = await fetch(`/employee/checkId?id=${userId}`);

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
//중복확인하고 아이디 재입력시 뚫리는 버그 방지
document.querySelector('input[name="id"]').addEventListener('input', function() {
    isIdVerified = false;
});