import React, {useEffect} from 'react'
import ConnectedPlayer from '../componenets/ConnectedPlayer'
import {useHistory} from 'react-router-dom'
import './Lobby.css'
import {useDispatch, useSelector} from "react-redux";
import {LobbyChannel} from "../socket";
import {syncLobbyState} from "../actions/gameAction";
import {initializeWageDeck, syncWageState} from "../actions/wageDeckAction";
import {initializePlayersState, syncPlayersState} from "../actions/playerActions";
import {initRoutes, syncRouteState} from "../actions/routeAction";
import {initHistory} from "../actions/historyAction";
import {SYNC_BOARD_STATE, SYNC_LOBBY_STATE} from "../actions/actions";

const msg = new LobbyChannel()

const Lobby = (props) => {

    const gameReducer = useSelector(state => state.gameReducer)

    const dispatch = useDispatch()
    const history = useHistory();
    const name = gameReducer.playerName
    const room = gameReducer.roomId
    const playerCount = gameReducer.maxPlayerCount

    const actualPlayerCount = gameReducer.actualPlayerCount
    const players = gameReducer.players

    useEffect(() => {
        msg.onSyncName((data) => {
            if (gameReducer.master) {
                const gameState = {
                    type: SYNC_LOBBY_STATE,
                    maxPlayerCount: gameReducer.maxPlayerCount,
                    actualPlayerCount: actualPlayerCount + 1,
                    players: [...players, {
                        playerId: data.id,
                        playerName: data.name
                    }]
                }
                msg.syncGameState(gameReducer.roomId, gameState)
            }
        })
    })

    useEffect(() => {
        msg.onSyncGameState((state) => {
            //if (!gameReducer.master) {
            if (state.type === SYNC_LOBBY_STATE) {
                dispatch(syncLobbyState(state))
            }
            // }
        })
    }, [])

    useEffect(() => {
        if (!gameReducer.master) {
            msg.onSyncBoardState((data) => {
                if (data.type === SYNC_BOARD_STATE) {
                    dispatch(syncPlayersState(data.playersReducer))
                    dispatch(syncRouteState(data.routeReducer))
                    dispatch(syncWageState(data.wageReducer))
                    let path = `/board`;
                    history.push(path);
                }
            })
        }
    }, [])

    if (gameReducer.master) {
        if (gameReducer.actualPlayerCount === gameReducer.maxPlayerCount) {
            dispatch(initializeWageDeck())
            dispatch(initializePlayersState(players))
            dispatch(initRoutes())
            dispatch(initHistory())


            let path = `/board`;
            history.push(path);
        }
    }

    const playersHtml = players.map(p =>
        <ConnectedPlayer key={p.playerId} color={p.playerName === name ? "darkred" : "blue"} name={p.playerName}/>
    )

    return (
        <div>
            <div className={"text-center lobby-header"}>
                <h1 className={"my-5"}>Váróterem</h1>
                <div className={"text-center"}>
                    <h3 className={"my-2 lobby-header-room"}>Szoba kód: {room}</h3>
                </div>

                <h5 className={"my-5 lobby-header-player_count"}>{playerCount} játékos</h5>
            </div>
            <div className={"lobby-players my-5"}>
                {playersHtml}
            </div>
        </div>
    )
}

export default Lobby