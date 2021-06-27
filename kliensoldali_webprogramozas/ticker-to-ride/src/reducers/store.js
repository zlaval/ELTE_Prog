import {combineReducers, createStore} from 'redux'
import playersReducer from "./playersReducer";
import {devToolsEnhancer} from "redux-devtools-extension";
import wageDeckReducer from "./wageDeckReducer";
import historyReducer from "./historyReducer";
import routeReducer from "./routeReducer";
import gameReducer from "./gameReducer";


const store = createStore(combineReducers({
            playersReducer,
            wageDeckReducer,
            historyReducer,
            routeReducer,
            gameReducer
    }),
    devToolsEnhancer()
)

export default store