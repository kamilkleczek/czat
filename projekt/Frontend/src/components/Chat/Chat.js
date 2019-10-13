import React from "react";
import "./Chat.scss";

const Chat = () => {
  return (
    <div id="chat-page">
      <div className="chat-container">
        <div className="chat-header">
          <h2>Welcome in our chat!</h2>
        </div>
        {/* <div className="connecting">Connecting...</div> */}
        <ul id="messageArea"></ul>
        <form id="messageForm" name="messageForm">
          <div className="form-group">
            <div className="input-group clearfix">
              <input
                type="text"
                id="message"
                placeholder="Type a message..."
                autoComplete="off"
                className="form-control"
              />
              <button type="submit" className="primary">
                Send
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Chat;
