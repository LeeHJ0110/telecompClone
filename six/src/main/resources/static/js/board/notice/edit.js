async function loadBoardVo(){
     const boardNo = Number(location.pathname.split("/").pop());

        if(isNaN(boardNo)){
            alert("잘못된 접근입니다.");
            return;
        }
    const resp = await fetch(`/board/notice/${boardNo}`);

    if(!resp.ok){
        throw new Error("loadBoardVo fail ...");
    }

    const data = await resp.json();
    const vo = data.vo;

    document.querySelector("main input[id=title]").value = vo.title;
    document.querySelector("main textarea[id=content]").value = vo.content;
    document.querySelector("#no").value = vo.no;
}
loadBoardVo();

async function edit(){
    console.log("edit start");
    try {
        const title = document.querySelector("#title").value;
        const content = document.querySelector("#content").value;
        const no = document.querySelector("#no").value;


        const resp = await fetch(`/board/notice` , {
            method : "PUT" ,
            headers : {
                "Content-Type" : "application/json" ,
            } ,
            body : JSON.stringify( {title, content, no} ) ,
        });

        if(!resp.ok){
            throw new Error("board edit fail ...");
        }

        const data = await resp.json();
        console.log("data : " , data);


        if(data.result != 1){
            throw new Error("board edit fail ...");
        }

        alert("게시글 수정 완료 !");
        location.href = `/board/notice/detail/${no}`;
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

        const resp = await fetch(`/board/notice` , {
            method : "delete" ,
            headers : {
                "Content-Type" : "application/json" ,
            } ,
            body : JSON.stringify( {no} ) ,
        });

        if(!resp.ok){
            throw new Error("fail to delete ...");
        }

        const data = await resp.json();
        if(data.result != 1){
            throw new Error("fail to delete ...");
        }
        if(!confirm("정말 삭제하시겠습니까?")){
            return;
        }

        alert("게시글 삭제 완료 !");
        location.href = "/board/notice/list/1";
    } catch (error) {
        alert("게시글 삭제 실패 !");
        location.href=`/home`;
        console.log(error);

    }
}