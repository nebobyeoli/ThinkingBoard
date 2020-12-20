// input:checkbox 삽입

let checked = [];

let getCategory = document.getElementById('getCategory');

if (getCategory) { // 기존 게시글 수정 페이지면
    let categories = getCategory.getAttribute('value').split('/');

    document.querySelectorAll('td.catbox').forEach(td => {
        if (categories.includes(td.innerHTML)) td.classList.add('checked');
    });
}

document.querySelectorAll('td.catbox').forEach((td, i) => {
    let value = td.innerHTML;
    td.outerHTML = td.outerHTML.replace(/<\/td>/g, `<td><input type="checkbox" name="category" value="${value}"></td>`);
    if (td.classList.contains('checked')) checked.push(i);
});

document.querySelectorAll('table.catboxes input[type=checkbox]').forEach((check, i) => {
    if (checked.includes(i)) check.setAttribute('checked', 'true');
});

// 주어진 form 입력이 입력 조건을 모두 충족하는지 확인

let validate = { isValid: true };

validate.invalPrint = function (areaname, alerttext) {
    if (validate.isValid) document.querySelector(`div.formarea.${areaname}`).scrollIntoView();
    if (document.querySelector(`div.formarea.${areaname} p.invalid`) != null) return;

    let inval = document.createElement('P');
    inval.setAttribute('class', 'invalid');
    appendInvalSVG(inval);
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
    return validate.isValid === true;
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
