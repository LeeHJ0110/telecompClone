load();

async function update() {
    try {
        const no = localStorage.getItem("asNo");
        const service = document.querySelector("input[name=service]").value;
        const content = document.querySelector("input[name=content]").value;
        const price = document.querySelector("input[name=price]").value;
        const category = document.querySelector("select[name=category]").value;

        const resp = await fetch(`/api/add-service/admin/update`,{
            method : "PUT",
            headers : {
                "Content-Type" : "application/json"
            } ,
            body : JSON.stringify({no,service,content,price,category})
        });
        if(!resp.ok){
            throw new Error("update fail.. resp not ok")
        }
        const data = await resp.json();
        if(data.result != 1){
            throw new Error("update fail.. result not 1")
        }
        alert("수정 성공 !")
        location.href = "/add-service/list/1"
    } catch (error) {
        console.log(error);
        alert("수정 실패...")
        location.href = "/add-service/list/1"
    } finally{
        localStorage.removeItem("asNo");
    }
}
async function load() {
    const no = localStorage.getItem("asNo");
    const resp = await fetch(`/api/add-service/${no}`)
    if(!resp.ok){
        throw new Error("load fail.. resp not ok")
    }
    const data = await resp.json();
    const vo = data.vo;

    document.querySelector("select[name=category]").value = vo.category;
    document.querySelector("input[name=content]").value = vo.content;
    document.querySelector("input[name=price]").value = vo.price;
    document.querySelector("input[name=service]").value = vo.service;
}
