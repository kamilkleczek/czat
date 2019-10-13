import React from "react";
import "./LoginPage.scss"
const LoginPage = () => {
  return (
    <div id="loginPage-page">
      <div className="loginPage-page-container">
        <h1 className="title">Type your Name</h1>
        <form id="loginPageForm" name="loginPageForm">
          <div className="form-group">
            <input
              type="text"
              id="name"
              placeholder="Name"
              autoComplete="off"
              className="form-control"
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

export default LoginPage;
