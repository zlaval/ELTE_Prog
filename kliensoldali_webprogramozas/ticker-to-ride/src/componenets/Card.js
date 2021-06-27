import React from 'react'
import './Card.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBan, faTrain} from "@fortawesome/free-solid-svg-icons";
import {useDispatch, useSelector} from "react-redux";
import {GRAY_LOCOMOTIVE, USER_BEGIN, USER_DRAW_CARD, USER_DRAW_LOCOMOTIVE_CARD} from "../data/constants";
import {LobbyChannel} from "../socket";
import {
    ACTUAL_PLAYER_DRAW_CARD,
    ADD_HISTORY_RECORD,
    DRAW_CARD,
    NEXT_PLAYER,
    PLAYER_DRAW_FROM_BOARD
} from "../actions/actions";
import {playerStateChange} from "../actions/playerActions";

const cardHandleStates = [USER_BEGIN, USER_DRAW_CARD, USER_DRAW_LOCOMOTIVE_CARD]

const msg = new LobbyChannel()

const Card = (props) => {

    const playersReducer = useSelector(state => state.playersReducer)
    const wageDeckReducer = useSelector(state => state.wageDeckReducer)
    const gameReducer = useSelector(state => state.gameReducer)
    const playerState = playersReducer.playerState

    const dispatch = useDispatch()

    const changeStateAfterDraw = (cardColor, place) => {
        msg.gamePlayAction(gameReducer.roomId, {
            type: ADD_HISTORY_RECORD,
            data: `${playersReducer.actualPlayer.name} draws a ${cardColor} card.`
        })
        if (place === "board" && cardColor === GRAY_LOCOMOTIVE) {
            msg.gamePlayAction(gameReducer.roomId, {type: NEXT_PLAYER})
            msg.gamePlayAction(gameReducer.roomId, {type: ADD_HISTORY_RECORD, data: `Next player moves.`})
        } else if (playerState !== USER_BEGIN) {
            msg.gamePlayAction(gameReducer.roomId, {type: NEXT_PLAYER})
            msg.gamePlayAction(gameReducer.roomId, {type: ADD_HISTORY_RECORD, data: `Next player moves.`})
        } else {
            const nextState = cardColor === GRAY_LOCOMOTIVE ? USER_DRAW_LOCOMOTIVE_CARD : USER_DRAW_CARD
            if (wageDeckReducer.board.length < 1) {
                msg.gamePlayAction(gameReducer.roomId, {type: NEXT_PLAYER})
            } else {
                dispatch(playerStateChange(nextState))
                // msg.gamePlayAction(gameReducer.roomId, {type: PLAYER_STATE_CHANGE, data: nextState})
            }
        }
    }

    const handleDraw = (cardColor) => {
        msg.gamePlayAction(gameReducer.roomId, {type: DRAW_CARD, data: cardColor})
        msg.gamePlayAction(gameReducer.roomId, {type: ACTUAL_PLAYER_DRAW_CARD, data: cardColor})
        changeStateAfterDraw(cardColor, "deck")
    }

    const handleBoardDraw = (cardColor) => {
        if (cardColor === GRAY_LOCOMOTIVE && playerState !== USER_BEGIN) {
            return
        }
        msg.gamePlayAction(gameReducer.roomId, {type: PLAYER_DRAW_FROM_BOARD, data: cardColor})
        msg.gamePlayAction(gameReducer.roomId, {type: ACTUAL_PLAYER_DRAW_CARD, data: cardColor})
        changeStateAfterDraw(cardColor, "board")
    }

    const style = props.color ? {
        backgroundColor: props.color
    } : {}

    let train = null
    if (props.color === GRAY_LOCOMOTIVE && playerState !== USER_BEGIN && !props.playerCard) {
        train = <FontAwesomeIcon className={"train-icon"} size={"lg"} icon={faBan}></FontAwesomeIcon>
    } else if (props.color === GRAY_LOCOMOTIVE) {
        train = <FontAwesomeIcon className={"train-icon"} size={"lg"} icon={faTrain}></FontAwesomeIcon>
    }

    const isActive = gameReducer.playerId === playersReducer.actualPlayer.id

    return (
        <div className={"card-container"} style={style}
             onClick={() => {
                 if (!isActive) {
                     return;
                 }
                 if (!cardHandleStates.includes(playerState)) {
                     return
                 }
                 if (props.deck) {
                     handleDraw(props.cardColor)
                 } else {
                     handleBoardDraw(props.color)
                 }
             }}
        >
            {train}
            <div className={"card-count"}>{props.count}</div>
            {props.text}
        </div>
    )
}

export default Card