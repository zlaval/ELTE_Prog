const canvas = document.querySelector('canvas');
const context = canvas.getContext('2d');

const bmPlus = document.querySelector("#bm-plus-btn")
const bmMinus = document.querySelector("#bm-minus-btn")
const sptyPlus = document.querySelector("#spty-plus-btn")
const sptyMinus = document.querySelector("#spty-minus-btn")

let prevMac = 250
let bigMacPrice = 250
let bigMacDay = 0
let prevSpoti = 300
let spotiPrice = 300
let spotiDay = 0


bmPlus.addEventListener("click", function () {
    const prevoiusPrice = bigMacPrice
    bigMacPrice += 10
    const writeValue = prevMac === bigMacPrice
    prevMac = prevoiusPrice
    bigMacDay++
    renderBigMac(prevoiusPrice, writeValue)
})


bmMinus.addEventListener("click", function () {
    const prevoiusPrice = bigMacPrice
    bigMacPrice -= 10
    const writeValue = prevMac === bigMacPrice
    prevMac = prevoiusPrice
    bigMacDay++
    renderBigMac(prevoiusPrice, writeValue)
})

sptyPlus.addEventListener("click", function () {
    const prevoiusPrice = spotiPrice
    spotiPrice += 10
    const writeValue = prevSpoti === spotiPrice
    prevSpoti = prevoiusPrice
    spotiDay++
    renderSpotify(prevoiusPrice, writeValue)
})

sptyMinus.addEventListener("click", function () {
    const prevoiusPrice = spotiPrice
    spotiPrice -= 10
    const writeValue = prevSpoti === spotiPrice
    prevSpoti = prevoiusPrice
    spotiDay++
    renderSpotify(prevoiusPrice, writeValue)
})

function renderBigMac(previousPrice, writeValue) {
    const fromX = (bigMacDay - 1) * 10
    const toX = (bigMacDay) * 10
    context.beginPath()
    context.moveTo(fromX, 600 - previousPrice)
    context.lineTo(toX, 600 - bigMacPrice)
    context.strokeStyle = 'red';
    context.stroke()
    if (writeValue) {
        context.fillText(previousPrice, fromX,  600 - previousPrice);
    }
}

function renderSpotify(previousPrice, writeValue) {
    const fromX = (spotiDay - 1) * 10
    const toX = (spotiDay) * 10
    context.beginPath()
    context.moveTo(fromX, 600 - previousPrice)
    context.lineTo(toX, 600 - spotiPrice)
    context.strokeStyle = 'blue';
    context.stroke()
    if (writeValue) {
        context.fillText(previousPrice,  fromX,  600 - previousPrice);
    }
}


