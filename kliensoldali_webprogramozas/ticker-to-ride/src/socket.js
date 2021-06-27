import {io} from "socket.io-client";
import {SYNC_NAME} from "./actions/actions";

//const path = "http://localhost:3031"
const path = "http://webprogramozas.inf.elte.hu:3031"

let socket

export class LobbyChannel {
    constructor() {
        if (!socket) {
            socket = io(path)
        }
    }

    socketId() {
        return socket.id
    }

    create(size, handler) {
        const ack = async function (resp) {
            handler(resp.status, resp.roomId)
        }
        socket.emit("create-room", size, ack)
    }

    join(roomName, handler) {
        const ack = (resp) => {
            handler(resp.status, resp.state, resp.message)
        }
        socket.emit("join-room", roomName, ack)
    }

    syncName(name, roomId, userId, handler) {
        const ack = (resp) => {
        }
        socket.emit("sync-action", roomId, {type: SYNC_NAME, name: name, id: userId}, true, ack)
    }

    onSyncName(handler) {
        //action-sent
        socket.on("action-sent", data => {
            if (data.action.type === SYNC_NAME) {
                handler(data.action)
            }
        })
    }

    syncGameState(roomId, gameState) {
        const ack = (resp) => {
        }
        socket.emit("sync-state", roomId, gameState, false, ack)
    }

    onSyncGameState(handler) {
        socket.on("state-changed", data => {
            handler(data.state)
        })
    }

    syncBoardState(roomId, state) {
        const ack = (resp) => {
        }
        socket.emit("sync-state", roomId, state, true, ack)
    }

    onSyncBoardState(handler) {
        socket.on("state-changed", data => {
            handler(data.state)
        })
    }

    gamePlayAction(roomId, payload) {
        const ack = (resp) => {
        }
        socket.emit("sync-action", roomId, payload, false, ack)
    }

    onGamePlayAction(handler) {
        socket.on("action-sent", data => {
            if (data.action.type !== SYNC_NAME) {
                handler(data.action)
            }
        })
    }


}