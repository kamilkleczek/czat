import React, { useState } from "react";
import "./Chat.scss";
import SockJsClient from "react-stomp";

const Chat = () => {
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState([]);

  return (
    <div id="chat-page">
      <div className="chat-container">
        <div className="chat-header">
          <h2>Welcome in our chat!</h2>
        </div>
        {!connected ? (
          <div className="connecting">Connecting...</div>
        ) : (
          <ul id="messageArea"></ul>
        )}

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
              <button type="submit" className="primary" disabled={!connected}>
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
