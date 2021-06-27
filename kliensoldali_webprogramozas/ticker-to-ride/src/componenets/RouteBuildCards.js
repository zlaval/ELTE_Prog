import React from 'react'
import './RouteBuildCards.css'
import {useSelector} from "react-redux";
import {useHistory} from "react-router-dom";
import {findPath} from "../data/path_finder";
import {BLACK} from "../data/constants";
import {LobbyChannel} from "../socket";
import {
    ADD_HISTORY_RECORD,
    BUILD_ROUTE,
    NEXT_PLAYER,
    PLAYER_BUILT_ROUTE,
    PUT_TO_FOLDED,
    SET_GOAL_DONE
} from "../actions/actions";

const msg = new LobbyChannel()

const RouteBuildCards = (props) => {

    const history = useHistory();
    const routeChange = () => {
        let path = `/summary`;
        history.push(path);
    }

    const playersReducer = useSelector(state => state.playersReducer)
    const gameReducer = useSelector(state => state.gameReducer)

    const cards = props.cards
    const colors = cards.reduce((acc, e) => acc.set(e, (acc.get(e) || 0) + 1), new Map());
    const playerColor = playersReducer.actualPlayer.color

    const select = () => {
        const player = playersReducer.actualPlayer
        const points = props.route.elements.length
        msg.gamePlayAction(gameReducer.roomId, {
            type: ADD_HISTORY_RECORD,
            data: `${player.name} built a route from ${props.route.fromCity} to ${props.route.toCity}. Earn ${points} points.`
        })

        msg.gamePlayAction(gameReducer.roomId, {
            type: PLAYER_BUILT_ROUTE,
            data: {
                points: points,
                cards: cards,
                routeId: props.route.id
            }
        })

        msg.gamePlayAction(gameReducer.roomId, {type: BUILD_ROUTE, data: {routeId: props.route.id, color: playerColor}})
        msg.gamePlayAction(gameReducer.roomId, {type: PUT_TO_FOLDED, data: cards})
        const wages = player.wages - points

        const routes = [...player.routes, props.route.id]
        player.goals.forEach(goal => {
            if (!goal.done) {
                const path = findPath(goal.from, goal.to, routes)
                if (path.length > 0) {
                    msg.gamePlayAction(gameReducer.roomId, {
                        type: ADD_HISTORY_RECORD,
                        data: `${player.name} finished the ${goal.fromCity} - ${goal.toCity} goal.`
                    })
                    msg.gamePlayAction(gameReducer.roomId, {
                        type: SET_GOAL_DONE,
                        data: goal.id
                    })
                }
            }
        })


        if (wages < 3) {
            routeChange()
        }
        // dispatch(nextPlayer())
        msg.gamePlayAction(gameReducer.roomId, {type: NEXT_PLAYER})
        msg.gamePlayAction(gameReducer.roomId, {
            type: ADD_HISTORY_RECORD,
            data: `Next player moves.`
        })
    }

    const cardHtml = []
    let j = 0;
    colors.forEach((count, color) => {
        let fontColor = "black"
        if (color === BLACK) {
            fontColor = "white"
        }

        const style = {
            backgroundColor: color,
            color: fontColor
        }

        cardHtml.push(
            <div style={style}
                 key={j}
                 className={"route_build_card text-center"}
            >
                {count}
            </div>
        )
        j++
    })

    return (<div className={"route_build_cards-container my-3 mx-3"} onClick={() => select()}>
        <div className={"route_build_cards-row"}>
            {cardHtml}
        </div>


    </div>)


}

export default RouteBuildCards