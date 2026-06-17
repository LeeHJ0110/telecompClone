function login(){
    const agencyNo = document.querySelector("body input[name=agencyNo]").value;
    const id = document.querySelector("body input[name=id]").value;
    const pw = document.querySelector("body input[name=pw]").value;

    fetch(`/employee/login` , {
        method : "POST" ,
        headers : {
            "Content-Type" : "application/json",
        } ,
        body : JSON.stringify({agencyNo, id, pw}) ,
    })
    .then( resp => {
        if(!resp.ok){
            throw new Error("login err ~~~");
        }
        alert("로그인 성공 !");
        location.href = "/employee/adminhome";
    } )
    .catch( err => {
        console.log(err);
        alert("로그인 실패 ...");
    })
    ;
}

document.querySelector("body input[name=pw]").addEventListener("keydown", function(e){
    if(e.key === "Enter"){
        login();
    }
});
document.querySelector("body input[name=id]").addEventListener("keydown", function(e){
    if(e.key === "Enter"){
        login();
    }
});
document.querySelector("body input[name=agencyNo]").addEventListener("keydown", function(e){
    if(e.key === "Enter"){
        login();
    }
});