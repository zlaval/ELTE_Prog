import React, {useEffect, useState} from 'react'
import PlayerBox from "../componenets/PlayerBox";
import Map from "../componenets/Map"
import './Board.css'
import Deck from "../componenets/Deck";
import Card from "../componenets/Card";
import History from "../componenets/History";
import Goal from "../componenets/Goal";
import {useDispatch, useSelector} from "react-redux";
import Modal from 'react-modal';
import {
    BLACK,
    BLUE,
    GRAY_LOCOMOTIVE,
    GREEN,
    ORANGE,
    PURPLE,
    RED,
    SIBLING_CITY_SELECTED,
    WHITE,
    YELLOW
} from "../data/constants";
import {getRoute} from "../data/path_finder";
import {calculateCardCombinations} from "../data/card_combination_calculator";
import RouteBuildCards from "../componenets/RouteBuildCards";
import {
    actualPlayerDrawWageCard,
    nextPlayer,
    playerAddCards,
    playerBuiltRoute,
    playerStateChange,
    setGoalToDone
} from "../actions/playerActions";
import {drawWageCardFromDeck, drawWageCardsFromDeck, playerDrawFromBoard, putToFolds} from "../actions/wageDeckAction";
import {LobbyChannel} from "../socket";
import {
    ACTUAL_PLAYER_DRAW_CARD,
    ADD_HISTORY_RECORD,
    BUILD_ROUTE,
    DRAW_CARD,
    NEXT_PLAYER,
    PLAYER_BUILT_ROUTE,
    PLAYER_DRAW_FROM_BOARD,
    PLAYER_STATE_CHANGE,
    PUT_TO_FOLDED,
    SET_GOAL_DONE,
    SYNC_BOARD_STATE
} from "../actions/actions";
import {addHistory} from "../actions/historyAction";
import {buildRoute} from "../actions/routeAction";
import {useHistory} from "react-router-dom";

const msg = new LobbyChannel()

