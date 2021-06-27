import React from 'react'
import './Deck.css'
import Card from "./Card";
import {useSelector} from "react-redux";

const Deck = (props) => {
    const wageDeckReducer = useSelector(state => state.wageDeckReducer)
    const cards = wageDeckReducer.deck
    if (cards.length <= 0) {
        return null
    }

    const cardsHtml = cards.map((card, i) => {
        return (<li key={i}>
            <Card cardColor={card} text={"Kocsik"} deck={true}/>
        </li>)
    })

    return (
        <div className={"deck-container"}>
            <div className={"deck-name"}>{props.name}</div>
            <div className={"deck-cards"}>
                <ul className={"deck"}>
                    {cardsHtml}
                </ul>
            </div>
        </div>


    )

}

export default Deck