import React, {useState} from "react";
import "./LoginPage.scss";
import {withRouter} from "react-router-dom";
import {User} from "../Model/User";

const LoginPage = props => {
  const [ name, setName ] = useState("");

  return (
    <div id="loginPage-page">
      <div className="loginPage-page-container">
        <h1 className="title">Type your Name</h1>
        <form
          id="loginPageForm"
          name="loginPageForm"
          onSubmit={event => {
            User.name = name;
            props.history.push("/chat");
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
            <button type="submit" className="accent loginPage-submit">
              Start Chatting
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default withRouter(LoginPage);
