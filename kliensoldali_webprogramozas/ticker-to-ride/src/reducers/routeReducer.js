import {ticketToRideData} from "../data/ticket-to-ride-data";
import {BUILD_ROUTE, INIT_ROUTES, SELECT_CITY, SELECT_SIBLING_CITY, SYNC_ROUTE_STATE} from "../actions/actions";

function initState() {
    const routes = []
    for (const [key, value] of Object.entries(ticketToRideData.connections)) {
        routes.push(value)
    }

    return {
        activeRoutes: routes,
        builtRoutes: [],
        selectedCityId: null,
        selectedSiblingCityId: null
    }
}

function routeReducer(state = initState(), action) {

    const {type, payload} = action

    switch (type) {
        case SYNC_ROUTE_STATE:
            return payload
        case INIT_ROUTES:
            return initState()
        case BUILD_ROUTE:
            const newActiveRoutes = state.activeRoutes.filter(a => a.id !== payload.routeId)
            const newlyBuiltRoute = state.activeRoutes.filter(a => a.id === payload.routeId).map(r => {
                return {
                    ...r,
                    color: payload.playerColor
                }
            })
            return {
                activeRoutes: newActiveRoutes,
                builtRoutes: [...state.builtRoutes, ...newlyBuiltRoute],
                selectedCityId: null,
                selectedSiblingCityId: null
            }
        case SELECT_CITY:
            let selectedSibling = state.selectedSiblingCityId
            if (!payload) {
                selectedSibling = null
            }
            return {
                ...state,
                selectedCityId: payload,
                selectedSiblingCityId: selectedSibling
            }
        case SELECT_SIBLING_CITY:
            return {
                ...state,
                selectedSiblingCityId: payload
            }
        default:
            return state
    }
}

export default routeReducer