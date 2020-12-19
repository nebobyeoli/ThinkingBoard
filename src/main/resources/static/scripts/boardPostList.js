// 옵션 선택자(⋮)에 사용되는 배열
let postopt_opened = []; // 0: 안 열림, 1: 열림
for (let i = 0; i < document.querySelectorAll('div.paper.post').length; i++) postopt_opened.push(0)

// 카테고리들을 화면에 나눠서 표시
document.querySelectorAll('div.paper.post > span[name=post-category]').forEach(span => {
    let categories = span.innerHTML.split('/');

    span.innerHTML = '';
    categories.forEach(category => {
        span.innerHTML += `<pre name="cat-${category}" class="block">${category}</pre>`;
    });
});

document.querySelectorAll('div.paper.post').forEach((post, i) => {
    // 옵션 선택자(⋮) SVG
    post.appendChild(createSVG({
        class: `postopt-svg _${i}`,
        viewBox: '0 0 20 20',
        onclick: `openclose_postopt(${i})`
    }, [
        'M12,9.1v1.8c0,0.6-0.5,1.1-1.1,1.1H9.1C8.5,12,8,11.5,8,10.9V9.1C8,8.5,8.5,8,9.1,8h1.8C11.5,8,12,8.5,12,9.1z',
        'M12,17v1.8c0,0.6-0.5,1.1-1.1,1.1H9.1c-0.6,0-1.1-0.5-1.1-1.1V17c0-0.6,0.5-1.1,1.1-1.1h1.8C11.5,15.9,12,16.4,12,17z',
        'M12,1.2V3c0,0.6-0.5,1.1-1.1,1.1H9.1C8.5,4.1,8,3.6,8,3V1.2c0-0.6,0.5-1.1,1.1-1.1h1.8C11.5,0.1,12,0.6,12,1.2z'
    ]));
    post.classList.add(`_${i}`);

    // postopt-panel : 글 수정 / 글 삭제 선택 패널
    post.innerHTML += `
        <div class="postopt-panel _${i}" style="display: none;">
            <p onclick="editPost(${i})">글 수정</p>
            <p onclick="deletePost.panel(${i})">글 삭제</p>
        </div>
    `;
});

// // Previous
//
// function postopt-panel(i) {
//     let pp = document.querySelector(`div.paper.post._${i}`);
//     if (postopt_opened[i] == 0) {
//         pp.innerHTML += `<div class="postopt-panel _${i}" onclick="postopt-panel()"><p>글 수정</p><p>글 삭제</p></div>`;
//         postopt_opened[i] = 1;
//     }
// }

function openclose_postopt(i) {
    let opened = postopt_opened[i];
    closeAll_postopt();

    if (opened == 0) {
        document.querySelector(`div.paper.post._${i} div.postopt-panel`).style.removeProperty('display');
        postopt_opened[i] = 1;
    }
    else {
        document.querySelector(`div.paper.post._${i} div.postopt-panel`).style.setProperty('display', 'none');
        postopt_opened[i] = 0;
    }
}

function closeAll_postopt() {
    document.querySelectorAll('div.postopt-panel').forEach(panl => panl.style.setProperty('display', 'none'));
    postopt_opened.fill(0);
}

document.body.addEventListener('click', e => {
    if (e.target.tagName == 'svg') return; // if no-exclude: 'many' bugs
    if (e.target.classList.contains('postopt-panel') || e.target.parentElement.classList.contains('postopt-panel')) return;
    closeAll_postopt();
});

// // Previous
//
// document.body.addEventListener('click', e => {
//     let pp = isPaperPost(e.target);
//     console.log(pp, e.target.parentElement)
//     console.log(e.target.classList.contains('postopt'))
//     console.log(e.target.tagName == 'svg')
//
//     if (pp && e.target.classList.contains('postopt') && e.target.tagName == 'svg') {
//         console.log('classlist',e.target.classList.value.split('_')[1])
//         if (postopt_opened[e.target.classList.value.split('_')[1]] == 0) {
//             pp.innerHTML += '<div class="postopt-panel" onclick="postopt-panel()"><p>글 수정</p><p>글 삭제</p></div>';
//         }
//     }
// });

function editPost(i) {
    closeAll_postopt();
}

let deletePost = {};

deletePost.transitionDur = getTransitionDur();

deletePost.panel = function (i) {
    closeAll_postopt();

    let dp = document.querySelector('div.delPost');
    let df = document.querySelector('div.delPost div.delPost-form');

    dp.setAttribute('pk', i);

    new Promise(resolve => { // resolve, reject
        dp.style.setProperty('display', 'flex');
        let dp_h = getSize(dp).height;
        let df_h = getSize(df).height; // value is 0 while display: none;

        resolve([ dp_h, df_h ]);

    }).then(orig_hs => {
        dp.style.setProperty('height', '0');
        df.style.setProperty('height', '0');

        return orig_hs;

    }).then(([dp_h, df_h]) => new Promise(resolve => {
        setTimeout(() => {
        // Reason for setTimeout: https://stackoverflow.com/a/3580703
            dp.style.setProperty('opacity', '1')

            dp.style.setProperty('height', dp_h);
            df.style.setProperty('height', df_h); /* 'max-content' numeric alt. */
        }, 100);
    }));
}

// // Previous
//
// deletePost.panel = function (i) {
//     closeAll_postopt();
//
//     let dp = document.querySelector('div.delPost');
//     let df = document.querySelector('div.delPost div.delPost-form');
//
//     dp.setAttribute('pk', i);
//     dp.style.setProperty('display', 'flex');
//     dp.style.setProperty('height', '0');
//
//     let df_height = getSize(df).height; // value is 0 while display: none;
//     df.style.setProperty('height', '0');
//
//     setTimeout(() => {
//         dp.style.setProperty('opacity', '1');
//         dp.style.setProperty('height', '100%');
//         df.style.setProperty('height', df_height); /* 'max-content' numeric alt. */
//     }, 100);
// }

deletePost.close = function () {
    let dp = document.querySelector('div.delPost');
    let df = document.querySelector('div.delPost div.delPost-form');

    new Promise(resolve => { // resolve, reject
        let dp_h = getSize(dp).height;
        let df_h = getSize(df).height; // value is 0 while display: none;

        resolve([ dp_h, df_h ]);

    }).then(orig_hs => new Promise(resolve => {
    // How to use setTimeout on Promise chain: https://stackoverflow.com/a/39538518

        dp.style.setProperty('opacity', '0');
        dp.style.setProperty('height', '0');
        df.style.setProperty('height', '0');

        setTimeout(() => resolve(orig_hs), this.transitionDur);

    })).then(([dp_h, df_h]) => {
        dp.style.setProperty('display', 'none');
        dp.style.setProperty('height', dp_h);
        df.style.setProperty('height', df_h);

        return df_h;
    });
}

// // Previous
//
// deletePost.close = function () {
//     let dp = document.querySelector('div.delPost');
//     let df = document.querySelector('div.delPost div.delPost-form');
//
//     let df_height = getSize(df).height;
//
//     new Promise((res, rej) => setTimeout(() => {
//         dp.style.setProperty('opacity', '0');
//         dp.style.setProperty('height', '0');
//         df.style.setProperty('height', '0');
//
//     }, 100)).then(() => {
//         dp.style.setProperty('display', 'none');
//         dp.style.setProperty('height', '100%');
//         df.style.setProperty('height', df_height);
//     });
// }
