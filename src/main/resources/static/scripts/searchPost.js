let checked = [];
let catboxVisible = false;

let searchType = document.getElementById('searchType').getAttribute('value');

document.querySelectorAll('input[type=radio][name=searchType]').forEach(radio => {
    if (radio.getAttribute('value') == searchType) {
        radio.setAttribute('checked', 'true');
        if (searchType == 'category') displayCatbox();
    }
});

// input:checkbox 삽입
document.querySelectorAll('td.catbox').forEach((td, i) => {
    let value = td.innerHTML;
    td.outerHTML = td.outerHTML.replace(/<\/td>/g, `<td><input type="checkbox" name="searchByCat" value="${value}"></td>`);
    if (td.classList.contains('checked')) checked.push(i);
});

document.querySelectorAll('table.catboxes input[type=checkbox]').forEach((check, i) => {
    if (checked.includes(i)) check.setAttribute('checked', 'true');
});

function displayCatbox() {
    if (catboxVisible) return;
    document.getElementById('catboxes').classList.remove('dispNone');
    document.getElementById('searchContent').classList.add('dispNone');
    catboxVisible = true;
}

function displaySearchContent() {
    if (!catboxVisible) return;
    document.getElementById('catboxes').classList.add('dispNone');
    document.getElementById('searchContent').classList.remove('dispNone');
    catboxVisible = false;
}
