export class Store {

    readLocalStore(gameType) {
        const ls = localStorage.getItem(gameType)
        if (ls) {
            const arr = JSON.parse(ls)
            const res = []
            for (let a of arr) {
                res.push(new Score(a.name, a.point))
            }
            return res
        }
        return []
    }

    writeLocalStore(playerList, gameType) {
        let previousList = this.readLocalStore(gameType)
        if (gameType == GameType.ONE_PLAYER_BEGINNER || gameType == GameType.ONE_PLAYER_ADVANCED) {
            const player = playerList[0]
            previousList.push(new Score(player.name, player.point))
            this.saveList(previousList, gameType)
        } else if (gameType == GameType.MULTIPLAYER) {
            for (let player of playerList) {
                let find = false
                for (let saved of previousList) {
                    if (saved.name === player.name) {
                        saved.point += player.point
                        find = true
                    }
                }
                if (!find) {
                    previousList.push(new Score(player.name, player.point))
                }

            }
            this.saveList(previousList, gameType)
        }
    }

    saveList(list, gameType) {
        list.sort((a, b) => b.point - a.point)
        list = list.slice(0, 10)
        localStorage.setItem(gameType, JSON.stringify(list))
    }

}

class Score {
    constructor(name, point) {
        this.name = name
        this.point = point
    }

}


export const GameType = {
    ONE_PLAYER_BEGINNER: 1,
    ONE_PLAYER_ADVANCED: 2,
    MULTIPLAYER: 3
}