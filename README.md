# ThinkingBoard

`All css/js are self-written; no external dependencies.`

`Java - Spring Boot`

<i>`Ham teacher is love, ham teacher is life`</i>

### On clone use:

Rename `application.properties_editOnUse` to `application.properties`

Change `datasource` password

### Notes to self

### <ins> [How to pass model attributes between Spring MVC controllers](https://stackoverflow.com/a/17346284) </ins>

<details>
<summary>

#### How to `Promise` chain:

</summary>

```javascript
new Promise((resolve, reject) => { 
    // Do something <...>
    let result = 'result'; // any result
    resolve(result);
    
}).then(result => {
    // Do something with the `result` <...>
    let pass = `wooked on ${result}`;
    return pass;
    
}).finally(() => {
    // Do something finally <...>
    // executes on either `resolve` or `reject` <doesn't matter>
    
    return 'sth this ended';
});
```
> `end`
</details>

<details>
<summary>

#### How to `transition` style:

</summary>

```css
* { transition: 400ms; }

button { font-size: 2em; margin-bottom: 1em; padding: .5em;}

#sth {
    background-color: lightpink;
    padding: 1em;
    height: 0;
}

#sth.active {
    /* stuff to transit */
    height: 40em;
    background-color: lavender;
}
```
```html
<button onclick="sth.doTransition()">do transition</button>
<div id="sth"></div>
```
```javascript
let sth = {};

sth.tdur = getComputedStyle(document.body).transitionDuration;
sth.tdur = Number(sth.tdur.replace('s', '')) * 1000; // 400

sth.doTransition = function () {
    document.getElementById('sth').classList.toggle('active');
    
    setTimeout(() => console.log('transition has wooked | resolve(value)'), this.tdur);
}
```
> `end`
</details>

<details>
<summary>

#### How to `position` `center`:

</summary>

```html
<div>
    [something text]
    <p>[something item]</p>
    <pre>[something pre]</pre>
</div>
```
```css
div {
    align-items: center; /* vertical */
    justify-content: center; /* horizontal */

    position: fixed;
    top: 0; left: 0;
    width: 100%; height: 100%;
    display: flex;
}
```
> `end`
</details>
