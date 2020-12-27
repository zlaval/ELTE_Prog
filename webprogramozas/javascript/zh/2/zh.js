const number = document.querySelector("#numbers")

number.addEventListener("keypress",function (event) {
    event.preventDefault()
    const value = number.value

    if (value==="") {
        if (event.keyCode < 49 || event.keyCode > 57) {
            return ;
        }
    } else {
        if (event.keyCode < 48 || event.keyCode > 57) {
            return ;
        }
    }
    number.value=value+event.key
})
