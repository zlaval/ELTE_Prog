const element = $('input[name="countryCode"]')

const pattern = element.attr("data-pattern")

const form = $("form")

const text = document.createElement('span');

form.append(text)
text.innerHTML = "Empty field"
element.on('input', (e) => {
    const value = element.val()
    if (!value) {
        text.innerHTML = "Empty field"
    } else if (!value.match(pattern)) {
        text.innerHTML = "Wrong value"
    } else {
        text.innerHTML = "Good value"
    }
})

form.submit((e) => {
    const value = element.val()
    if (!value || !value.match(pattern)) {
        e.preventDefault();
    }
})