import {ADD_HISTORY_RECORD, INIT_HISTORY} from "../actions/actions";


function historyReducer(state = [], action) {
    const {type, payload} = action
    switch (type) {
        case INIT_HISTORY:
            return []
        case ADD_HISTORY_RECORD:
            return [...state, payload]
        default:
            return state
    }

}

export default historyReducer