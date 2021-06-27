import {
    ACTUAL_PLAYER_DRAW_CARD,
    NEXT_PLAYER,
    PLAYER_ADD_CARDS,
    PLAYER_BUILT_ROUTE,
    PLAYER_STATE_CHANGE,
    SET_GOAL_DONE,
    START_GAME,
    SYNC_PLAYERS_STATE
} from "./actions";

export function setGoalToDone(goalId) {
    return {
        type: SET_GOAL_DONE,
        payload: goalId
    }
}

export function nextPlayer() {
    return {
        type: NEXT_PLAYER,
        payload: null
    }
}

export function actualPlayerDrawWageCard(card) {
    return {
        type: ACTUAL_PLAYER_DRAW_CARD,
        payload: card
    }
}

export function playerStateChange(state) {
    return {
        type: PLAYER_STATE_CHANGE,
        payload: state
    }
}

export function initializePlayersState(players) {
    return {
        type: START_GAME,
        payload: players
    }
}

export function playerAddCards(player, card) {
    return {
        type: PLAYER_ADD_CARDS,
        payload: {
            player: player,
            card: card
        }
    }
}

export function playerBuiltRoute(point, cards, routeId) {
    return {
        type: PLAYER_BUILT_ROUTE,
        payload: {
            point,
            cards,
            routeId
        }
    }
}

export function syncPlayersState(data) {
    return {
        type: SYNC_PLAYERS_STATE,
        payload: data
    }
}