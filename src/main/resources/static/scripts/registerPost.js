// input:checkbox 삽입

let checked = [];

document.querySelectorAll('td.catbox').forEach((td, i) => {
    let value = td.innerHTML;
    td.outerHTML = td.outerHTML.replace(/<\/td>/g, `<td><input type="checkbox" name="category" value="${value}"></td>`);
    if (td.classList.contains('checked')) checked.push(i);
});

document.querySelectorAll('table.catboxes input[type=checkbox]').forEach((check, i) => {
    if (checked.includes(i)) check.setAttribute('checked', 'true');
});
