import React, { useState } from "react";
import "./Chat.scss";
import SockJsClient from "react-stomp";

const Chat = () => {
  const [connected, setConnected] = useState(false);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);

  const onMessageReceive = (msg, topic) => {
    console.log("TCL: onMessageReceive -> msg", msg);
    // this.setState(prevState => ({
    //   messages: [...prevState.messages, msg]
    // }));
  };

  const sendMessage = (msg, selfMsg) => {
    console.log("TCL: sendMessage -> selfMsg", selfMsg);
    // try {
    //   this.clientRef.sendMessage("/app/all", JSON.stringify(selfMsg));
    //   return true;
    // } catch(e) {
    //   return false;
    // }
  };
  return (
    <div id="chat-page">
      <div className="chat-container">
        <div className="chat-header">
          <h2>Welcome in our chat!</h2>
        </div>
        {!connected ? (
          <div className="connecting">Connecting...</div>
        ) : (
          <ul id="messageArea">
            {messages.map(text => (
              <li>{text}</li>
            ))}
          </ul>
        )}

        <form
          id="messageForm"
          name="messageForm"
          onSubmit={event => {
            setMessage("");
            event.preventDefault();
          }}
        >
          <div className="form-group">
            <div className="input-group clearfix">
              <input
                type="text"
                id="message"
                placeholder="Type a message..."
                autoComplete="off"
                value={message}
                onChange={event => setMessage(event.target.value)}
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
