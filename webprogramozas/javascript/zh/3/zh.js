document.body.addEventListener('click', eventHandler)

function eventHandler(event) {
    const x = event.pageX
    const y = event.pageY

	if(event.target.classList.contains('star')){
		event.target.classList.add('falling')
	}else{
		document.body.innerHTML += `<span class="star" style="top: ${y}px; left: ${x}px;"></span>`
	}
    
}
