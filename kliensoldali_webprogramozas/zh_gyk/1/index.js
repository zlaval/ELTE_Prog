const visibleDiv = document.querySelector("#visiblePercentage")
visibleDiv.innerHTML = `0 %`

const thresholds = new Array();

for (let i = 0; i < 101; ++i) {
    thresholds.push(i / 100)

}

const option = {
    root: null,
    rootMargin: "0px",
    threshold: thresholds
}

const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        const percentage = entry.intersectionRatio * 100
        visibleDiv.innerHTML = `${percentage} %`
    })

}, option)

observer.observe(visibleDiv)