const canvas = document.querySelector('canvas');
const context = canvas.getContext('2d');

const changeBtn = document.querySelector("#btn-change")
const animationBtn = document.querySelector("#btn-animation")

let array = []

console.log(random(-10, 10))
fillArray()
drawLine()


function random(min, max) {
    const mx = max + 1
    return Math.floor(Math.random() * (mx - min) + min)
}


function fillArray() {

    for (let i = 0; i < 20; ++i) {
        array.push(random(-5, 5))
    }
    console.log(array)
}

function drawLine() {
    let x = 0
    let y = 105

    for (let v of array) {
        context.beginPath()
        context.moveTo(x, y)
        context.lineTo(x + 10, y + v)
        context.strokeStyle = 'grey'
        context.lineWidth = 3
        context.stroke()
        x = x + 10
        y = y + v
    }

    context.beginPath()
    context.moveTo(x, y)
    context.lineTo(210, 105)
    context.strokeStyle = 'grey'
    context.lineWidth = 3
    context.stroke()
}

changeBtn.addEventListener("click", function () {
    context.clearRect(0, 0, canvas.width, canvas.height)
    array = array.map(e => e + random(-1, 1))
    drawLine()
})

animationBtn.addEventListener("click",animate)


function animate(){
    requestAnimationFrame(animate);
    context.clearRect(0, 0, canvas.width, canvas.height)
    array = array.map(e => e + random(-1, 1))
    drawLine()
}