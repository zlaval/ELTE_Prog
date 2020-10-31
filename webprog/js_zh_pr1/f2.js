
const data = document.querySelector('#adatok')

function handleClickOnList(event) {
    event.target.parentElement.classList.toggle('erkezes');
}

function handleClickOnSelf(event) {
    event.target.parentElement.classList.toggle('polc');
}

function handleClickOnItem(event) {
    event.target.classList.toggle('termek');
}

delegate(data, 'click', '.szallitmany', '.szallitmany > div:first-of-type ', handleClickOnList)
delegate(data, 'click', '.szallitmany', '.szallitmany > div:last-of-type ', handleClickOnSelf)
delegate(data, 'click', '.szallitmany > div > ul > li', null, handleClickOnItem)

function delegate(parent, type, selector, eventSource, handler) {
    parent.addEventListener(type, function (event) {
        const eventElement = event.target.closest(eventSource);
        const targetElement = event.target.closest(selector);
        if (!eventSource || eventElement === event.target) {
            if (this.contains(targetElement)) {
                handler.call(targetElement, event);
            }
        }
    })
}