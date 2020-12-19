// 배경 디자인 위치 고정
document.body.innerHTML = `
    <div id="body-fill" class="fullscreen"></div>
    ${document.body.innerHTML}
`;

function getTransitionDur() {
    let sec = getComputedStyle(document.body).transitionDuration;
    let mil = Number(sec.split('s')[0]) * 1000; // First attr of transition-duration
    return mil;
}

// p.inval에 (!) SVG 추가
function appendInvalSVG(target) {
    target.innerHTML += `
        <svg viewBox="0 0 20 20">
            <path d="M10,0C4.5,0,0,4.5,0,10s4.5,10,10,10s10-4.5,10-10S15.5,0,10,0z M10,18.5c-4.7,0-8.5-3.8-8.5-8.5S5.3,1.5,10,1.5 s8.5,3.8,8.5,8.5S14.7,18.5,10,18.5z"></path>
            <path d="M11.4,15c0,0.4-0.1,0.7-0.4,1c-0.3,0.3-0.6,0.5-1,0.5c-0.4,0-0.8-0.2-1-0.5c-0.3-0.3-0.4-0.6-0.4-1c0-0.4,0.1-0.7,0.4-1 c0.5-0.6,1.5-0.6,2,0C11.3,14.3,11.4,14.6,11.4,15z"></path><path d="M8.6,3.8h2.8l-0.7,8.5c0,0-1.5,0-1.5,0S8.6,3.8,8.6,3.8z"></path>
        </svg>
    `;
}
