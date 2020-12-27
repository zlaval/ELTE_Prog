const canvas = document.querySelector('canvas');
const context = canvas.getContext('2d');

let x = 0;
let xVelocity = 0;
let yVelocity = 0;

const rectangle = {
    width: 80,
    height: 80,
    xVelocity: 0,
    yVelocity: 0
};

let rectangleX = 0
let rectangleY = 0

function render() {
    requestAnimationFrame(render);
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.beginPath();
    context.fillStyle = "blue";
    context.fillRect(rectangleX, rectangleY, rectangle.width, rectangle.height);

    context.beginPath();
    context.fillStyle = "white";
    context.fillRect(rectangleX+20, rectangleY+20, 10, 10)
    context.fillRect(rectangleX+rectangle.width-30, rectangleY+20, 10, 10)
    context.fillStyle = "red";
    context.fillRect(rectangleX+20, rectangleY+50, 10, 10)
    context.fillRect(rectangleX+30, rectangleY+60, 10, 10)
    context.fillRect(rectangleX+40, rectangleY+60, 10, 10)
    context.fillRect(rectangleX+50, rectangleY+50, 10, 10)

    const newRectangleX = rectangleX + rectangle.xVelocity;

    const newRectangleY = rectangleY + rectangle.yVelocity;

    if (newRectangleX + rectangle.width <= canvas.width && newRectangleX >= 0) {
        rectangleX = newRectangleX;
    }

    if (newRectangleY + rectangle.height <= canvas.height && newRectangleY >= 0) {
        rectangleY = newRectangleY;
    }
}

render();

window.addEventListener('keydown', function (event) {
    if (rectangle.xVelocity === 0) {
        if (event.key === 'ArrowRight') {
            rectangle.xVelocity = 3;
        }
        else if (event.key === 'ArrowLeft') {
            rectangle.xVelocity = -3;
        }
    }
    if(rectangle.yVelocity===0){
        if (event.key === 'ArrowUp') {
            rectangle.yVelocity = -3;
        }else if (event.key === 'ArrowDown') {
            rectangle.yVelocity = +3;
        }
    }
});

window.addEventListener('keyup', function (event) {
    if (event.key === 'ArrowRight' && rectangle.xVelocity > 0 || event.key === 'ArrowLeft' && rectangle.xVelocity < 0) {
        rectangle.xVelocity = 0;
    }
    if (event.key === 'ArrowDown' && rectangle.yVelocity > 0 || event.key === 'ArrowUp' && rectangle.yVelocity < 0) {
        rectangle.yVelocity = 0;
    }
});
