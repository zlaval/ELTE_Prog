const input = document.querySelector("input")

input.addEventListener("keydown", function (event) {
    event.preventDefault()
    if (event.key === 'Backspace' || event.key === 'Delete') {
        return
    }
    const value=event.key

    if(value.match("[0-9\.]")){
        input.value+=value
    }
    if(input.value.match("^[0-9]{1,3}[\.]{1}[0-9]{1,3}[\.]{1}[0-9]{1,3}[\.]{1}[0-9]{1,3}$")){
        input.classList.add("helyes")
        input.classList.remove("helytelen")
    }else{
        input.classList.add("helytelen")
        input.classList.remove("helyes")
    }

})