import { Card, Color, Form, GameMode, Number, Player, Content, AdditionalCardMode } from "./models.js";
import { Store, GameType } from "./store.js"

const timeSpan = document.querySelector(".elapsed-time")
const playerPanel = document.querySelector("#player-panel")
const cardPanel = document.querySelector("#card-panel")
const addCardBtn = document.querySelector('#add-card-btn')
const findSetBtn = document.querySelector("#find-set-btn")
const showSetBtn = document.querySelector("#show-set-btn")
const alertDiv = document.querySelector(".ui-div")
const pointDiv = document.querySelector(".point-div")
const deckRemain = document.querySelector(".deck-remain")
const newGameBtn = document.querySelector("#new-game")
const menuBtn = document.querySelector("#menu-btn")
const endGameBtns = document.querySelector("#end-game-btns")
const menu = document.querySelector("#menu")
const playGround = document.querySelector("#play-ground")
const beginner = document.querySelector("#competition-beginner")
const advancedType = document.querySelector("#competition-advanced")
const multiplayer = document.querySelector("#competition-multiplayer")

const store = new Store()

let clock = null
let startTime = null
let options

let deck = []
let playersMap = new Map()
let cardOnScreenMap = new Map()

let activePlayer = null
let gameInterval = null
let timeBack


export function startGame(gameOptions, players) {
    playersMap = new Map()
    cleanUp()
    options = gameOptions
    startTimer()
    createPlayerMap(players)
    buildDeck()
    renderDeck()
    renderPointDiv()
    setUi()
    selectIfOnePlayer()
    uiMessageOnePlayer()
}

function selectIfOnePlayer() {
    if (playersMap.size === 1 && options.gameMode == GameMode.PRACTICE) {
        activePlayer = playersMap.values().next().value
        activePlayer.active = false
        renderPlayerPanel()
    }
}

function uiMessageOnePlayer() {
    if (playersMap.size === 1 && options.gameMode == GameMode.PRACTICE) {
        alertDiv.innerHTML = "Gyakorlás 1 játékossal, nem szükséges játékost kijelölni"
    }
}

newGameBtn.addEventListener("click", function () {
    startTimer()
    buttonsDisabled(false)
    updateScores()
    buildDeck()
    renderDeck()
    renderPointDiv()
    renderPlayerPanel()
    endGameBtns.style.display = "none"
    selectIfOnePlayer()
    uiMessageOnePlayer()
})

function updateScores() {
    for (let [i, p] of playersMap) {
        if (p.sumPoints == null) {
            p.sumPoints = 0
        }
        p.sumPoints += p.point
        p.point = 0
        p.active = true
    }
}

function cleanUp() {
    clock = null
    startTime = null
    deck = []
    cardOnScreenMap = new Map()
    activePlayer = null
    gameInterval = null
}

function setUi() {
    if (options.findSetBtn) {
        findSetBtn.style.display = ""
    } else {
        findSetBtn.style.display = "none"
    }
    if (options.showSetBtn) {
        showSetBtn.style.display = ""
    } else {
        showSetBtn.style.display = "none"
    }
    if (options.additionalCard === AdditionalCardMode.AUTO) {
        addCardBtn.style.display = "none"
    } else {
        addCardBtn.style.display = ""
    }
    endGameBtns.style.display = "none"
}

function setDeckRemain() {
    deckRemain.innerHTML = `Pakli: ${deck.length} lap`
}

function renderPointDiv() {
    let players = ""
    for (let [i, p] of playersMap) {
        let sumPointTag = ""
        if (p.sumPoints != null) {
            sumPointTag = ` ( ${p.sumPoints} )`
        }
        players += `<div class="player-point">${p.name}: <b>${p.point}</b> ${sumPointTag}</div>`
    }
    pointDiv.innerHTML = players
}

function startTimer() {
    timeSpan.parentElement.style.display = ""
    startTime = Date.now()
    clock = setInterval(startTimeMeasure, 1000)
}

function startTimeMeasure() {
    const now = Date.now()
    const elapsedSeconds = Math.floor((now - startTime) / 1000)
    timeSpan.innerHTML = `Játékidő: ${elapsedSeconds} s`
}

