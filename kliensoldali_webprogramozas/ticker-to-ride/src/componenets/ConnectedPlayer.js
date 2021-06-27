import React from 'react'
import './ConnectedPlayer.css'

const ConnectedPlayer = (props) => {

    const colorStyle = {
        backgroundColor: `${props.color}`
    }

    return (
        <div className={"connected-player-container text-center"} style={colorStyle}>
            <div className={"connected-player-name"}>{props.name}</div>
        </div>
    )

}

export default ConnectedPlayer

