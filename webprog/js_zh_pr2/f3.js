const canvas = document.querySelector('canvas');
const context = canvas.getContext('2d');
const imgScale = document.querySelector("#img-scale")

function renderImage(scale = 1) {
    const height = imgData.length
    const width = imgData[0].length

    canvas.width = width * scale
    canvas.height = height * scale

    for (let y = 0; y < height; y++) {
        for (let x = 0; x < width; ++x) {
            const pixelColor = imgData[y][x]
            context.fillStyle = `rgb(${pixelColor},${pixelColor},${pixelColor})`
            context.fillRect(x * scale, y * scale, scale, scale)
        }
    }
}

imgScale.addEventListener("input", function () {
    let value = imgScale.value
    if (!value || value < 1) {
        value = 1
    }
    renderImage(value)
})


renderImage()