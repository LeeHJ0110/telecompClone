async function login(){
    const id = document.querySelector("main input[name=id]").value;
    const pw = document.querySelector("main input[name=pw]").value;

    const resp = await fetch(`/member/login` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json",
        } ,
        body : JSON.stringify({id, pw}) ,
    });

    if (!resp.ok) {
        alert("아이디 또는 비밀번호를 확인해주세요.");
        throw new Error("로그인 실패");
    }

    const data = await resp.json();

    location.href= "/home";
    
}

document.querySelector("main input[name=pw]").addEventListener("keydown", function(e){
    if(e.key === "Enter"){
        login();
    }
});

document.querySelector("main input[name=id]").addEventListener("keydown", function(e){
    if(e.key === "Enter"){
        login();
    }
});

