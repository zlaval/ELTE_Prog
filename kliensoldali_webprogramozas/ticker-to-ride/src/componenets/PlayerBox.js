import React from 'react'
import './PlayerBox.css'

const playerBox = (props) => {

    const selected = props.selected ? {
        border: `6px solid blue`,
        backgroundColor: `lightgrey`
    } : {}

    const playerColor = {
        backgroundColor: `${props.color}`
    }

    return (
        <div className={"card player_box-container"} style={selected}>
            <div className={"card-body"}>
                <div className={"player_box-header"} style={playerColor}>
                    <div className={"card-title player_box-header--name"}>{props.name}</div>
                    <div className={"player_box-header--point_header"}>Pontok
                        <div className={"player_box-header--point"}>{props.point}</div>
                    </div>
                </div>
                <hr className={"player_box-hr"}/>
                <div className={"player_box-data text-center"}>
                    <div>Célok
                        <div className={"player_box-header--goal"}>{props.goal}</div>
                    </div>
                    <div>Vagonok
                        <div className={"player_box-header--wage"}>{props.wage}</div>
                    </div>
                    <div>Kártyák
                        <div className={"player_box-header--cards"}>{props.cards}</div>
                    </div>
                    <div>Kör
                        <div className={"player_box-header--round"}>{props.round}</div>
                    </div>

                </div>

            </div>

        </div>
    )

}

export default playerBox

