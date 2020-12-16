// 배경 디자인 위치 고정
document.body.innerHTML = `<div id="body-fill"></div>${document.body.innerHTML}`;

let preblock = `<pre class="goto">이동하기</pre>`;

document.body.addEventListener('mouseover', e => {
    if (e.target.classList.contains('redir') && e.target.tagName == 'P') {
        if (e.target.innerHTML.slice(-preblock.length) != preblock) {
            e.target.innerHTML += preblock;
        }
    }
});

document.body.addEventListener('mouseout', e => {
    if (e.target.classList.contains('redir') && e.target.tagName == 'P') {
        if (e.target.innerHTML.slice(-preblock.length) == preblock) {
            e.target.innerHTML = e.target.innerHTML.slice(0, -preblock.length);
        }
    }
});

document.body.addEventListener('click', e => {
    if (e.target.classList.contains('redir') && e.target.tagName == 'P') {
        let href = e.target.getAttribute('href');
        window.open(href, '_self');
    }
});

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