function buildDeck() {
    for (let num in Number) {
        for (let color in Color) {
            for (let form in Form) {
                if (options.advanced) {
                    for (let content in Content) {
                        deck.push(new Card(num, color, form, content))
                    }
                } else {
                    deck.push(new Card(num, color, form, "FULL"))
                }
            }
        }
    }
    shuffleDeck()
    console.log(deck)
}

function shuffleDeck() {
    deck.sort(() => Math.random() - 0.5)
}

function createImageName(card) {
    return `./img/${Number[card.number]}${Content[card.content]}${Color[card.color]}${Form[card.form]}.svg`

}

function createPlayerMap(players) {
    let index = 1
    for (let p of players) {
        const player = new Player(index, p)
        playersMap.set(index, player)
        index++
    }
    renderPlayerPanel()
}

function renderPlayerPanel() {
    playerPanel.innerHTML = ""

    for (let [i, p] of playersMap) {
        if (p.active) {
            playerPanel.innerHTML += `<div class="col-12"> <button key="${i}" type="button" class="btn btn-dark player-select-btn">${p.name}</button></div>`
        } else {
            if (playersMap.size === 1 && options.gameMode == GameMode.PRACTICE) {
                playerPanel.innerHTML += `<div class="col-12"> <button disabled key="${i}" type="button" class="btn btn-warning player-select-btn">${p.name}</button></div>`
            } else {
                playerPanel.innerHTML += `<div class="col-12"> <button disabled key="${i}" type="button" class="btn btn-light player-select-btn">${p.name}</button></div>`
            }
        }

    }
}

function reRenderDeck() {
    setDeckRemain()
    const newCardOnScreenMap = new Map()
    const mod = cardOnScreenMap.size < 13 ? 4 : 5
    let cardTags = '<div class="row">'
    let i = 0
    for (let [id, card] of cardOnScreenMap) {
        if (i % mod === 0 && i !== 0) {
            cardTags += '</div>'
            cardTags += '<div class="row">'
        }
        const cardId = `card-${i}`
        card.cardId = cardId
        const svg = createImageName(card)
        cardTags += `<div class="col py-3""><img id="${cardId}" class="card" src=${svg}></div>`
        newCardOnScreenMap.set(cardId, card)
        i++
    }
    cardTags += '</div>'
    cardPanel.innerHTML = cardTags
    cardOnScreenMap = newCardOnScreenMap
    setAddBtnActivity()
}

function renderDeck() {
    let cardTags = '<div class="row">'
    for (let i = 0; i < 12; i++) {
        if (i % 4 === 0 && i !== 0) {
            cardTags += '</div>'
            cardTags += '<div class="row">'
        }
        const card = deck.shift()
        const svg = createImageName(card)
        const cardId = `card-${i}`
        card.cardId = cardId
        cardTags += `<div class="col py-3" ><img id="${cardId}" class="card" src=${svg}></div>`
        cardOnScreenMap.set(cardId, card)
    }
    cardTags += '</div>'
    cardPanel.innerHTML = cardTags
    setDeckRemain()
    setAddBtnActivity()
}

function setAddBtnActivity() {
    if (cardOnScreenMap.size > 12) {
        addCardBtn.disabled = true
    } else {
        addCardBtn.disabled = false
    }
}


function handlePlayerButtonClick(event) {
    const playerId = event.target.getAttribute("key")
    activePlayer = playersMap.get(parseInt(playerId))
    alertDiv.innerHTML = `${activePlayer.name} jelölhet.`
    event.target.classList.remove("btn-dark")
    event.target.classList.add("btn-warning")
    event.target.classList.add("btn-animation")
    event.target.disabled = true

    if (playersMap.size === 1 && options.gameMode === GameMode.PRACTICE) {
        return
    }
    gameInterval = setTimeout(handleCardAfterFinishMarkingOrTimeout, 10000)
    timeBack = 10
    setTimeout(countDown, 1000)
    buttonsDisabled(true)
}

function countDown() {
    timeBack--
    if (timeBack > 0 && gameInterval != null) {
        alertDiv.innerHTML = `<h3>${timeBack}</h3>`
        setTimeout(countDown, 1000)
    }
}

