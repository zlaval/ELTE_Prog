const generateBtn = document.querySelector("button")
const content = document.querySelector("div")

generateBtn.addEventListener("click", function () {
    const tags = content.querySelectorAll("h1, h2, h3, h4, h5, h6")

    let html = `<ul>`

    let actualLevel = 1
    for (let tag of tags) {
        let level = tag.nodeName.substring(1)
        console.log(level)

        if (level < actualLevel) {
            html += `</ul>`
        } else if (level > actualLevel) {
            html += `<ul>`
        }
        html += `<li>${tag.innerHTML}</li>`

        actualLevel = level
    }
    html += `</ul>`

    const toc = document.createElement("div")
    toc.innerHTML = html
    document.body.insertBefore(toc, generateBtn)

})