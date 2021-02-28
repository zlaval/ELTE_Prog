
const imagesDiv = document.querySelector("#images")

$(document).ready(function () {
    enhanceImages()
    $(document).ready(function () {
        $('#imageGalery').lightSlider({
            gallery: true,
            item: 1,
            loop: false,
            thumbItem: 9,
            slideMargin: 300,
            enableDrag: false,
            currentPagerPosition: 'middle',
            onSliderLoad: function (el) {
                el.lightGallery({
                    selector: '#imageGallery .lslide'
                });
            }
        });
    });
});

function enhanceImages() {
    const images = document.querySelectorAll("img")
    const ul = document.createElement("ul");
    ul.setAttribute("id", "imageGalery")

    images.forEach(img => {
        const li = document.createElement("li")
        li.setAttribute("data-thumb", img.getAttribute("src"))
        li.setAttribute("data-src", img.getAttribute("src"))
        li.appendChild(img)
        ul.appendChild(li)
    })
    imagesDiv.appendChild(ul)

}
