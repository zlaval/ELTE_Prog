
class KeyfilteredInput extends HTMLInputElement {
    connectedCallback() {
        const validChars = this.getAttribute("validCharacters")
        this.addEventListener('keydown', (e) => {
            if (!validChars.includes(e.key)) {
                e.preventDefault()
            }
        });
    }
}

customElements.define("keyfiltered-input", KeyfilteredInput, { extends: 'input' })