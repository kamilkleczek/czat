import React, {useState} from "react";
import "./Chat.scss";
import {MessageType} from "../Model/Message";

const Chat = ({isChatHandler, sendMessageHanlder, messages}) => {
  const [ message, setMessage ] = useState("");

  const getMessage = (message) => {
    let key = `${message.sender}-${message.content.replace(/\s+/g, '')}`;
    if (message.type === MessageType.Chat) {
      return <li key={key}><div className="username">{message.sender}</div>{message.content}</li>
    }
    return <li key={key}>{message.content}</li>
  }

  return (
    <div className="chat-container">
      <div className="chat-header">
        <h2>Welcome in our chat!</h2>
        <button
          className="link-btn"
          type="button"
          onClick={() => isChatHandler(false)}>Chat History</button>
      </div>

      <ul id="messageArea">
        {messages.map(m => getMessage(m))}
      </ul>

      <form
        id="messageForm"
        name="messageForm"
        onSubmit={event => {
          sendMessageHanlder(message);
          setMessage("");
          event.preventDefault();
        }}
      >
        <div className="form-group">
          <div className="input-group clearfix">
            <input
              type="text"
              id="message" placeholder="Type a message..."
              autoComplete="off"
              value={message}
              onChange={event => setMessage(event.target.value)}
              className="form-control"
            />
            <button type="submit" className="primary">
              Send
              </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Chat;
