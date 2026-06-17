async function insert(){

    const title = document.querySelector("main input[id=title]").value;
    const content = document.querySelector("main textarea[id=content]").value;

    try {
        const resp = await fetch(`/board/notice` , {
            method : "POST",
            headers : {
                "Content-Type" : "application/json" ,
            },
            body : JSON.stringify( {title, content} ) ,
        });

        if(!resp.ok){
            throw new Error("board insert fail ...");
        }

        alert("게시글 작성 성공 !");
        location.href = `/board/notice/list/1`;
    } catch (error) {
        console.log(error);
    }

}