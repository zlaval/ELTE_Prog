import {BUILD_ROUTE, INIT_ROUTES, SELECT_CITY, SELECT_SIBLING_CITY, SYNC_ROUTE_STATE} from "./actions";

export function initRoutes() {
    return {
        type: INIT_ROUTES,
        payload: null
    }
}

export function selectCity(cityId) {
    return {
        type: SELECT_CITY,
        payload: cityId
    }
}

export function selectSiblingCity(cityId) {
    return {
        type: SELECT_SIBLING_CITY,
        payload: cityId
    }
}

export function buildRoute(routeId, playerColor) {
    return {
        type: BUILD_ROUTE,
        payload: {
            routeId,
            playerColor
        }
    }
}

export function syncRouteState(data) {
    return {
        type: SYNC_ROUTE_STATE,
        payload: data
    }
}