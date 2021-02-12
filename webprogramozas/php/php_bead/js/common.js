const btnLogin = document.querySelector("#btn-login")
const btnRegistration = document.querySelector("#btn-registration")
const btnLogout = document.querySelector("#btn-logout")

if (btnLogin) {
    btnLogin.addEventListener("click", event => {
        window.location.href = "../page/login.php"
    })
}
if (btnRegistration) {
    btnRegistration.addEventListener("click", event => {
        window.location.href = "../page/registration.php"
    })
}

if (btnLogout) {
    btnLogout.addEventListener("click", event => {
        window.location.href = "../page/logout.php"
    })
}