import React from 'react'
import './Summary.css'
import {animated, useSpring as UseSpring} from "react-spring";
import {ticketToRideData} from "../data/ticket-to-ride-data";
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";

function collectGoals(goalIds) {

    return Object.entries(ticketToRideData.destinations).filter(([k, v]) => {
        return goalIds.includes(k)
    }).map(([k, v]) => {
        return {from: v.fromCity, to: v.toCity, point: parseInt(v.value)}
    })
}


const Summary = (props) => {

    const playersReducer = useSelector(state => state.playersReducer)

    const animations = [
        UseSpring({opacity: 1, from: {opacity: 0}, delay: 150}),
        UseSpring({opacity: 1, from: {opacity: 0}, delay: 300}),
        UseSpring({opacity: 1, from: {opacity: 0}, delay: 450}),
        UseSpring({opacity: 1, from: {opacity: 0}, delay: 600}),
        UseSpring({opacity: 1, from: {opacity: 0}, delay: 750}),
    ]

    const res = playersReducer.players.map((m, i) => {
        let points = m.points

        const successGoals = m.goals.filter(g => g.done)
        const failedGoals = m.goals.filter(g => !g.done)
        successGoals.forEach(s => {
            points += parseInt(s.value)
        })
        failedGoals.forEach(f => {
            points -= parseInt(f.value)
        })

        const successGoalIds = successGoals.map(g => g.id)
        const failedGoalIds = failedGoals.map(g => g.id)

        const success = collectGoals(successGoalIds)
            .map(g => {
                return <div className={"summary-box--goals"}>
                    <div>{g.from} - {g.to}</div>
                    <div>+ {g.point}</div>
                </div>
            })

        const failed = collectGoals(failedGoalIds)
            .map(g => {
                return <div className={"summary-box--goals"}>
                    <div>{g.from} - {g.to}</div>
                    <div>- {g.point}</div>
                </div>
            })

        const longestPathHtml = i === 1 ?
            <div className={"text-center my-2 summary-box-longest_path"}>
                +10 pont a leghosszabb útért
            </div> : null

        return <animated.div key={i} style={animations[i]} className={"summary-box"}>
            <div className={"summary-box-name text-center"}>{m.name}</div>
            <div className={"summary-box-body"}>
                <div className={"summary-box-point text-center my-2"}>Építések pontszáma {m.points}</div>
                <div className={"summary-box-success"}>Teljesített célok:
                    <div className={"summary-box-success--list"}>
                        {success}
                    </div>
                </div>
                <div className={"summary-box-failed"}>Nem teljesített célok:
                    <div className={"summary-box-failed--list"}>
                        {failed}
                    </div>
                </div>
                {longestPathHtml}
            </div>
            <div className={"summary-box-result text-center"}>Összesített pontszám:
                <div className={"summary-box-result--point"}>
                    {points}
                </div>
            </div>
        </animated.div>
    })

    return (
        <div>
            <div className={"summary-container"}>
                {res}

            </div>
            <div className={"text-center"}>
                <Link
                    className={"btn btn-warning btn-lg my-5"}
                    to={{
                        pathname: "/"
                    }}
                >
                    Kezdőlap
                </Link>
            </div>
        </div>
    )
}

export default Summary