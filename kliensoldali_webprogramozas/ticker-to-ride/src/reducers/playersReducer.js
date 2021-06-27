import {
    ACTUAL_PLAYER_DRAW_CARD,
    NEXT_PLAYER,
    PLAYER_ADD_CARDS,
    PLAYER_BUILT_ROUTE,
    PLAYER_STATE_CHANGE,
    SET_GOAL_DONE,
    START_GAME,
    SYNC_PLAYERS_STATE
} from "../actions/actions";
import {USER_BEGIN} from "../data/constants";
import {ticketToRideData} from "../data/ticket-to-ride-data";

const colors = ["blue", "red", "yellow", "brown", "green"]

const initialPlayer = (id, name, colorIndex, selected) => {
    return {
        id: id,
        name: name,
        round: 0,
        points: 0,
        goals: [],
        wages: 45,
        cards: [],
        color: colors[colorIndex],
        selected: selected,
        routes: []
    }
}

const playerInitialState = (players) => {
    const goals = Object.values(ticketToRideData.destinations).filter(x => !x.longDestination)
    const longGoals = Object.values(ticketToRideData.destinations).filter(x => x.longDestination)
    let i = 0
    const generated = players.map(pl => {
        const p = initialPlayer(pl.playerId, pl.playerName, i, i === 0)
        i++
        const playerGoals = []
        for (let i = 0; i < 5; i++) {
            const index = Math.floor(Math.random() * goals.length);
            const goal = goals.splice(index, 1)[0]
            goal.done = false
            playerGoals.push(goal)
        }
        const index = Math.floor(Math.random() * longGoals.length);
        const longGoal = longGoals.splice(index, 1)[0]
        longGoal.done = false
        playerGoals.push(longGoal)
        p.goals = playerGoals
        return p
    })
    return {
        players: generated,
        playerState: USER_BEGIN,
        actualPlayer: generated[0],
        actualPlayerIndex: 0
    }
}

const pointsList = [1, 2, 4, 7, 9, 15, 21]


function playersReducer(state = playerInitialState([]), action) {
    const {type, payload} = action
    let newState = state
    switch (type) {
        case SYNC_PLAYERS_STATE: {
            return payload
        }
        case START_GAME:
            return playerInitialState(payload)
        case SET_GOAL_DONE:
            const goals = [...state.actualPlayer.goals].map(g => {
                if (g.id === payload) {
                    return {
                        ...g,
                        done: true
                    }
                } else {
                    return g
                }
            })
            const actualPlayerMod = {
                ...state.actualPlayer,
                goals: goals
            }
            const playerList = state.players.map(p => {
                if (p.id === actualPlayerMod.id) {
                    return actualPlayerMod
                }
                return p
            })
            return {
                ...state,
                actualPlayer: actualPlayerMod,
                players: playerList
            }
        case PLAYER_BUILT_ROUTE:
            const cards = [...state.actualPlayer.cards]
            for (let card of payload.cards) {
                const index = cards.indexOf(card)
                cards.splice(index, 1)
            }
            const newActualPlayer = {
                ...state.actualPlayer,
                wages: state.actualPlayer.wages - payload.point,
                cards: cards,
                points: state.actualPlayer.points + pointsList[payload.point],
                routes: [...state.actualPlayer.routes, payload.routeId]
            }
            const newPlayerList = state.players.map(p => {
                if (p.id === newActualPlayer.id) {
                    return newActualPlayer
                }
                return p
            })
            return {
                ...state,
                actualPlayer: newActualPlayer,
                players: newPlayerList
            }
        case ACTUAL_PLAYER_DRAW_CARD:
            const players = state.players.map(p => {
                if (p.id === state.actualPlayer.id) {
                    return {
                        ...p,
                        cards: [...p.cards, payload]
                    }
                }
                return p
            })
            return {
                ...state,
                players: players
            }
        case PLAYER_STATE_CHANGE:
            newState = {
                players: state.players,
                playerState: payload,
                actualPlayer: state.actualPlayer,
                actualPlayerIndex: state.actualPlayerIndex
            }
            return newState
        case NEXT_PLAYER:
            newState = {
                players: state.players,
                playerState: USER_BEGIN,
                actualPlayer: state.actualPlayer,
                actualPlayerIndex: state.actualPlayerIndex
            }
            newState.actualPlayer.round += 1
            newState.actualPlayerIndex += 1
            if (newState.actualPlayerIndex >= newState.players.length) {
                newState.actualPlayerIndex = 0
            }
            newState.actualPlayer = newState.players[newState.actualPlayerIndex]
            return newState
        case PLAYER_ADD_CARDS:
            const newPlayers = state.players.map(p => {
                if (p.id === payload.player.id) {
                    p.cards = payload.card
                }
                return p
            })
            return {
                ...state,
                players: newPlayers
            }
        default:
            return state
    }
}

export default playersReducer