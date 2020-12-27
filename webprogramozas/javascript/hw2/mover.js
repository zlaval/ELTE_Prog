function getRandomInteger(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

function getRandomTransition() {
    return Math.floor(Math.random() * 5 + 2) / 10
}

const maxX = window.innerWidth - 100;
const maxY = window.innerHeight - 100;

const hitBox = document.querySelector('.hit-box');
hitBox.style.left = "0px"
hitBox.style.top = "0px"

hitBox.addEventListener("mouseover", event => {

    const newX = getRandomInteger(0, maxX)
    const newY = getRandomInteger(0, maxY)
    const transitionTime = getRandomTransition()

    hitBox.style.left = `${newX}px`
    hitBox.style.top = `${newY}px`
    hitBox.style.transition = `all ${transitionTime}s linear`

})
