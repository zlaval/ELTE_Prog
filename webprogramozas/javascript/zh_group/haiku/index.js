const haikuEditor = document.querySelector("#haiku-editor")
const numberOfCharacters = document.querySelector("#number-of-characters")
const numberOfRows = document.querySelector("#number-of-rows")
const vowelsPerRow = document.querySelector("#vowels-per-row")
const addHaikuBtn = document.querySelector("#btn-copy-haiku")
const haikuList = document.querySelector("#haikus")

addHaikuBtn.style.display = "none"

addHaikuBtn.addEventListener("click", function () {
    const text = haikuEditor.value
    const pre = document.createElement("pre")
    pre.innerHTML = text
    haikuList.appendChild(pre)

})

haikuEditor.addEventListener("input", function (event) {
    const text = haikuEditor.value
    console.log(text)
    numberOfCharacters.innerHTML = text.length
    const rows = text.split("\n")
    numberOfRows.innerHTML = rows.length
    console.log(`Magánhangzók száma: ${calculateVowelCount(rows[0])}`)
    showVowelsPerRow(rows)
    examineHaiku(rows)
})

function calculateVowelCount(text) {
    let sum = 0
    for (let i = 0; i < text.length; ++i) {
        const c = text.charAt(i)
        if ("aáeéiíoóöőuúüű".includes(c)) {
            sum++
        }
    }
    return sum
}

function showVowelsPerRow(rows) {
    let vowelsTag = ""
    for (let row of rows) {
        const sum = calculateVowelCount(row)
        vowelsTag += `<li>${sum}</li>`
    }
    vowelsPerRow.innerHTML = vowelsTag
}

function examineHaiku(rows) {
    if (rows.length === 3) {
        const first = calculateVowelCount(rows[0])
        const second = calculateVowelCount(rows[1])
        const third = calculateVowelCount(rows[2])
        if (first === 5 && second === 7 && third === 5) {
            haikuEditor.parentElement.classList.add("haiku")
            addHaikuBtn.style.display = ""
            return
        }
    }
    addHaikuBtn.style.display = "none"
    haikuEditor.parentElement.classList.remove("haiku")

}