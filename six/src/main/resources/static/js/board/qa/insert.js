async function insert(){

    const title = document.querySelector("main input[id=title]").value;
    const content = document.querySelector("main textarea[id=content]").value;
    const category = document.querySelector("#category").value;

    try {
        const resp = await fetch(`/board/qa` , {
            method : "POST",
            headers : {
                "Content-Type" : "application/json" ,
            },
            body : JSON.stringify( {title, content,category} ) ,
        });

        if(!resp.ok){
            throw new Error("board insert fail ...");
        }

        alert("게시글 작성 성공 !");
        location.href = `/board/qa/list/1`;
    } catch (error) {
        console.log(error);
    }

}