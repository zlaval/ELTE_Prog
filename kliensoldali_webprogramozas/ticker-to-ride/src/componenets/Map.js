import React from 'react'
import {ticketToRideData} from '../data/ticket-to-ride-data'
import City from "./City"
import './Map.css'
import {findConnectedCities, findPath} from "../data/path_finder";
import Station from "./Station";
import {useSelector} from "react-redux";

const Map = (props) => {

    const routeReducer = useSelector(state => state.routeReducer)
    const playersReducer = useSelector(state => state.playersReducer)
    const gameReducer = useSelector(state => state.gameReducer)

    const playerId = gameReducer.playerId
    const me = playersReducer.players.find(p => p.id === playerId)
    const connectedCities = findConnectedCities(
        routeReducer.selectedCityId,
        routeReducer.activeRoutes,
        playersReducer.actualPlayer.cards,
        playersReducer.actualPlayer.wages
    )

    const items = []
    for (const [key, value] of Object.entries(ticketToRideData.cities)) {
        const e = <City
            key={`${key}_city`}
            x={value.x}
            y={value.y}
            id={value.id}
            connected={connectedCities.includes(key)}
            hovered={props.hoverGoal.departure === key || props.hoverGoal.destination === key}
        />
        items.push(e)
    }

    const builtRoutes = routeReducer.builtRoutes
    let index = 0
    for (let route of builtRoutes) {
        for (const element of route.elements) {
            const e = <Station key={route.id + "_station_" + index} x={element.x} y={element.y} color={route.color}
                               built={true}/>
            items.push(e)
            index++
        }
    }

    let hover = []
    if (props.hoverGoal.built) {
        hover = findPath(props.hoverGoal.departure, props.hoverGoal.destination, me.routes)
        for (const [key, value] of Object.entries(ticketToRideData.connections)) {
            if (hover.includes(key)) {

                for (const element of value.elements) {
                    const e = <Station key={key + "_station_" + index} x={element.x} y={element.y} color={"aqua"}
                                       built={false}/>
                    items.push(e)
                    index++
                }
            }
        }
    }

    return (
        <div className={"map"}>
            <div id="map">
                <div className={"map-content"}>
                    {items}
                </div>
            </div>
        </div>
    )
}

export default Map