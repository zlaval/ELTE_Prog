import React, {useState} from 'react'
import './Home.css'
import {useHistory} from "react-router-dom";
import logo from '../asset/logo.png'
import {useDispatch} from "react-redux";
import {LobbyChannel} from "../socket";
import {initGame} from "../actions/gameAction";

const msg = new LobbyChannel()

const Home = (props) => {
    const [startNewGameClicked, setNewGameClicked] = useState(false)
    const [connectToGameClicked, setConnectToGameClicked] = useState(false)
    const [playerName, setPlayerName] = useState("")
    const [roomName, setRoomName] = useState("")
    const [playerCount, setPlayerCount] = useState(2)

    let renderHtml = ""
    let roomHtml = ""
    let playerCountHtml = ""

    let urlQuery = ""
    let showButton = false

    if (startNewGameClicked && playerName.length > 0) {
        urlQuery = `?name=${playerName}&playerCount=${playerCount}`
        showButton = true
    } else if (connectToGameClicked && playerName.length > 0 && roomName.length > 0) {
        urlQuery = `?name=${playerName}&room=${roomName}`
        showButton = true
    }

    const dispatch = useDispatch()
    const history = useHistory()

    const startGame = () => {
        msg.create(10, (status, roomId) => {
            if (status === "ok") {
                dispatch(initGame({
                    master: true,
                    playerId: msg.socketId(),
                    playerName: playerName,
                    maxPlayerCount: playerCount,
                    roomId: roomId
                }))
                history.push("/lobby")
            } else {
                alert("Failed to create room")
            }
        })

    }

    const joinGame = () => {
        msg.join(roomName, (status, state, message) => {
            if (status === "ok") {
                const userId = msg.socketId()
                dispatch(initGame({
                    master: false,
                    playerId: userId,
                    playerName: playerName,
                    playerCount: 1,
                    maxPlayerCount: 1,
                    roomId: roomName
                }))
                msg.syncName(playerName, roomName, userId, (resp) => {
                })
                history.push("/lobby")
            } else {
                alert(message)
            }
        })
    }


    const radioChangeFn = (e) => setPlayerCount(parseInt(e.target.value))

    if (startNewGameClicked) {
        playerCountHtml = (
            <div>
                <div className={"home-player_count my-3"}>
                    <div className={"home-player_count--input mx-3"}>
                        <input
                            type="radio"
                            value="2"
                            name="gender"
                            className={"mx-1"}
                            checked={playerCount === 2}
                            onChange={radioChangeFn}/> 2 játékos
                    </div>
                    <div className={"home-player_count--input mx-3"}>
                        <input
                            type="radio"
                            value="3"
                            name="gender"
                            className={"mx-1"}
                            checked={playerCount === 3}
                            onChange={radioChangeFn}/> 3 játékos
                    </div>
                    <div className={"home-player_count--input mx-3"}>
                        <input
                            type="radio"
                            value="4"
                            name="gender"
                            className={"mx-1"}
                            checked={playerCount === 4}
                            onChange={radioChangeFn}/> 4 játékos
                    </div>
                    <div className={"home-player_count--input mx-3"}>
                        <input
                            type="radio"
                            value="5"
                            name="gender"
                            className={"mx-1"}
                            checked={playerCount === 5}
                            onChange={radioChangeFn}/> 5 játékos
                    </div>
                </div>
            </div>
        )
    }

    if (connectToGameClicked) {
        roomHtml = (<div>
                <label className={"home-label"}>Szoba neve</label>
                <input
                    type="text"
                    className={"form-control"}
                    required
                    value={roomName}
                    onChange={(e) => setRoomName(e.target.value)}
                />

                <div>
                    <button
                        className={"btn btn-primary btn-lg my-5"}
                        onClick={() => {
                            joinGame()
                        }}
                    >
                        Csatlakozás
                    </button>
                </div>
            </div>
        )
    }
    let btn = ""
    if (startNewGameClicked) {
        btn = <div>
            <button
                className={"btn btn-primary btn-lg my-5"}
                onClick={() => {
                    startGame()
                }}
            >
                Játék indítása
            </button>
        </div>
    }

    if (startNewGameClicked || connectToGameClicked) {
        renderHtml = (<div className={"text-center my-5"}>
            {playerCountHtml}

            <div className={"home-form"}>
                <label className={"home-label"}>Név</label>
                <input
                    type="text"
                    className={"form-control"}
                    required
                    value={playerName}
                    onChange={(e) => setPlayerName(e.target.value)}
                />

                {btn}

                {roomHtml}

            </div>
        </div>)
    } else {
        renderHtml = <div>
            <div className={"text-center my-5"}>
                <button
                    className={"btn btn-primary btn-lg"}
                    onClick={() => {
                        setConnectToGameClicked(false)
                        setNewGameClicked(true)
                    }}
                >
                    Új játék
                </button>
            </div>
            <div className={"text-center my-5"}>
                <button
                    className={"btn btn-warning  btn-lg"}
                    onClick={() => {
                        setNewGameClicked(false)
                        setConnectToGameClicked(true)
                    }}
                >
                    Csatlakozás játékhoz
                </button>
            </div>
        </div>
    }


    return (
        <div>
            <h1 className={"text-center home-title"}>
                <img className={"home-logo"} src={logo} alt={"logo"}/>
            </h1>

            {renderHtml}

            <div className={"text-center my-5"}>
                <div className={"home-rule"}>
                    <h2 className={"home-rule-title"}>A játék</h2>
                    <p>Edinburgh sziklás hegyoldalaitól Konstantinápoly napos partjaiig, a szürke Pamplona
                        szövetségtől a szeles berlini állomásig a Ticket to Ride Europe elvisz a századforduló
                        Európájának
                        nagy városain át egy új vasúti kalandra.
                        Vállalod az utazás rizikóját át Svájc sötét alagútjain? Megkockáztatod az utazástegy
                        fekete-tengeri
                        komp fedélzetén, vagy létrehozod régi birodalmak nagy fővárosainak pompás vasútállomásait?
                        Itt a nagy lehetőség, hogy te legyél Európa legnagyobb vasúti mágnása!
                        Kapd fel a csomagod, üdvözöld a kalauzt és szállj fel!</p>
                    <a
                        target={"_blank"}
                        rel={"noreferrer"}
                        href={"https://tarsasjatekrendeles.hu/shop_ordered/7237/pic/Compaya/Ticket_To_Ride_Europe.pdf"}>
                        Részletes játékszabály
                    </a>
                </div>
            </div>
        </div>
    )
}

export default Home