import { GameMode, Options, AdditionalCardMode } from "./models.js";
import { startGame } from "./game.js"
import { Store, GameType } from "./store.js"

const options = new Options()
let playerList = []
const store = new Store()

const playerCount = document.querySelector("#player-count")
const playerCountDisplay = document.querySelector("#player-count-display")
const players = document.querySelector("#players")
const playMode = document.querySelector("#play-mode")
const accordionBtn = document.querySelector(".accordion")
const accordionPanel = document.querySelector(".accordion-panel")
const otherOptions = document.querySelector("#other-options")
const startGameBtn = document.querySelector("#start-game")
const menu = document.querySelector("#menu")
const playGround = document.querySelector("#play-ground")
const advanced = document.querySelector("#advanced")

const findSetChx = document.querySelector("#find-set-chx")
const showSetChx = document.querySelector("#show-set-chx")
const addCardChx = document.querySelector("#additional-card-chx")

const beginner = document.querySelector("#competition-beginner")
const advancedType = document.querySelector("#competition-advanced")
const multiplayer = document.querySelector("#competition-multiplayer")

playerCount.value = options.playerCount
playerCountDisplay.innerHTML = `( ${playerCount.value} )`
addPlayers(playerCount.value)
menu.style.display = ""
playGround.style.display = "none"

renderScores()

playerCount.addEventListener("input", function (event) {
    const playerCount = this.value
    options.playerCount = playerCount
    playerCountDisplay.innerHTML = `( ${playerCount} )`
    addPlayers(playerCount)
})

playMode.addEventListener("change", function () {
    if (this.checked) {
        options.gameMode = GameMode.COMPETITION
    } else {
        options.gameMode = GameMode.PRACTICE
    }
    otherSettings(this.checked)
})

accordionBtn.addEventListener("click", function () {
    this.classList.toggle("active")
    if (accordionPanel.style.display === "block") {
        accordionPanel.style.display = "none";
    } else {
        accordionPanel.style.display = "block";
    }
})

startGameBtn.addEventListener("click", function () {
    const playerInputs = document.querySelectorAll(".player")
    playerList = []
    for (let p of playerInputs) {
        playerList.push(p.value)
    }
    menu.style.display = "none"
    playGround.style.display = ""
    options.advanced = advanced.checked
    if (options.gameMode === GameMode.COMPETITION) {
        options.findSetBtn = false
        options.showSetBtn = false 
        options.additionalCard = AdditionalCardMode.AUTO
    } else {
        options.findSetBtn = findSetChx.checked
        options.showSetBtn = showSetChx.checked
        if (addCardChx.checked) {
            options.additionalCard = AdditionalCardMode.BTN
        } else {
            options.additionalCard = AdditionalCardMode.AUTO
        }
    }

    startGame(options, playerList)
})

function addPlayers(count) {
    players.innerHTML = ''
    for (let i = 0; i < count; ++i) {
        players.innerHTML += `<div class="form-group"><input type="text" class="form-control player" value="Játékos${i + 1}"></div>`
    }
}

function otherSettings(hidden) {
    if (hidden) {
        otherOptions.style.display = "none";
    } else {
        otherOptions.style.display = "";
    }
}

function renderScores() {
    renderScore(GameType.ONE_PLAYER_BEGINNER, beginner)
    renderScore(GameType.ONE_PLAYER_ADVANCED, advancedType)
    renderScore(GameType.MULTIPLAYER, multiplayer)
}

function renderScore(gameType, div) {
    const scores = store.readLocalStore(gameType)
    let endTag = '<div class="text-center">'
    let index = 1
    for (let score of scores) {
        endTag += `<div class="row"><span class="place">${index}.</span><div class="col-11 py-2"><span class="result-list"> ${score.name}: <span class="result-point">${score.point}</span></span></div></div>`
        index++
    }
    endTag += "</div>"
    div.innerHTML = endTag
}