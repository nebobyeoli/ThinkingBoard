// 배경 디자인 위치 고정
document.body.innerHTML = `<div id="body-fill" class="fullscreen"></div>${document.body.innerHTML}`;

function getTransitionDur() {
    let sec = getComputedStyle(document.body).transitionDuration;
    let mil = Number(sec.replace('s', '')) * 1000;
    return mil;
}

// visible element의 { width: offsetWidth, height: offsetHeight } 반환
// takes time to execute ; js does not wait for finish
function getSize(el) {
    let cmp = getComputedStyle(el);
    let border = [cmp.borderTop, cmp.borderRight, cmp.borderBottom, cmp.borderLeft].map(x=>Number(x.split('px')[0])); // cmp.border : px, style, (rgb/rgba)
    let margin = [cmp.marginTop, cmp.marginRight, cmp.marginBottom, cmp.marginLeft].map(x=>Number(x.split('px')[0]));

    let shadow = cmp.boxShadow; // x, y, blur, radius, (rgb/rgba)

    if (shadow != 'none') { // shadow is object (=/= string)
        shadow = shadow
            .replace(/(?<=rgb\(|rgba\()(.*)(?=\))/gi, '')  // remove rgb/rgba values
            .replace(/rgba\(\) |rgb\(\) /gi, '')           // remove rgb/rgba
            .split(/px |px/gi).filter(x => x != '').map(x => Number(x)); // convert to numbers
        
        shadow = { x: shadow[0]+shadow[2]+shadow[3], y: shadow[1]+shadow[2]+shadow[3] };
    }
    else shadow = { x: 0, y: 0 };

    // size of full "wrapper"
    let _x = border[1]+border[3] + margin[1]+margin[3] - shadow.x;
    let _y = border[0]+border[2] + margin[0]+margin[2] - shadow.y;

    let size = {
        width: el.clientWidth - _x + 'px',
        height: el.clientHeight - _y + 'px'
    };
    console.log(el.clientWidth, el.clientHeight)
    console.log(size)
    return size;
}

// p.inval에 (!) SVG 추가
function appendInvalSVG(target) {
    target.appendChild(createSVG({ viewBox: '0 0 20 20' }, [
        'M10,0C4.5,0,0,4.5,0,10s4.5,10,10,10s10-4.5,10-10S15.5,0,10,0z M10,18.5c-4.7,0-8.5-3.8-8.5-8.5S5.3,1.5,10,1.5 s8.5,3.8,8.5,8.5S14.7,18.5,10,18.5z',
        'M11.4,15c0,0.4-0.1,0.7-0.4,1c-0.3,0.3-0.6,0.5-1,0.5c-0.4,0-0.8-0.2-1-0.5c-0.3-0.3-0.4-0.6-0.4-1c0-0.4,0.1-0.7,0.4-1 c0.5-0.6,1.5-0.6,2,0C11.3,14.3,11.4,14.6,11.4,15z',
        'M8.6,3.8h2.8l-0.7,8.5c0,0-1.5,0-1.5,0S8.6,3.8,8.6,3.8z'
    ]));
}

function createSVG(attributes = {}, path_ds = [], path_fills = [], path_strokes = []) {
    let svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    Object.entries(attributes).map(attr => svg.setAttribute(attr[0], attr[1]));

    if (path_ds) path_ds.forEach((dss, i) => {

        let path = document.createElementNS(svg.namespaceURI, 'path');

        path.setAttribute('d', dss);

        if (path_fills[i]) path.setAttribute('fill', path_fills[i]);
        if (path_strokes[i]) path.setAttribute('stroke', path_strokes[i]);

        svg.appendChild(path);
    });

    return svg;
}

function isPaperPost(elem) {
    let pp = elem;
    while (true) {
        if (!pp.classList.contains('paper') || !pp.classList.contains('post') || pp.tagName != 'div') {
            if (pp.tagName == 'BODY') return false;
            pp = pp.parentElement;
        }
        else return pp;
    }
}
