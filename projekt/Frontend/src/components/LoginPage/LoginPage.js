import React, {useState} from "react";
import "./LoginPage.scss";

import {withRouter} from "react-router-dom";
import {User} from "../Model/User";

const LoginPage = props => {
  const [ name, setName ] = useState("");
  const [ password, setPassword ] = useState("");
  const [ isLoading, setLoading ] = useState(false);
  const [ isRegistering, setRgister ] = useState(false);


  return (
    <div id="loginPage-page">
      <div className="loginPage-page-container">
        <h1 className="title">{isRegistering ? "Register your account" : "Login to our chat"}</h1>
        <form
          id="loginPageForm"
          name="loginPageForm"
          onSubmit={event => {
            setLoading(true);
            User.name = name;
            User.password = password;
            if (isRegistering) {
              console.log("TCL: Registering User", User)
            } else {
              console.log("TCL: Login in User", User)
            }
            // props.history.push("/chat");
            event.preventDefault();
          }}
        >
          <div className="form-group">
            <input
              type="text"
              id="name"
              placeholder="Name"
              value={name}
              autoComplete="off"
              className="form-control"
              onChange={event => setName(event.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="password"
              placeholder="Password"
              value={password}
              autoComplete="off"
              className="form-control"
              onChange={event => setPassword(event.target.value)}
            />
          </div>
          <div className="form-group">
            <button type="submit" className="accent loginPage-submit" disabled={isLoading}>
              {isRegistering ? "Register" : "Start Chatting"}
            </button>
          </div>
        </form>
        <button className="register-btn" type="button" onClick={() => isRegistering ? setRgister(false) : setRgister(true)}>{isRegistering ? "Go to Login" : "Go to Register"}</button>
      </div>
    </div>
  );
};

export default withRouter(LoginPage);
