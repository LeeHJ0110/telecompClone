async function insert() {
    try {
        const category = document.querySelector("select[name=category]").value;
        const dataNo = document.querySelector("select[name=dataNo]").value;
        const name = document.querySelector("input[name=name]").value;
        const dataTotal = document.querySelector("input[name=dataTotal]").value;
        const dataShare = document.querySelector("input[name=dataShare]").value;
        const price = document.querySelector("input[name=price]").value;
        const voiceTotal = document.querySelector("input[name=voiceTotal]").value;
        const smsTotal = document.querySelector("input[name=smsTotal]").value;
        const description = document.querySelector("input[name=description]").value;

        const result = {category,dataNo,name,dataTotal,dataShare,price,voiceTotal,smsTotal,description};
    
        const resp = await fetch(`/api/plan/admin/insert`,{
            method : "POST",
            headers : {
                "Content-Type" : "application/json"
            },
            body : JSON.stringify(result)
        })
        if(!resp.ok){
            throw new Error("resp error...");
        }
        const data = await resp.json();
        if(data.result != 1){
            throw new Error("result not 1");
        }
        alert("요금제 등록 성공!");
        location.href = "/plan/list/1"
        
    } catch (error) {
        alert(error);
    }
    
}