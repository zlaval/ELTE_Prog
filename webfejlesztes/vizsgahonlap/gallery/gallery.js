$(document).ready(function () {
    $('#gallery').flexPhotoGallery({
        imageArray: imageArray,
        isViewImageOnClick: true
    });
});

var imageArray = [
    {
        "url": "../assets/gallery_img/gallery1.jpg",
        "width": 1024,
        "height": 768,
        "alt": "Játék kép - Ősi templom"
    },
    {
        "url": "../assets/gallery_img/gallery2.jpg",
        "width": 1024,
        "height": 768,
        "alt": "Játék kép - Útvesztő bejárat"
    },
    {
        "url": "../assets/gallery_img/gallery3.jpg",
        "width": 1024,
        "height": 768,
        "alt": "Játék kép - Városi csata"
    },
    {
        "url": "../assets/gallery_img/gallery4.jpg",
        "width": 1024,
        "height": 768,
        "alt": "Játék kép - Romok a vadonban"
    },
    {
        "url": "../assets/gallery_img/gallery5.jpg",
        "width": 1024,
        "height": 768,
        "alt": "Játék kép - Csata"
    }

]