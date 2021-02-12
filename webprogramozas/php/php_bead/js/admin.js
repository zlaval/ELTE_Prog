const dateInput = document.querySelector("#date")

function setDate() {
    if (!dateInput.value) {
        let today = new Date().toISOString().split('T')[0]
        dateInput.value = today
    }
}

setDate()