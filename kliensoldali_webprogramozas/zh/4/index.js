
class KeyfilteredInput extends HTMLInputElement {
    connectedCallback() {
        const pattern = this.getAttribute("validCharacters")

        this.addEventListener('keydown', (e) => {
            if (!e.key.match(pattern)) {
                e.preventDefault()
            }
        });
    }
}

customElements.define("keyfiltered-input", KeyfilteredInput, { extends: 'input' })