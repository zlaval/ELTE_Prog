import {
    DRAW_CARD,
    DRAW_CARDS,
    INIT_WAGE_DECK,
    PLAYER_DRAW_FROM_BOARD,
    PUT_TO_FOLDED,
    SYNC_WAGE_STATE
} from "../actions/actions";
import {BLACK, BLUE, GRAY_LOCOMOTIVE, GREEN, ORANGE, PURPLE, RED, WHITE, YELLOW} from "../data/constants";

function createDeck() {
    const purpleCards = generate(PURPLE)
    const whiteCards = generate(WHITE)
    const blueCards = generate(BLUE)
    const yellowCards = generate(YELLOW)
    const orangeCards = generate(ORANGE)
    const blackCards = generate(BLACK)
    const redCards = generate(RED)
    const greenCards = generate(GREEN)
    const grayCards = generate(GRAY_LOCOMOTIVE, 15)

    const cards = purpleCards
        .concat(whiteCards)
        .concat(blueCards)
        .concat(yellowCards)
        .concat(orangeCards)
        .concat(blackCards)
        .concat(redCards)
        .concat(grayCards)
        .concat(greenCards)

    const shuffled = cards.sort(() => Math.random() - 0.5)

    return shuffled
}

function generate(color, quantity = 12) {
    const cards = []
    for (let i = 0; i < quantity; ++i) {
        cards.push(color)
    }
    return cards
}

function initializeState() {
    const deck = createDeck()
    const boardCards = deck.splice(0, 5)
    return {
        deck: deck,
        board: boardCards,
        folds: []
    }
}

function wageDeckReducer(state = initializeState(), action) {
    const {type, payload} = action
    let newDeck
    switch (type) {
        case SYNC_WAGE_STATE:
            return payload
        case DRAW_CARD:
            const index = state.deck.lastIndexOf(x => x === payload)
            newDeck = [...state.deck]
            newDeck.splice(index, 1)
            if (newDeck.length === 0) {
                const shuffledFold = [...state.folds]
                return {
                    ...state,
                    deck: shuffledFold,
                    folds: []
                }
            }
            return {
                ...state,
                deck: newDeck
            }
        case PUT_TO_FOLDED:
            const newFolds = [...state.folds, ...payload]
            return {
                ...state,
                folds: newFolds
            }
        case INIT_WAGE_DECK:
            return initializeState()
        case DRAW_CARDS:
            newDeck = [...state.deck]
            payload.forEach(c => {
                const index = newDeck.indexOf(c)
                newDeck.splice(index, 1)
            })
            return {
                ...state,
                deck: newDeck
            }
        case PLAYER_DRAW_FROM_BOARD:
            newDeck = [...state.deck]
            const boardCards = [...state.board]
            const boardCardIndex = boardCards.indexOf(payload)
            boardCards.splice(boardCardIndex, 1)
            if (newDeck.length > 0) {
                const boardCard = newDeck.splice(-1, 1)[0]
                boardCards.push(boardCard)
            } else if (state.folds.length > 0) {
                const shuffledFold = [...state.folds]
                while (boardCards.length < 5) {
                    const boardCard = shuffledFold.splice(-1, 1)[0]
                    boardCards.push(boardCard)
                }
                return {
                    deck: shuffledFold,
                    folds: [],
                    board: boardCards
                }
            }
            return {
                ...state,
                deck: newDeck,
                board: boardCards
            }
        default:
            return state
    }

}

export default wageDeckReducer