async function update() {
    try {
        const no = localStorage.getItem("uNo");
        const category = document.querySelector("select[name=category]").value;
        const dataNo = document.querySelector("select[name=dataNo]").value;
        const name = document.querySelector("input[name=name]").value;
        const dataTotal = document.querySelector("input[name=dataTotal]").value;
        const dataShare = document.querySelector("input[name=dataShare]").value;
        const price = document.querySelector("input[name=price]").value;
        const voiceTotal = document.querySelector("input[name=voiceTotal]").value;
        const smsTotal = document.querySelector("input[name=smsTotal]").value;
        const description = document.querySelector("input[name=description]").value;
        
        const result = {no,category,dataNo,name,dataTotal,dataShare,price,voiceTotal,smsTotal,description};
    
        const resp = await fetch(`/api/plan/admin/update`,{
            method : "PUT",
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
        alert("요금제 수정 성공!");
        location.href = "/plan/list/1"
        
    } catch (error) {
        alert(error);
    }finally{
        localStorage.removeItem("uNo");
    }
}

async function load() {

    const no = localStorage.getItem("uNo")
    const resp = await fetch(`/api/plan/detail/${no}`)
    if(!resp.ok){
        throw new Error("load err not resp ok");
    }
    const data = await resp.json();
    const vo = data.vo;
    
    document.querySelector("select[name=category]").value = vo.category;
    document.querySelector("select[name=dataNo]").value = vo.dataNo;
    document.querySelector("input[name=name]").value = vo.name;
    document.querySelector("input[name=dataTotal]").value = vo.dataTotal;
    document.querySelector("input[name=dataShare]").value = vo.dataShare;
    document.querySelector("input[name=price]").value = vo.price;
    document.querySelector("input[name=voiceTotal]").value = vo.voiceTotal;
    document.querySelector("input[name=smsTotal]").value = vo.smsTotal;
    document.querySelector("input[name=description]").value = vo.description;
}
load();