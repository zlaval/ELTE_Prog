import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Home from "./containers/Home"
import Board from "./containers/Board"
import Lobby from "./containers/Lobby"
import Summary from "./containers/Summary";
import {Provider} from "react-redux";
import store from "./reducers/store"
import Modal from 'react-modal'

Modal.setAppElement('#root')

function App() {

    return (
        <Router>
            <Provider store={store}>
                <Switch>
                    <Route exact={true} path="/">
                        <Home></Home>
                    </Route>
                    <Route path="/board">
                        <Board></Board>
                    </Route>
                    <Route path="/lobby">
                        <Lobby></Lobby>
                    </Route>
                    <Route path="/summary">
                        <Summary></Summary>
                    </Route>
                </Switch>
            </Provider>
        </Router>
    );
}

export default App;
