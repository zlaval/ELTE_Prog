import {ticketToRideData} from "./ticket-to-ride-data";
import FastPriorityQueue from "fastpriorityqueue";
import {GRAY_LOCOMOTIVE} from "./constants";


function findRoute(graph, node) {

    node.distance = 0
    const heap = new FastPriorityQueue(function (a, b) {
        return a.distance > b.distance
    })
    heap.add(node)
    while (heap.size > 0) {
        const actual = heap.poll()

        actual.neighbours.forEach(v => {
            const child = graph.get(v)
            const dist = actual.distance + 1
            if (dist < child.distance) {
                heap.remove(child)
                child.distance = dist
                child.previous = actual
                heap.add(child)
            }
        })
    }

}

function findId(from, to, cons) {
    for (const node of cons) {
        if (node.from === from && node.to === to) {
            return `${node.id}`
        } else if (node.from === to && node.to === from) {
            return `${node.id}`
        }
    }
}

function getConnectionsById(connectionIds) {
    const result = []
    for (const [key, node] of Object.entries(ticketToRideData.connections)) {
        if (connectionIds.includes(parseInt(key))) {
            result.push(node)
        }
    }
    return result
}

export function findPath(from, to, connectionIds) {

    const cons = getConnectionsById(connectionIds)

    let path = []
    if (from !== "" && to !== "" && cons.length > 0) {

        const graph = new Map()
        for (const node of cons) {
            if (graph.has(node.from)) {
                if (!graph.get(node.from).neighbours.includes(node.to)) {
                    graph.get(node.from).neighbours.push(node.to)
                }
            } else {
                graph.set(node.from, {id: node.from, neighbours: [node.to], previous: null, distance: 1000})
            }

            if (graph.has(node.to)) {
                if (!graph.get(node.to).neighbours.includes(node.from)) {
                    graph.get(node.to).neighbours.push(node.from)
                }
            } else {
                graph.set(node.to, {id: node.to, neighbours: [node.from], previous: null, distance: 1000})
            }
        }

        if (graph.has(from) && graph.has(to)) {
            findRoute(graph, graph.get(from))
            let actual = graph.get(to)
            let prev = actual.previous
            while (prev !== null) {
                const route = findId(actual.id, prev.id, cons)
                if (!route) {
                    return []
                }
                path.unshift(route)
                actual = prev
                prev = prev.previous
            }
            return path
        }
    }

    return path
}

function calculateCardCount(color, cards) {
    return cards.filter(c => c === color).length
}

function hasEnoughCards(route, cards) {
    const locomotiveCount = calculateCardCount(GRAY_LOCOMOTIVE, cards)
    if (route.locomotive > locomotiveCount) {
        return false
    }
    const routeColor = route.color
    if (routeColor === GRAY_LOCOMOTIVE) {
        const colors = cards.reduce((acc, e) => acc.set(e, (acc.get(e) || 0) + 1), new Map());
        for (let [key, value] of colors) {
            const cardsWithLocomotive = value + locomotiveCount
            if (cardsWithLocomotive >= route.elements.length) {
                return true
            }
        }
    } else {
        const playerCardsInColor = calculateCardCount(routeColor, cards)
        const cardsWithLocomotive = playerCardsInColor + locomotiveCount
        if (cardsWithLocomotive >= route.elements.length) {
            return true
        }
    }
    return false;
}

export function findConnectedCities(city, activeRoutes, cards, wages) {
    let connectedCities = []
    if (city) {
        for (const value of activeRoutes) {
            if (value.from === city && value.elements.length <= wages) {
                if (hasEnoughCards(value, cards)) {
                    connectedCities.push(value.to)
                }
            } else if (value.to === city && value.elements.length <= wages) {
                if (hasEnoughCards(value, cards)) {
                    connectedCities.push(value.from)
                }
            }
        }
    }

    return connectedCities
}

export function getRoute(from, to, cards) {
    for (const [key, node] of Object.entries(ticketToRideData.connections)) {
        if (node.from === from && node.to === to) {
            if (hasEnoughCards(node, cards)) {
                return node
            }
        } else if (node.from === to && node.to === from) {
            if (hasEnoughCards(node, cards)) {
                return node
            }
        }
    }
    return null
}
