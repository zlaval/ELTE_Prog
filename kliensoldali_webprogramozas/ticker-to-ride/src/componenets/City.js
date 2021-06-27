import React from 'react'
import './City.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCity, faHammer, faMapMarkerAlt} from "@fortawesome/free-solid-svg-icons";
import {useDispatch, useSelector} from "react-redux";
import {selectCity, selectSiblingCity} from "../actions/routeAction";
import {playerStateChange} from "../actions/playerActions";
import {CITY_SELECTED, SIBLING_CITY_SELECTED, USER_BEGIN} from "../data/constants";
import {LobbyChannel} from "../socket";
import {ADD_HISTORY_RECORD} from "../actions/actions";

const msg = new LobbyChannel()

const cityHandleStates = [CITY_SELECTED, SIBLING_CITY_SELECTED, USER_BEGIN]
const City = (props) => {

    const dispatch = useDispatch()
    const routeReducer = useSelector(state => state.routeReducer)
    const playersReducer = useSelector(state => state.playersReducer)
    const gameReducer = useSelector(state => state.gameReducer)

    const afterCitySelected = (cityId, type) => {
        let nextState
        if (cityId) {
            nextState = type ? SIBLING_CITY_SELECTED : CITY_SELECTED
            msg.gamePlayAction(gameReducer.roomId, {
                type: ADD_HISTORY_RECORD,
                data: `${playersReducer.actualPlayer.name} selects a city.`
            })

        } else {
            nextState = type ? CITY_SELECTED : USER_BEGIN
            msg.gamePlayAction(gameReducer.roomId, {
                type: ADD_HISTORY_RECORD,
                data: `${playersReducer.actualPlayer.name} deselects a city.`
            })
        }
        dispatch(playerStateChange(nextState))
        // msg.gamePlayAction(gameReducer.roomId, {
        //     type: PLAYER_STATE_CHANGE,
        //     data: nextState
        // })
    }

    const selectCityHandler = (cityId) => {
        dispatch(selectCity(cityId))
        afterCitySelected(cityId)
    }
    const selectSiblingCityHandler = (cityId) => {
        dispatch(selectSiblingCity(cityId))
        afterCitySelected(cityId, "sibling")
    }

    const cityClicked = () => {
        const playerState = playersReducer.playerState
        if (!cityHandleStates.includes(playerState)) {
            return
        }
        if (playerState === SIBLING_CITY_SELECTED && !isSelectedCity && !isSelectedSiblingCity) {
            return;
        }

        if (playerState === CITY_SELECTED && !props.connected && !isSelectedCity) {
            return;
        }

        if (isSelectedCity) {
            selectCityHandler(null)
        } else if (isSelectedSiblingCity) {
            selectSiblingCityHandler(null)
        } else if (props.connected) {
            selectSiblingCityHandler(props.id)
        } else {
            selectCityHandler(props.id)
        }
    }

    const isSelectedCity = routeReducer.selectedCityId === props.id
    const isSelectedSiblingCity = routeReducer.selectedSiblingCityId === props.id

    const style = {
        left: `${props.x - 0.6}%`,
        top: `${props.y - 0.6}%`,
    }

    const hover = props.hovered ? {
        border: '4px solid black',
        height: '1.8vw',
        width: '1.8vw',
        backgroundColor: 'red'
    } : {}

    let icon
    if (isSelectedCity) {
        icon = <FontAwesomeIcon className={"city-fa_icon"} icon={faCity}></FontAwesomeIcon>
    } else if (isSelectedSiblingCity) {
        icon = <FontAwesomeIcon className={"city-fa_icon"} icon={faHammer}></FontAwesomeIcon>
    } else if (props.connected) {
        icon = <FontAwesomeIcon className={"city-fa_icon"} icon={faMapMarkerAlt}></FontAwesomeIcon>
    } else {
        icon = <div className={"city-icon"}></div>
    }

    const isActive = gameReducer.playerId === playersReducer.actualPlayer.id
    return (
        <div className={"city-marker text-center"} style={{...style, ...hover}} onClick={
            () => {
                if (isActive) {
                    cityClicked()
                }
            }
        }>
            {icon}
        </div>
    )
}

export default City