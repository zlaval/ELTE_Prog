const todos = document.querySelector("#todos")

function handleStepClick(event) {
    const actualElement = event.target
    const nextElement=actualElement.nextElementSibling
    if(nextElement && nextElement.classList.contains("done")){
        return
    }
    actualElement.classList.toggle('done');
    let previous = actualElement.previousElementSibling
    while (previous) {
        previous.classList.add('done')
        previous = previous.previousElementSibling
    }
    checkIfAllDone(actualElement)
}

function checkIfAllDone(element){
    const parent=element.parentElement
    const allChild=parent.children

    for(let child of allChild){
        if(!child.classList.contains('done')){
            parent.parentElement.classList.remove('done')
            return
        }
    }
    parent.parentElement.classList.add('done')
}


delegate(todos, "click", ".step > ul > li", null, handleStepClick)






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
