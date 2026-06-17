async function save() {
    // 1. input 태그에서 사용자가 입력한 값 가져오기
    const email = document.querySelector("input[name='email']").value;
    const address = document.querySelector("input[name='address']").value;
    const address2 = document.querySelector("input[name='address2']").value;

  
    const resp = await fetch('/mypage/edit', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({email, address, address2})
    });

    if (!resp.ok) {
        alert("작성 내용을 확인해주세요.")
        throw new Error("수정 실패");
    }
    const data = await resp.json();
    alert("수정 성공");
    
    if (data.result != "1") {
        throw new Error("info edit fail..");
    }
    location.href= "/mypage";

}

async function quit(){
    try {
        confirm("정말 탈퇴하시겠습니까?");
        const no = document.querySelector("main input[name=no]").value;

        const resp = await fetch(`/mypage/myquit` , {
            method : "DELETE" ,
            headers : {
                "Content-Type" : "application/json" ,
            } ,
            body : JSON.stringify( {no} ) ,
        });

        if(!resp.ok){
            throw new Error("fail to delete ...");
        }

        const data = await resp.json();
        if(data.result != 1){
            throw new Error("fail to delete ...");
        }

        alert("회원탈퇴 완료 !");
        location.href = "/home";
    } catch (error) {
        location.href=`/home`;
        console.log(error);
        
    }
}


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