const links = document.querySelector('#links')

function handleClickOnList(event) {
	event.preventDefault()
	console.log(this.href)
}

delegate(links, 'click', 'li a', handleClickOnList)

function delegate(parent, type, selector, handler) {
	parent.addEventListener(type, function (event) {
		const targetElement = event.target.closest(selector);
		if (this.contains(targetElement)) {
			handler.call(targetElement, event);
		}
	})
}