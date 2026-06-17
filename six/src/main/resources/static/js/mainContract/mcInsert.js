async function insert() {
    const term = document.querySelector("input[name=term]").value;
    const discountRate = document.querySelector("input[name=discountRate]").value;
    const penalty = document.querySelector("input[name=penalty]").value;

    const resp = await fetch(`/api/main-contract/admin/insert`,{
        method : "POST",
        headers : {
            "Content-Type" : "application/json"
        } ,
        body : JSON.stringify({term,discountRate,penalty})
    })
    if(!resp.ok){
        throw new Error("약정 등록 에러 resp not ok");
    }
    const data = await resp.json();
    if(data.result != 1){
        throw new Error("약정 등록 에러 result not 1");
    }
    alert("약정 등록 완료!");
    location.href = `/main-contract/list`

}