let isIdVerified = false;

async function join() {
    if (pwCheckMsg.innerHTML === "비밀번호가 일치하지 않습니다.") {
        alert("비밀번호를 확인해주세요");
        return;
    }
    // 사용자가 중복확인 버튼을 안 눌렀거나, 중복된 아이디인 상태로 가입버튼을 누른 경우 차단
    if (!isIdVerified) {
        alert('아이디 중복 확인을 진행해주세요.');
        return;
    }

    const id = document.querySelector("main input[name=id]").value;
    const pw = document.querySelector("main input[name=pw]").value;
    const pw2 = document.querySelector("main input[name=pw2]").value;
    const name = document.querySelector("main input[name=name]").value;
    // const phone = document.querySelector("main input[name=phone]").value;
    const address = document.querySelector("main input[name=address]").value;
    const address2 = document.querySelector("main input[name=address2]").value;
    const email_id = document.querySelector("main input[name=email]").value;
    const email_domain = document.querySelector("#domain_txt").value
    const email = email_id + "@" + email_domain;
    const resident = document.querySelector("main input[name=resident]").value;
    // const profile = document.querySelector("main input[name=profile]").files[0];

    if (!id || !pw || !name || !address || !email_id || !email_domain || !resident) {
        alert("상세주소를 제외한 정보는 필수로 입력해주세요.");
        return;
    }


    const idRegex = /^[a-zA-Z0-9]+$/; // 아이디: 영문, 숫자만
    const pwRegex = /^[a-zA-Z0-9!@#$%^&*()_+=-]+$/; // 비밀번호: 영문, 숫자, 특수문자만 (공백 제외)
    const nameRegex = /^[가-힣a-zA-Z]+$/; // 이름: 한글, 영문만 (숫자, 특수문자 제외)

    if (!idRegex.test(id)) {
        alert("아이디는 영문과 숫자만 사용할 수 있습니다.");
        document.querySelector("main input[name=id]").focus();
        return;
    }

    if (!pwRegex.test(pw)) {
        alert("비밀번호는 영문, 숫자, 특수문자만 사용할 수 있습니다. (공백 띄어쓰기 불가)");
        document.querySelector("main input[name=pw]").focus();
        return;
    }

    if (!nameRegex.test(name)) {
        alert("이름은 한글 또는 영문만 사용할 수 있습니다. (숫자, 기호 불가)");
        document.querySelector("main input[name=name]").focus();
        return;
    }

    const emailRegex = /^[a-zA-Z0-9]+$/;

    if (!emailRegex.test(email_id)) {
        alert("이메일 아이디는 영문과 숫자만 사용할 수 있습니다.");
        document.querySelector("main input[name=email]").focus();
        return;
    }

    if (email_id.length > 20) {
        alert("이메일 아이디는 20자 이하로 입력해 주세요.");
        document.querySelector("main input[name=email]").focus();
        return;
    }

    const resp = await fetch(`/member/join`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id, pw, name, address, address2, email, resident
            // phone 잠시 빼두기 회원가입에서 안받아야함
        }),
    });

    if (!resp.ok) {
        throw new Error("고객 회원가입 실패");
    }

    const data = await resp.json();



    if (data.x === "-1" || data.msg === "duplicate_resident") {
        alert("이미 가입된 이용자입니다(사유:주민등록번호)");
        document.querySelector("main input[name=resident]").focus();
        return; // 가입 진행 멈춤
    }

    // (기존 코드에서는 data.x != 1 이었지만, 컨트롤러에서 String.valueOf(result)로
    // 문자열 "1"을 넘겨주기 때문에 문자열 비교로 맞춰주면 더 안전합니다.)
    if (data.x !== "1") {
        throw new Error("회원가입 실패~");
    }

    alert("회원가입 성공");
    location.href = "/member/login";

} // join() 함수 끝

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 사용자가 선택한 주소 타입에 따라 값을 가져온다.
            var addr = '';
            if (data.userSelectedType === 'R') { // 도로명 주소
                addr = data.roadAddress;
            } else { // 지번 주소
                addr = data.jibunAddress;
            }

            // 우편번호와 기본 주소를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;

            // 상세주소 입력칸으로 마우스 커서를 자동으로 옮겨준다.
            document.getElementById("detailAddress").focus();
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
        const response = await fetch(`/member/checkId?id=${userId}`);

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


document.addEventListener("DOMContentLoaded", function() {
    const pw1 = document.getElementById('pw');
    const pw2 = document.getElementById('pw2');
    const pwMsg = document.getElementById('pwCheckMsg');

    function validatePassword() {
        const val1 = pw1.value;
        const val2 = pw2.value;

        // 둘 중 하나라도 비어있으면 메시지 삭제
        if (val1 === "" || val2 === "") {
            pwMsg.innerText = "";
            return;
        }

        if (val1 === val2) {
            pwMsg.innerText = "비밀번호가 일치합니다.";
            pwMsg.style.color = "green";
        } else {
            pwMsg.innerText = "비밀번호가 일치하지 않습니다.";
            pwMsg.style.color = "red";
        }
    }

    // 두 입력창 모두에 실시간 감지 이벤트 등록
    pw1.addEventListener('input', validatePassword);
    pw2.addEventListener('input', validatePassword);
});

function select_domain() {
    const domain_text_box = document.querySelector("#domain_txt");
    const domain_list_box = document.querySelector("#domain_list");

    domain_list_box.addEventListener('change', (event) => {
        if (event.target.value !== 'none') {
            domain_text_box.value = event.target.value;
            domain_text_box.disabled = true;
        } else {
            domain_text_box.value = "";
            domain_text_box.disabled = false;
        }
    });
}

select_domain();