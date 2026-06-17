// 전화번호 문자열을 "010-1234-1234" 식으로 변경
function formatPhone(phone){
    const num = phone.replace(/\D/g,"");

    if(num.length === 11){
        return num.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
    }
    return phone;
}

// 돈 문자열을 "20,123,530원" 식으로 변경
function formatMoney(value){
    return Number(value).toLocaleString("ko-KR") + "원";
}

// 카드 번호 문자열을 "1234-XXXX-XXXX-4321" 식으로 변경(15자도 가능)
function formatCardNumber(card){
    const num = String(card).replace(/\D/g,"");
    if(num.length === 16){
        return num.replace(/(\d{4})(\d{4})(\d{4})(\d{4})/, "$1-XXXX-XXXX-$4");
    }
    if(num.length === 15){
        return num.replace(/(\d{4})(\d{6})(\d{5})/, "$1-XXXXXX-$3");
    }
    return card;
}

// 날짜 문자열을 "2026년 03월 13일" 로 변경, 6자리는 "2026년 03월" 로 변경
function formatDate(date){
    const str = String(date).trim();

    // yyyyMM
    if(/^\d{6}$/.test(str)){
        const year = str.substring(0,4);
        const month = str.substring(4,6);
        return `${year}년 ${month}월`;
    }

    // yyyy-MM-dd HH:mm:ss 또는 yyyy-MM-dd
    const match = str.match(/^(\d{4})-(\d{2})-(\d{2})/);
    if(match){
        const year = match[1];
        const month = match[2];
        const day = match[3];
        return `${year}년 ${month}월 ${day}일`;
    }
    
    return "-";
}

// 주민등록번호 문자열을 "123456-1******"로 변경
function formatResident(rrn){
    const num = String(rrn).replace(/\D/g,"");

    if(num.length === 13){
        return num.replace(/(\d{6})(\d{1})(\d{6})/, "$1-$2******");
    }
    
    return rrn;
}