import {INIT_GAME, PLAYER_JOIN, SYNC_LOBBY_STATE} from "../actions/actions";

const initialState = {
    master: false,
    roomId: null,
    playerName: null,
    playerId: null,
    maxPlayerCount: 2,
    actualPlayerCount: 1,
    players: []
}


function playersReducer(state = initialState, action) {
    const {type, payload} = action
    switch (type) {
        case INIT_GAME:
            return {
                ...state,
                master: payload.master,
                playerId: payload.playerId,
                playerName: payload.playerName,
                maxPlayerCount: payload.maxPlayerCount,
                roomId: payload.roomId,
                players: [{
                    playerId: payload.playerId,
                    playerName: payload.playerName,
                }]
            }
        case PLAYER_JOIN:
            return {
                ...state,
                maxPlayerCount: state.maxPlayerCount + 1,
                players: [...state.players, payload]
            }
        case  SYNC_LOBBY_STATE:
            return {
                ...state,
                maxPlayerCount: payload.maxPlayerCount,
                actualPlayerCount: payload.actualPlayerCount,
                players: payload.players
            }
        default:
            return state
    }

}

export default playersReducer