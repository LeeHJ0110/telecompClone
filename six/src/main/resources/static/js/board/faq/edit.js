async function loadBoardVo(){
     const boardNo = Number(location.pathname.split("/").pop());

        if(isNaN(boardNo)){
            alert("잘못된 접근입니다.");
            return;
        }
    const resp = await fetch(`/board/faq/${boardNo}`);

    if(!resp.ok){
        throw new Error("loadBoardVo fail ...");
    }

    const data = await resp.json();
    const vo = data.vo;

    if(!vo){
        console.error("vo 없음", data);
        return;
    }

    console.log(data);
    document.querySelector("#category").value = vo.category;
    document.querySelector("#question").value = vo.question;
    document.querySelector("#answer").value = vo.answer;
    document.querySelector("#no").value = vo.no;
}
loadBoardVo();

async function edit(){
    console.log("edit start");
    try {
        const question = document.querySelector("main input[id=question]").value;
        const answer = document.querySelector("main textarea[id=answer]").value;
        const category = document.querySelector("#category").value;
        const no = document.querySelector("#no").value;


        const resp = await fetch(`/board/faq` , {
            method : "PUT" ,
            headers : {
                "Content-Type" : "application/json" ,
            } ,
            body : JSON.stringify( {question, answer,category, no} ) ,
        });

        if(!resp.ok){
            throw new Error("board edit fail ...");
        }

        const data = await resp.json();
        console.log("data : " , data);


        if(data.result != 1){
            throw new Error("faq edit fail ...");
        }

        alert("게시글 수정 완료 !");
        location.href = `/board/faq/list/1`;
    } catch (error) {
        alert("게시글 수정 실패 !");
        location.href = `/board/notice/list/1`;
        console.log(error);
    }

}

async function del(){
    console.log("del start");
    try {
        const no = document.querySelector("#no").value;

        const resp = await fetch(`/board/faq` , {
            method : "delete" ,
            headers : {
                "Content-Type" : "application/json" ,
            } ,
            body : JSON.stringify( {no} ) ,
        });

        if(!resp.ok){
            throw new Error("faq to delete ...");
        }

        const data = await resp.json();
        if(data.result != 1){
            throw new Error("faq to delete ...");
        }
        if(!confirm("정말 삭제하시겠습니까?")){
            return;
        }

        alert("게시글 삭제 완료 !");
        location.href = "/board/faq/list/1";
    } catch (error) {
        alert("게시글 삭제 실패 !");
        console.log(error);

    }
}