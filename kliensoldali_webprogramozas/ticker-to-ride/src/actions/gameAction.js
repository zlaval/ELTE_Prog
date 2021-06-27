import {INIT_GAME, PLAYER_JOIN, SYNC_LOBBY_STATE} from "./actions";


export function initGame(data) {
    return {
        type: INIT_GAME,
        payload: data
    }
}

export function playerJoined(playerId, playerName) {
    return {
        type: PLAYER_JOIN,
        payload: {
            playerId: playerId,
            playerName: playerName
        }
    }
}

export function syncLobbyState(state) {
    return {
        type: SYNC_LOBBY_STATE,
        payload: state
    }
}

/*
export function addHistory(record) {
    return {
        type: ADD_HISTORY_RECORD,
        payload: record
    }
}
 */