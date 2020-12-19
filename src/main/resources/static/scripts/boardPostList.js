const transDur = getTransitionDur();

const ppost = document.querySelectorAll('div.paper.post');
const ppostCat = document.querySelectorAll('div.paper.post > span[name=post-category]');

let postopt = {
    expanded: [], // 옵션 선택자(⋮)에 사용되는 배열 -- 0: 안 열림, 1: 열림
    panel: i => document.querySelector(`div.paper.post div.postopt-panel._${i}`),
    panelAll: () => document.querySelectorAll('div.postopt-panel')
};

let deletePost = {
    outer: document.querySelector('div.delPost'),
    inpwd: document.querySelector('div.delPost input[name=password]'),
    panel: document.querySelector('div.delPost div.delPost-form'),
    fill: document.querySelector('div.delPost div.backfill')
};

document.body.addEventListener('click', e => {
    if (e.target.tagName == 'svg') return; // if no-exclude: too many bugs.
    if (e.target.classList.contains('postopt-panel') || e.target.parentElement.classList.contains('postopt-panel')) return;
    postopt.closeAll();
});

/*** for Object : postopt ***/

for (let i = 0; i < ppost.length; i++) {
    postopt.expanded.push(0);
}

// 카테고리들을 화면에 나눠서 표시
ppostCat.forEach(span => {
    let categories = span.innerHTML.split('/');

    span.innerHTML = '';
    categories.forEach(category => {
        span.innerHTML += `<pre name="cat-${category}" class="block">${category}</pre>`;
    });
});

ppost.forEach((post, i) => {
    // SVG : 옵션 선택자(⋮)
    // postopt-panel : 글 수정 / 글 삭제 선택 패널
    post.innerHTML += `
        <svg class="postopt-svg" viewBox="0 0 20 20" onclick="postopt.toggle(${i})">
            <path d="M12,9.1v1.8c0,0.6-0.5,1.1-1.1,1.1H9.1C8.5,12,8,11.5,8,10.9V9.1C8,8.5,8.5,8,9.1,8h1.8C11.5,8,12,8.5,12,9.1z"></path>
            <path d="M12,17v1.8c0,0.6-0.5,1.1-1.1,1.1H9.1c-0.6,0-1.1-0.5-1.1-1.1V17c0-0.6,0.5-1.1,1.1-1.1h1.8C11.5,15.9,12,16.4,12,17z"></path>
            <path d="M12,1.2V3c0,0.6-0.5,1.1-1.1,1.1H9.1C8.5,4.1,8,3.6,8,3V1.2c0-0.6,0.5-1.1,1.1-1.1h1.8C11.5,0.1,12,0.6,12,1.2z"></path>
        </svg>
        <div class="postopt-panel _${i}" style="display: none;">
            <p onclick="editPost(${i})">글 수정</p>
            <p onclick="deletePost.open(${i})">글 삭제</p>
        </div>
    `;
});

postopt.closeAll = function () {
    this.panelAll().forEach(panel => panel.style.display = 'none');
    this.expanded.fill(0);
}

postopt.toggle = function (i) {
    let opened = this.expanded[i];
    this.closeAll();

    if (opened == 0) {
        this.panel(i).style.display = '';
        this.expanded[i] = 1;
    }
    else {
        this.panel(i).style.display = 'none';
        this.expanded[i] = 0;
    }
}

/*** for Object : editPost ***/

function editPost(i) {
    postopt.closeAll();
}

/*** for Object : deletePost ***/

deletePost.outer.addEventListener('keydown', e => {
    if (e.keyCode == 27) deletePost.close(); // Esc key
});

deletePost.open = function (i) {
    postopt.closeAll();

    this.outer.setAttribute('pk', i); // primary key -- id
    this.inpwd.focus();

    this.outer.toggleAttribute('display');
    this.fill.toggleAttribute('display');

    this.outer.toggleAttribute('active');
    this.panel.toggleAttribute('active');
}

deletePost.close = function () {
    this.panel.toggleAttribute('active');
    this.outer.toggleAttribute('active');

    setTimeout(() => {
        this.fill.toggleAttribute('display');
        this.outer.toggleAttribute('display');
    }, transDur);
}
