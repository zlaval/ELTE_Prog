const navLinks = document.querySelectorAll('[data-scroll-style]');


for (let link of navLinks) {
    const type = link.getAttribute("data-scroll-style")
    if (type !== 'jump') {
        link.addEventListener('click', (e) => {
            e.preventDefault();

            const href = link.href;
            const id = href.split('#')[1];


            const targetElement = document.querySelector(`#${id}`);
            targetElement.scrollIntoView({ behavior: type });
        });
    }
}