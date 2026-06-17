async function insert(){

    const question = document.querySelector("main input[id=question]").value;
    const answer = document.querySelector("main textarea[id=answer]").value;
    const category = document.querySelector("#category").value;

    try {
        const resp = await fetch(`/board/faq` , {
            method : "POST",
            headers : {
                "Content-Type" : "application/json" ,
            },
            body : JSON.stringify( {question, answer, category} ) ,
        });

        if(!resp.ok){
            throw new Error("faq insert fail ...");
        }

        alert("게시글 작성 성공 !");
        location.href = `/board/faq/list/1`;
    } catch (error) {
        console.log(error);
    }

}