function checkAndReserPlayers() {
    let allInactive = true
    for (let [i, p] of playersMap) {
        if (p.active) {
            allInactive = false
        }
    }
    if (allInactive) {
        for (let [i, p] of playersMap) {
            p.active = true
        }
        setTimeout(function () {
            alertDiv.innerHTML = "Új kör indult"
            renderPlayerPanel()
        }, 1000)

    }

}

delegate(playerPanel, "click", ".player-select-btn", ".player-select-btn", handlePlayerButtonClick)

addCardBtn.addEventListener('click', function () {
    addExtraCards()
})

menuBtn.addEventListener("click", function () {
    menu.style.display = ""
    playGround.style.display = "none"
})

function addExtraCards() {
    if (deck.length === 0 || cardOnScreenMap.size > 12) {
        return
    }
    for (let i = 12; i < 15; i++) {
        const card = deck.shift()
        const cardId = `card-${i}`
        cardOnScreenMap.set(cardId, card)
    }
    reRenderDeck()
}

findSetBtn.addEventListener("click", function () {
    const hasSet = hasSetOnBoard()
    if (hasSet) {
        alertDiv.innerHTML = "Található set az asztalon"
    } else {
        alertDiv.innerHTML = "Nem található set az asztalon"
    }
})

showSetBtn.addEventListener("click", function () {
    const set = findSetOnBoard()
    for (let card of set) {
        const cardTag = document.querySelector(`#${card.cardId}`)
        cardTag.classList.toggle('show-set')
    }
    setTimeout(function () {
        for (let card of set) {
            const cardTag = document.querySelector(`#${card.cardId}`)
            cardTag.classList.toggle('show-set')
        }
    }, 2000)
})

function getSelectedCards() {
    return document.querySelectorAll(".selected-card")
}

function removeSelection() {
    const cards = getSelectedCards()
    for (let card of cards) {
        card.classList.toggle('selected-card')
        card.classList.toggle("card-rotate")
    }
}

function cardClickHandler(event) {
    if (getSelectedCards().length >= 3 || activePlayer === null) {
        return
    }
    event.target.classList.toggle('selected-card')
    event.target.classList.toggle("card-rotate")
    if (getSelectedCards().length === 3) {
        setTimeout(handleCardAfterFinishMarkingOrTimeout, 1200)
    }

}

function hasSetOnBoard() {
    return findSetOnBoard().length > 0
}

function badTip() {
    activePlayer.point -= 1
    alertDiv.innerHTML = "Hibás tipp! -1 pont"
    removeSelection()
}

function handleCardAfterFinishMarkingOrTimeout() {
    if (gameInterval != null) {
        clearTimeout(gameInterval)
    }
    gameInterval = null
    activePlayer.active = false
    const cards = getSelectedCards();
    if (cards.length < 3) {
        badTip()
    } else {
        const set = isSet(cardOnScreenMap.get(cards[0].id), cardOnScreenMap.get(cards[1].id), cardOnScreenMap.get(cards[2].id))
        if (!set) {
            badTip()
        } else {
            alertDiv.innerHTML = "Jó tipp! +1 pont"
            activePlayer.point += 1
            for (let [i, p] of playersMap) {
                p.active = true
            }
            changeCardsOnBoard(cards)
            autoAddCard()
        }
    }

    activePlayer = null
    renderPlayerPanel()
    renderPointDiv()
    checkAndReserPlayers()
    buttonsDisabled(cardOnScreenMap.size === 0)
    selectIfOnePlayer()
}

function autoAddCard() {
    if (options.additionalCard == AdditionalCardMode.AUTO) {
        if (!hasSetOnBoard() && cardOnScreenMap.size <= 12 && deck.length > 0) {
            addExtraCards()
        }
    }
    if (!hasSetOnBoard()) {
        if (cardOnScreenMap.size > 12 || deck.length === 0) {
            gameOver()
        }
    }
}

