import React from 'react'
import './History.css'
import {useSelector} from "react-redux";

const History = (props) => {

    const historyReducer = useSelector(state => state.historyReducer)

    const historyHtml = historyReducer
        .filter((h, index) => index > historyReducer.length - 11)
        .map((h, index) => {
            const i = index + 1
            return <div key={i}>{i}. {h}</div>
        })

    return (
        <div className={"history-container"}>
            <div>Előzmények</div>
            {historyHtml}
        </div>
    )
}

export default History