const Board = (props) => {

    const dispatch = useDispatch()
    const [hoverGoal, setHoverGoal] = useState({departure: "", destination: "", built: false})
    const playersReducer = useSelector(state => state.playersReducer)
    const wageReducer = useSelector(state => state.wageDeckReducer)
    const routeReducer = useSelector(state => state.routeReducer)
    const gameReducer = useSelector(state => state.gameReducer)


    const history = useHistory();
    const routeChange = () => {
        let path = `/summary`;
        history.push(path);
    }

    const drawWageCards = (playerCards) => {
        dispatch(drawWageCardsFromDeck(playerCards))
    }

    const drawWagesToPlayers = () => {
        const cards = [...wageReducer.deck]
        playersReducer.players.forEach(p => {
            const playerCards = cards.splice(0, 4)
            drawWageCards(playerCards)
            dispatch(playerAddCards(p, playerCards))
        })
    }


    useEffect(() => {
        drawWagesToPlayers()

        const syncObj = {
            type: SYNC_BOARD_STATE,
            playersReducer: playersReducer,
            wageReducer: wageReducer,
            routeReducer: routeReducer
        }

        msg.syncBoardState(gameReducer.roomId, syncObj)

        //send message to start the game
    }, [])


    useEffect(() => {
        msg.onGamePlayAction((data) => {
            if (data.type === ADD_HISTORY_RECORD) {
                dispatch(addHistory(data.data))
            } else if (data.type === NEXT_PLAYER) {
                dispatch(nextPlayer())
            } else if (data.type === PLAYER_STATE_CHANGE) {
                dispatch(playerStateChange(data.data))
            } else if (data.type === DRAW_CARD) {
                dispatch(drawWageCardFromDeck(data.data))
            } else if (data.type === ACTUAL_PLAYER_DRAW_CARD) {
                dispatch(actualPlayerDrawWageCard(data.data))
            } else if (data.type === PLAYER_DRAW_FROM_BOARD) {
                dispatch(playerDrawFromBoard(data.data))
            } else if (data.type === PLAYER_BUILT_ROUTE) {
                const dt = data.data
                dispatch(playerBuiltRoute(dt.points, dt.cards, dt.routeId))
            } else if (data.type === BUILD_ROUTE) {
                const dt = data.data
                dispatch(buildRoute(dt.routeId, dt.color))
            } else if (data.type === PUT_TO_FOLDED) {
                dispatch(putToFolds(data.data))
            } else if (data.type === SET_GOAL_DONE) {
                dispatch(setGoalToDone(data.data))
            }
            //todo handle events and call dispatchers
        })
    }, [])

    playersReducer.players.forEach(p => {
        if (p.wages < 3) {
            routeChange()
        }
    })

    const actualPlayer = playersReducer.actualPlayer
    const playerId = gameReducer.playerId
    const me = playersReducer.players.find(p => p.id === playerId)
    const calculateCount = (color) => {
        return me.cards.filter(c => c === color).length
    }

    const wages = [PURPLE, WHITE, BLUE, YELLOW, ORANGE, BLACK, RED, GREEN, GRAY_LOCOMOTIVE].map(c => {
        const count = calculateCount(c)
        return <Card key={c} color={c} count={count} playerCard={true}></Card>
    })


    let i = 0;
    const goals = me.goals.map((v) => {
        i++
        return <Goal
            key={i}
            data={v}
            setHoverGoal={setHoverGoal}
        ></Goal>
    })


    const playersHtml = playersReducer.players.map(player => {
        return <PlayerBox
            key={player.name}
            name={player.name}
            point={player.points}
            wage={player.wages}
            cards={player.cards.length}
            goal={player.goals.length}
            round={player.round}
            selected={player.id === actualPlayer.id}
            color={player.color}
        />
    })


    const openedCardsHtml = wageReducer.board.map((c, index) => {
        return <Card key={index} color={c}></Card>
    })

    const customStyles = {
        content: {
            minWidth: '50vw',
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)'
        }
    };

    const cardsToBuild = () => {
        const playerCards = playersReducer.actualPlayer.cards
        const route = getRoute(routeReducer.selectedCityId, routeReducer.selectedSiblingCityId, playerCards)
        const cardCombinations = calculateCardCombinations(playerCards, route)
        return cardCombinations.map(t => {

            return <RouteBuildCards
                cards={t}
                route={route}
            />
        })
    }

    const routeHtml = () => {
        const playerCards = playersReducer.actualPlayer.cards
        const route = getRoute(routeReducer.selectedCityId, routeReducer.selectedSiblingCityId, playerCards)
        if (route && route.fromCity && route.toCity) {
            return <div className={"text-center"}>
                <h2>
                    {route.fromCity} - {route.toCity}
                </h2>
            </div>
        } else {
            return ""
        }
    }

    const modal = playersReducer.playerState === SIBLING_CITY_SELECTED ? <div>
        <Modal isOpen={playersReducer.playerState === SIBLING_CITY_SELECTED} style={customStyles}>
            {routeHtml()}
            {cardsToBuild()}
        </Modal>

    </div> : null

    return (
        <div>
            <div className={"board-container"}>
                <div className={"board-left-area"}>
                    <Map hoverGoal={hoverGoal}></Map>
                    <div className={"board-cards"}>
                        <div className={"board-cards--cards"}>
                            {openedCardsHtml}
                            <Deck></Deck>
                        </div>
                    </div>
                </div>
                {modal}
                <div className={"board-right-area"}>
                    <div className={"board-right-area--top"}>
                        <div className={"board-right-area--top-left"}>
                            {playersHtml}
                        </div>
                        <div className={"board-right-area--top-right"}>
                            <div className={"board-player_goals"}>
                                {goals}
                            </div>
                            <div className={"board-player_wages"}>
                                {wages}
                            </div>
                        </div>
                    </div>
                    <div className={"board-right-area--bottom"}>
                        <History/>
                    </div>
                </div>

            </div>

        </div>
    )
}

export default Board