function gameOver() {
    alertDiv.innerHTML="Játék vége"
    const now = Date.now()
    const elapsedSeconds = Math.floor((now - startTime) / 1000)
    clearInterval(clock)
    cleanUp()

    let endTag = '<div class="text-center">'
    const players = [...playersMap.values()]
    players.sort((a, b) => b.point - a.point)

    saveResult(players)

    let index = 1
    for (let player of players) {
        let sum = ""
        if (player.sumPoints != null) {
            const points = player.sumPoints + player.point
            sum = `<span class="sum-point">össz: ${points}<span>`
        }
        endTag += `<div class="row"><div class="place col-2">${index}.</div><div class="col-10 py-2"><span class="result-list"> ${player.name}: <span class="result-point">${player.point}</span> ${sum}</span></div></div>`
        index++
    }

    endTag += "</div>"
    cardPanel.innerHTML = endTag
    endGameBtns.style.display = ""
    buttonsDisabled(true)
    renderScores()
}

function saveResult(players) {
    try {
        if (options.gameMode == GameMode.COMPETITION) {
            let mode
            if (options.playerCount === 1) {
                if (options.advanced) {
                    mode = GameType.ONE_PLAYER_ADVANCED
                } else {
                    mode = GameType.ONE_PLAYER_BEGINNER
                }
            } else {
                mode = GameType.MULTIPLAYER
            }

            store.writeLocalStore(players, mode)
        }
    } catch (err) {
        console.log(err)
        console.log("Cannot save result")
    }
}



function buttonsDisabled(disabled) {
    if (cardOnScreenMap.size <= 12) {
        addCardBtn.disabled = disabled
    }
    showSetBtn.disabled = disabled
    findSetBtn.disabled = disabled

    if (disabled) {
        const playerButtons = document.querySelectorAll(".player-select-btn")
        for (let playerButton of playerButtons) {
            playerButton.disabled = true
        }
    } else {
        renderPlayerPanel()
    }
}

function changeCardsOnBoard(cards) {
    for (let i = 0; i < 3; ++i) {
        if (deck.length > 0 && cardOnScreenMap.size <= 12) {
            const newCard = deck.shift()
            cardOnScreenMap.set(cards[i].id, newCard)
        } else {
            cardOnScreenMap.delete(cards[i].id)
        }
    }
    reRenderDeck()
}


function findSetOnBoard() {
    const cards = [...cardOnScreenMap.values()]
    for (let i = 0; i < cards.length - 2; i++) {
        const card1 = cards[i]
        for (let j = i + 1; j < cards.length - 1; j++) {
            const card2 = cards[j]
            for (let k = j + 1; k < cards.length; k++) {
                const card3 = cards[k]
                const set = isSet(card1, card2, card3)
                if (set) {
                    return [card1, card2, card3]
                }
            }
        }

    }
    return []
}

function isSet(card1, card2, card3) {
    let set = false
    if (card1.number === card2.number && card2.number === card3.number) {
        set = true
    }
    if (!set) {
        if (card1.number != card2.number && card1.number != card3.number && card3.number != card2.number) {
            set = true
        }
    }
    if (!set) {
        return false
    }

    set = false
    if (card1.color === card2.color && card2.color === card3.color) {
        set = true
    }
    if (!set) {
        if (card1.color != card2.color && card1.color != card3.color && card3.color != card2.color) {
            set = true
        }
    }
    if (!set) {
        return false
    }

    set = false
    if (card1.form === card2.form && card2.form === card3.form) {
        set = true
    }
    if (!set) {
        if (card1.form != card2.form && card1.form != card3.form && card3.form != card2.form) {
            set = true
        }
    }
    if (!set) {
        return false
    }

    set = false
    if (card1.content === card2.content && card2.content === card3.content) {
        set = true
    }
    if (!set) {
        if (card1.content != card2.content && card1.content != card3.content && card3.content != card2.content) {
            set = true
        }
    }
    if (!set) {
        return false
    }

    return true
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

delegate(cardPanel, "click", ".card", ".card", cardClickHandler)

function delegate(parent, type, selector, eventSource, handler) {
    parent.addEventListener(type, function (event) {
        const eventElement = event.target.closest(eventSource);
        const targetElement = event.target.closest(selector);
        if (!eventSource || eventElement === event.target) {
            if (this.contains(targetElement)) {
                handler.call(targetElement, event);
            }
        }
    })
}
