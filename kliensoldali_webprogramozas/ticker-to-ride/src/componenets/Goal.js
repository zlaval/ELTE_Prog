import React from 'react'
import './Goal.css'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheck} from "@fortawesome/free-solid-svg-icons";

const Goal = (props) => {

    const style = props.data.done ? {
        backgroundColor: 'gray'
    } : {}

    const check = props.data.done ? <FontAwesomeIcon className={"goal-done"} icon={faCheck}></FontAwesomeIcon> : null

    return (
        <div className={"goal-container"}
             style={style}
             onMouseEnter={() => props.setHoverGoal({
                 departure: props.data.from,
                 destination: props.data.to,
                 built: props.data.done
             })}
             onMouseLeave={() => props.setHoverGoal({departure: "", destination: "", built: false})}>
            <div>{props.data.fromCity}</div>
            <div>-</div>
            <div>{props.data.toCity}</div>
            {check}
        </div>
    )
}

export default Goal