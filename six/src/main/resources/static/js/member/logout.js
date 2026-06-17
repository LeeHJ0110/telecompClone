async function logout() {
    const resp = await fetch('/member/logout');

    if (!resp.ok) {
        throw new Error("logout fail js");
    }
    localStorage.clear();
    alert("로그아웃 되었습니다.");
    location.href="/home";
}