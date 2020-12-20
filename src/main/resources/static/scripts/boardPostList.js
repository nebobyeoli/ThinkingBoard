const transDur = getTransitionDur();

const ppost = document.querySelectorAll('div.paper.post');
const ppostCat = document.querySelectorAll('div.paper.post > span[name=post-category]');

let pkList = [];
ppost.forEach(pp => pkList.push(pp.getAttribute('pk')));

let postopt = {
    active: [], // 옵션 선택자(⋮)가 열렸는지 저장하는 배열
    panel: i => document.querySelector(`div.paper.post div.postopt-panel._${i}`),
    panelAll: () => document.querySelectorAll('div.postopt-panel')
};

let modifyPost = {
    active: false, // panel이 열려 있는 상태에서 Esc키 계속 누르고 있을 때 toggle이 계속 실행되는 것 방지

    outer: document.querySelector('div.modPost'),
    panel: document.querySelector('div.modPost div.modPost-form'),
    fill: document.querySelector('div.modPost div.backfill'),

    form: document.querySelector('div.modPost form'),
    inpwd: document.querySelector('div.modPost input[name=password]'),

    result: document.getElementById('modOnePostResult'),
    showResult: document.getElementById('displayModPostResult')
};

document.body.addEventListener('click', e => {
    if (e.target.tagName == 'svg') return; // if no-exclude: too many bugs.
    if (e.target.classList.contains('postopt-panel') || e.target.parentElement.classList.contains('postopt-panel')) return;
    postopt.closeAll();
});

/*** for Object : postopt ***/

for (let i = 0; i < ppost.length; i++) {
    postopt.active.push(false);
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
            <p onclick="modifyPost.goEdit(${i})">글 수정</p>
            <p onclick="modifyPost.goDelete(${i})">글 삭제</p>
        </div>
    `;
});

postopt.closeAll = function () {
    this.panelAll().forEach(panel => panel.style.display = 'none');
    this.active.fill(0);
}

postopt.toggle = function (i) {
    let opened = this.active[i];
    this.closeAll();

    if (!opened) {
        this.panel(i).style.display = '';
        this.active[i] = true;
    }
    else {
        this.panel(i).style.display = 'none';
        this.active[i] = false;
    }
}

/*** for Object : modifyPost ***/

modifyPost.outer.addEventListener('keydown', e => {
    if (e.keyCode == 27) modifyPost.close(); // Esc key
});

modifyPost.goEdit = function (i) {
    this.outer.setAttribute('modify', 'edit');
    this.form.action = '/posts/editOne';
    document.querySelector('div.modPost button[type=submit]').innerHTML = '수정';
    document.querySelector('div.modPost p.invalid').style.display = 'none';
    this.open(i);
}

modifyPost.goDelete = function (i) {
    this.outer.setAttribute('modify', 'delete');
    // this.outer.modify = 'goDelete';
    this.form.action = '/posts/deleteOne';
    document.querySelector('div.modPost button[type=submit]').innerHTML = '삭제';
    document.querySelector('div.modPost p.invalid').style.display = '';
    this.open(i);
}

modifyPost.getResult = function () {
    let modPk = this.result.getAttribute('modPk');
    let modType = this.result.getAttribute('modType');
    let modResult = this.result.getAttribute('modResult');
    console.log('modPk:', modPk);
    console.log('modType:', modType);
    console.log('modResult:', modResult);

    if (modType == null) return;

    if (modResult == 'true') {
        this.showResult.style.display = '';
        this.showResult.innerHTML += `글 ${modType=='edit' ? '수정이' : '삭제가'} 완료되었습니다.`;
    }
    else {
        this.showResult.innerHTML += `비밀번호가 잘못되었습니다.`;
        if (modType == 'edit') this.goEdit(pkList.indexOf(modPk));
        else this.goDelete(pkList.indexOf(modPk));
        document.querySelector('div.modPost div.invCase').innerHTML += `
            <p class="invalid wrongPwd">
                ${this.showResult.innerHTML}
            </p>
        `;
    }
}

modifyPost.open = function (i) {
    if (this.active) return;

    postopt.closeAll();

    document.getElementById('pk').value = pkList[i]; // primary key -- id
    this.inpwd.focus();

    this.outer.toggleAttribute('display');
    this.fill.toggleAttribute('display');

    this.outer.toggleAttribute('active');
    this.panel.toggleAttribute('active');

    this.active = true;
}

modifyPost.close = function () {
    if (!this.active) return;

    this.panel.toggleAttribute('active');
    this.outer.toggleAttribute('active');

    setTimeout(() => {
        this.fill.toggleAttribute('display');
        this.outer.toggleAttribute('display');

        let hasWrong = document.querySelector('div.modPost p.invalid.wrongPwd');
        if (hasWrong) hasWrong.remove();

        this.active = false;
    }, transDur);
}
