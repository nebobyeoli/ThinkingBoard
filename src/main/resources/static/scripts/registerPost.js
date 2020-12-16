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

let validate = {};

validate.invalPrint = function (areaname, alerttext) {
    if (validate.isValid) document.querySelector(`div.formarea.${areaname}`).scrollIntoView();
    if (document.querySelector(`div.formarea.${areaname} p.invalid`) != null) return;

    let inval = document.createElement('p');
    inval.setAttribute('class', 'invalid');

    inval.appendChild(createSVG({ viewBox: '0 0 20 20' }, [
        'M10,0C4.5,0,0,4.5,0,10s4.5,10,10,10s10-4.5,10-10S15.5,0,10,0z M10,18.5c-4.7,0-8.5-3.8-8.5-8.5S5.3,1.5,10,1.5 s8.5,3.8,8.5,8.5S14.7,18.5,10,18.5z',
        'M11.4,15c0,0.4-0.1,0.7-0.4,1c-0.3,0.3-0.6,0.5-1,0.5c-0.4,0-0.8-0.2-1-0.5c-0.3-0.3-0.4-0.6-0.4-1c0-0.4,0.1-0.7,0.4-1 c0.5-0.6,1.5-0.6,2,0C11.3,14.3,11.4,14.6,11.4,15z',
        'M8.6,3.8h2.8l-0.7,8.5c0,0-1.5,0-1.5,0S8.6,3.8,8.6,3.8z'
    ]));
    inval.innerHTML += alerttext;

    document.querySelector(`div.formarea.${areaname}`).appendChild(inval);
}

validate.all = function () {
    validate.isValid = true;
    validate.title();
    validate.category();
    validate.contents();
    validate.password();
    return validate.isValid;
};

validate.title = function () {
    let title = document.querySelector('input[type=text][name=title]').value;
    if (title == '') {
        validate.invalPrint('title', '제목을 입력해 주세요.');
        return validate.isValid = false;
    }
    return validate.isValid && true;
};

validate.category = function () {
    let catlength = document.querySelectorAll('input[type=checkbox][name=category]:checked').length;
    if (catlength == 0) {
        validate.invalPrint('category', '카테고리를 선택해 주세요.');
        return validate.isValid = false;
    }
    return validate.isValid === true;
}

validate.contents = function () {
    let contents = document.querySelector('textarea[name=contents]').value;
    if (contents == '') {
        validate.invalPrint('contents', '내용을 입력해 주세요.');
        return validate.isValid = false;
    }
    return validate.isValid === true;
}

validate.password = function () {
    let password = document.querySelector('input[type=password][name=password]').value;
    let password_check = document.querySelector('input[type=password][name=password_check]').value;

    if (password_check !== password) {
        validate.invalPrint('password', '비밀번호를 확인해 주세요.');
        return validate.isValid = false;
    }
    return validate.isValid === true;
};
