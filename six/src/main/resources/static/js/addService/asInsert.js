
async function insert() {
    try {
        const service = document.querySelector("input[name=service]").value;
        const content = document.querySelector("input[name=content]").value;
        const price = document.querySelector("input[name=price]").value;
        const category = document.querySelector("select[name=category]").value;

        const resp = await fetch(`/api/add-service/admin/insert`,{
            method : "POST",
            headers : {
                "Content-Type" : "application/json"
            } ,
            body : JSON.stringify({service,content,price,category})
        });
        if(!resp.ok){
            throw new Error("insert fail.. resp not ok")
        }
        const data = await resp.json();
        if(data.result != 1){
            throw new Error("insert fail.. result not 1")
        }
        alert("등록 성공 !")
    } catch (error) {
        console.log(error);
        alert("등록 실패...")
        
    }finally{
        location.href = "/add-service/list/1"
    }
}