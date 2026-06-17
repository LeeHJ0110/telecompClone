async function update() {
    try {
        const term = document.querySelector("input[name=term]").value;
        const discountRate = document.querySelector("input[name=discountRate]").value;
        const penalty = document.querySelector("input[name=penalty]").value;
        const no = localStorage.getItem("mcNo");
    
        const resp = await fetch(`/api/main-contract/admin/update`,{
            method : "PUT",
            headers : {
                "Content-Type" : "application/json"
            } ,
            body : JSON.stringify({term,discountRate,penalty,no})
        })
        if(!resp.ok){
            throw new Error("약정 수정 에러 resp not ok");
        }
        const data = await resp.json();
        if(data.result != 1){
            throw new Error("약정 수정 에러 result not 1");
        }
        alert("약정 수정 완료!");
        location.href = `/main-contract/list`
    } catch (error) {
        console.log(error);
        alert("에러발생..");
        location.href = `/home`
    }finally{
        localStorage.removeItem("mcNo");
    }
    
    

}