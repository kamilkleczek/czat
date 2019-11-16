import React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import "./App.scss";
import LoginPage from "./components/LoginPage/LoginPage";
import Chat from "./components/Chat/Chat";
import History from "./components/History/History";

function App() {
  return (
    <Router>
      <div className="App">
        <div className="container">
          <Route exact path="/" component={LoginPage} />
          <Route path="/chat" component={Chat} />
          <Route path="/history" component={History} />
        </div>
      </div>
    </Router>
  );
}

export default App;
