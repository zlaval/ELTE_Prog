import {GRAY_LOCOMOTIVE} from "./constants";

function calculateCardCount(color, cards) {
    if (!cards) {
        return 0
    }
    return cards.filter(c => c === color).length
}

export function calculateCardCombinations(cards, route) {
    if (!cards || !route) {
        return []
    }
    const result = []
    const routeColor = route.color
    const routeLength = route.elements.length
    const playerLocomotives = calculateCardCount(GRAY_LOCOMOTIVE, cards)
    if (routeColor === GRAY_LOCOMOTIVE) {
        const colors = cards.reduce((acc, e) => acc.set(e, (acc.get(e) || 0) + 1), new Map());
        for (let [key, value] of colors) {
            let playerUnusedLocomotives = playerLocomotives
            const arr = []
            for (let i = 0; i < route.locomotive; ++i) {
                arr.push(GRAY_LOCOMOTIVE)
                playerUnusedLocomotives -= 1
            }
            const cardsNeeded = routeLength - arr.length
            for (let i = 0; i < cardsNeeded && i < value; ++i) {
                if (key === GRAY_LOCOMOTIVE) {
                    playerUnusedLocomotives -= 1
                }
                arr.push(key)
            }
            let j = 0
            const locomotiveNeed = routeLength - arr.length
            while (j < locomotiveNeed && playerUnusedLocomotives > 0) {
                arr.push(GRAY_LOCOMOTIVE)
                playerUnusedLocomotives -= 1
                j++
            }
            if (arr.length === routeLength && playerUnusedLocomotives >= 0) {
                result.push(arr)
            }
        }
    } else {
        const arr = []
        const playerCardsInColor = calculateCardCount(routeColor, cards)
        for (let i = 0; i < route.locomotive; ++i) {
            arr.push(GRAY_LOCOMOTIVE)
        }
        const cardsNeeded = routeLength - arr.length
        for (let i = 0; i < cardsNeeded && i < playerCardsInColor; ++i) {
            arr.push(routeColor)
        }
        let j = 0
        const locomotiveNeed = routeLength - arr.length
        while (j < locomotiveNeed) {
            arr.push(GRAY_LOCOMOTIVE)
            j++
        }

        result.push(arr)
    }


    return result
}