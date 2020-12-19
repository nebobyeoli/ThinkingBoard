// 각 페이지 화면으로 이동
// 마우스 이벤트

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
