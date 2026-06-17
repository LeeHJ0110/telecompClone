const track = document.querySelector(".hero-track");
const slides = document.querySelectorAll(".hero-slide");
const prev = document.querySelector(".hero-prev");
const next = document.querySelector(".hero-next");
const dotsContainer = document.querySelector(".hero-dots");

let index = 0;
let slideCount = slides.length;

/* dots 생성 */

for(let i=0;i<slideCount;i++){

    const dot = document.createElement("div");
    dot.classList.add("hero-dot");

    if(i===0) dot.classList.add("active");

    dot.addEventListener("click",()=>{
        index=i;
        updateSlide();
    });

    dotsContainer.appendChild(dot);
}

const dots = document.querySelectorAll(".hero-dot");

function updateSlide(){

    track.style.transform = `translateX(-${index*100}%)`;

    dots.forEach(d=>d.classList.remove("active"));
    dots[index].classList.add("active");

}

/* next */

next.addEventListener("click",()=>{

    index++;

    if(index>=slideCount) index=0;

    updateSlide();

});

/* prev */

prev.addEventListener("click",()=>{

    index--;

    if(index<0) index=slideCount-1;

    updateSlide();

});

/* 자동 슬라이드 */

setInterval(()=>{

    index++;

    if(index>=slideCount) index=0;

    updateSlide();

},5000);