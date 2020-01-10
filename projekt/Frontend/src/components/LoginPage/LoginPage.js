import React, {useState} from "react";
import "./LoginPage.scss";

import {withRouter, useHistory} from "react-router-dom";
import {MainService} from "../../Service/http";
import {User} from "../Model/User";

const LoginPage = () => {
  let history = useHistory();
  const [ name, setName ] = useState("");
  const [ password, setPassword ] = useState("");
  const [ error, setError ] = useState(null);
  const [ isLoading, setLoading ] = useState(false);
  const [ isRegistering, setRegister ] = useState(false);

  const clearInputs = () => {
    setPassword("");
    setName("");
    setError(null);
  }

  const setUser = ({name, id, password}) => {
    User.password = password;
    User.name = name;
    User.id = id;
  }
  return (
    <div id="loginPage-page">
      <div className="loginPage-page-container">
        <h1 className="title">{isRegistering ? "Register your account" : "Login to our chat"}</h1>
        {error && <div className="error-msg">{error}</div>}
        <form
          id="loginPageForm"
          name="loginPageForm"
          onSubmit={event => {
            setLoading(true);
            clearInputs();
            const credentials = {name, password};
            if (isRegistering) {
              MainService.register(credentials).then(({data}) => {
                setError(null);
                setRegister(false);
              }).catch(e => {
                setError("Name Already Exists!")
              }).finally(() => setLoading(false))

            } else {
              MainService.login(credentials).then(({data}) => {
                setError(null);
                setUser(data);
                history.push("/chat");
              }).catch(e => {
                setError("Bad credentials!")
              }).finally(() => setLoading(false))
            }
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
        <button className="link-btn" type="button" onClick={() => {
          isRegistering ? setRegister(false) : setRegister(true)
          clearInputs();
        }}>{isRegistering ? "Go to Login" : "Go to Register"}</button>
      </div>
    </div>
  );
};

export default withRouter(LoginPage);
