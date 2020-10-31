const dlSpeed = document.querySelector("#download-speed-txt")
const dlBtn = document.querySelector("#download-speed-btn")

const tbody = document.querySelector("tbody")

let round = 1

dlSpeed.addEventListener("input", function () {
    console.log("reset round")
    round = 1
})

dlBtn.addEventListener("click", function () {
    const downloaded = round * dlSpeed.value
    for (let row of tbody.rows) {
        const sizeTxt = row.cells[1].innerHTML
        const size = sizeTxt.substring(0, sizeTxt.length - 3)
        let downloadedPercentage = downloaded / size * 100

        if (downloadedPercentage > 100) {
            downloadedPercentage = 100
        }

        row.cells[2].innerHTML=`${downloadedPercentage}%`


        console.log(downloadedPercentage)
    }

    round++;
});