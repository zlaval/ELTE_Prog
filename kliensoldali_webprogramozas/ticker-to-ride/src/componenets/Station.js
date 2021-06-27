import React from 'react'
import './Station.css'

const Station = props => {

    const divStyle = {
        left: `${props.x}%`,
        top: `${props.y}%`,
        backgroundColor: `${props.color}`
    }
    const built = props.built ? {
        border: '2px solid black'
    } : {
        height: '0.8vw',
        width: '0.8vw'
    }

    return (
        <div className={"station-marker"} style={{...divStyle, ...built}}>
        </div>
    )
}

export default Station