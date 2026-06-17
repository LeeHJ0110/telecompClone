const checkDay = document.querySelector("#attendanceCount");
const btn = document.getElementById("checkBtn");

let count = 0;
let isCheckedToday = false;
loadCheckDay();
async function loadCheckDay() {
    const resp = await fetch(`/api/event/mypage/attendance`)
    if(!resp.ok){
        throw new Error("출석일 불러오기 실패");
    }
    const data = await resp.json();
    checkDay.innerHTML = data.count+"일";
}

async function attendance() {
    if (loginAdmin) {
        alert("회원 전용 컨텐츠 입니다.");
        location.href = `/employee/adminhome`;
    }else if (loginEmployee) {
        location.href = `/qr`
    }else if(!loginMember){
        alert("로그인 후 참여 가능");
        location.href = `/member/login`
    }
    if(isCheckedToday){
        alert("이미 출석 완료했습니다.");
        return;
    }
    try {
        const resp = await fetch(`/api/event/mypage/attendance`, {
            method: "POST"
        });

        if(!resp.ok){
            throw new Error("출석 실패");
        }

        const data = await resp.json();

        checkDay.innerText = data.count + "일";

        isCheckedToday = true;
        btn.disabled = true;
        btn.innerText = "출석 완료";

    } catch (e){
        console.log(e);
        
    }
}
