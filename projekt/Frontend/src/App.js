import React from "react";
import { BrowserRouter as Router, Route } from "react-router-dom";
import "./App.scss";
import LoginPage from "./components/LoginPage/LoginPage";
import Contaier from "./components/Container/Container";

function App() {
  return (
    <Router>
      <div className="App">
        <div className="container">
          <Route exact path="/" component={LoginPage} />
          <Route path="/chat" component={Contaier} />
        </div>
      </div>
    </Router>
  );
}

export default App;
