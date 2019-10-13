import React, { useState, useEffect } from "react";
import "./Chat.scss";
import SockJsClient from "react-stomp";
import Axios from "axios";
const BACKEND_URL = "http://localhost:8080/";
const Chat = () => {
  const [connected, setConnected] = useState(false);
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [clientRef, setClientRef] = useState(null);

  useEffect(() => {
    Axios.get(`${BACKEND_URL}/history`).then(response => {
      console.log("TCL: Chat -> response", response);
      setMessages(response.data);
    });
  }, []);

  const onMessageReceive = (msg, topic) => {
    console.log("TCL: onMessageReceive -> msg", msg);

    setMessages([...messages, msg]);
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
            clientRef.sendMessage(
              "/app/all",
              JSON.stringify(message)
            );
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
        <SockJsClient
          url={`${BACKEND_URL}/handler`}
          topics={["/topic/all"]}
          onMessage={onMessageReceive}
          ref={client => setClientRef(client)}
          onConnect={() => {
            setConnected(true);
          }}
          onDisconnect={() => {
            setConnected(false);
          }}
          debug={false}
        />
      </div>
    </div>
  );
};

export default Chat;
