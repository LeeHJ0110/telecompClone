function closeSearch(){
    document.getElementById("searchDropdown").classList.remove("active");
}
document.addEventListener("DOMContentLoaded", () => {
    renderRecentKeywords();
});

document.addEventListener("click", function(e){
    const dropdown = document.getElementById("searchDropdown");
    const btn = document.querySelector(".icon-btn[onclick='openSearch()']");

    // dropdown 내부 클릭은 무시
    if (dropdown.contains(e.target) || btn.contains(e.target)) {
        return;
    }

    closeSearch();
});

function saveKeyword(keyword) {
    if (!keyword) return;

    let keywords = JSON.parse(localStorage.getItem("recentSearch")) || [];

    // 중복 제거: 기존에 같은 단어가 있다면 삭제 후 맨 앞에 추가
    keywords = keywords.filter(item => item !== keyword);
    keywords.unshift(keyword);

    // 최대 5개까지만 저장 (개수 조절 가능)
    if (keywords.length > 5) {
        keywords.pop();
    }

    localStorage.setItem("recentSearch", JSON.stringify(keywords));
    renderRecentKeywords();
}

function renderRecentKeywords() {
    const recentList = document.getElementById("recentList");
    const keywords = JSON.parse(localStorage.getItem("recentSearch")) || [];

    if (keywords.length === 0) {
        recentList.innerHTML = '<li>최근 검색어가 없습니다.</li>';
        return;
    }

    recentList.innerHTML = keywords.map(word => 
        `<li onclick="quickSearch('${word}')">${word}</li>`
    ).join('');
}

function openSearch(){
    const el = document.getElementById("searchDropdown");
    el.classList.add("active");

    setTimeout(()=>{
        document.getElementById("searchInput").focus();
    }, 100);
}
function doSearch(){
    const keyword = document.getElementById("searchInput").value.trim();

    if(!keyword){
        alert("검색어를 입력하세요.");
        return;
    }
    saveKeyword(keyword);

    // 실제 이동 (프로젝트에 맞게 수정)
    location.href = `https://search.naver.com/search.naver?query=${encodeURIComponent(keyword)}`;
}

document.getElementById("searchInput").addEventListener("keydown", function(e){
    if(e.key === "Enter"){
        doSearch();
    }
});
document.addEventListener("keydown", function(e){
    if(e.key === "Escape"){
        closeSearch();
    }
});

function quickSearch(keyword){
    location.href = `https://search.naver.com/search.naver?query=${encodeURIComponent(keyword)}`;
}
function instar(){
    location.href = `https://www.instagram.com/dandelion7100061/`
}

function checkEmployee(){
    if(loginEmployee === "true" || loginAdmin === "true"){
        const root = document.querySelector(':root'); 
        root.style.setProperty('--active-color', `#477FC9`);
    }else{
        const root = document.querySelector(':root'); 
        root.style.setProperty('--active-color', `#ff6a00`);
    }
}
checkEmployee();