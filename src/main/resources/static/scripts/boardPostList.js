// 카테고리들을 화면에 나눠서 표시
document.querySelectorAll('div.paper.post > span[name=post-category]').forEach(span => {
    let categories = span.innerHTML.split('/');

    span.innerHTML = '';
    categories.forEach(category => {
        span.innerHTML += `<pre name="cat-${category}" class="block">${category}</pre>`;
    });
});
