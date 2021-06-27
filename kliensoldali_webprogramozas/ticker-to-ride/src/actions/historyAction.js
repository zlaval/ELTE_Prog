import {ADD_HISTORY_RECORD, INIT_HISTORY} from "./actions";

export function addHistory(record) {
    return {
        type: ADD_HISTORY_RECORD,
        payload: record
    }
}

export function initHistory() {
    return {
        type: INIT_HISTORY,
        payload: null
    }
}