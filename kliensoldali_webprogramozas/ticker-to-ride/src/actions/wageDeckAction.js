import {DRAW_CARD, DRAW_CARDS, INIT_WAGE_DECK, PLAYER_DRAW_FROM_BOARD, PUT_TO_FOLDED, SYNC_WAGE_STATE} from "./actions";

export function drawWageCardFromDeck(card) {
    return {
        type: DRAW_CARD,
        payload: card
    }
}

export function drawWageCardsFromDeck(cards) {
    return {
        type: DRAW_CARDS,
        payload: cards
    }
}

export function playerDrawFromBoard(card) {
    return {
        type: PLAYER_DRAW_FROM_BOARD,
        payload: card
    }
}

export function initializeWageDeck() {
    return {
        type: INIT_WAGE_DECK,
        payload: null
    }
}

export function putToFolds(cards) {
    return {
        type: PUT_TO_FOLDED,
        payload: cards
    }
}

export function syncWageState(data) {
    return {
        type: SYNC_WAGE_STATE,
        payload: data
    }
}