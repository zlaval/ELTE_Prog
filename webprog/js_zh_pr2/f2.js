const showHideBtn = document.querySelector("button")
const hiddenFields = document.querySelectorAll("td[data-hidden]")
const table = document.querySelector("table")

const elementName=document.querySelector("#name")
const symbol=document.querySelector("#symbol")
const number=document.querySelector("#number")

let hiddenValue = true
let selectedElement = null
let selectedElementOriginalStyle = null

showHideBtn.addEventListener("click", function () {
    hiddenValue = !hiddenValue
    for (let he of hiddenFields) {
        he.setAttribute("data-hidden", hiddenValue)
    }
})

function handleElementClick(event) {
    if (!event.target.innerHTML) {
        return
    }

    if (selectedElement) {
        selectedElement.style.background = selectedElementOriginalStyle
    }
    selectedElement = event.target
    selectedElementOriginalStyle = selectedElement.style.background
    selectedElement.style.background = "red"


    symbol.innerHTML=selectedElement.getAttribute("id")
    elementName.innerHTML=selectedElement.getAttribute("data-name")
    number.innerHTML=calculateElementNumber(selectedElement)
}

function calculateElementNumber(element){
    let tds=table.querySelectorAll("td[id]")
    let index=0
    while(tds[index] !== element) {
        index++
    }
    return index+1
}

delegate(table, "click", "td", "td", handleElementClick)

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
