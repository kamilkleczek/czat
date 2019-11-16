import React, {useState} from "react";
import {Redirect, useHistory} from 'react-router-dom';
import "./Chat.scss";
import SockJsClient from "react-stomp";
import {MessageGenerator, MessageType} from "../Model/Message";
import {BACKEND_URL} from "../../Service/settings";
import {User} from "../Model/User";
const Chat = () => {
  let history = useHistory();
  const [ connected, setConnected ] = useState(false);
  const [ message, setMessage ] = useState("");
  const [ messages, setMessages ] = useState([]);
  const [ clientRef, setClientRef ] = useState(null);

  const onMessageReceive = (msg, topic) => {
    setMessages([ ...messages, msg ]);
  };

  const getMessage = (message) => {
    let key = `${message.sender}-${message.content.replace(/\s+/g, '')}`;
    if (message.type === MessageType.Chat) {
      return <li key={key}><div className="username">{message.sender}</div>{message.content}</li>
    }
    return <li key={key}>{message.content}</li>
  }
  if (User.id == null) {
    return <Redirect to="/" />
  }

  return (
    <div id="chat-page">
      <div className="chat-container">
        <div className="chat-header">
          <h2>Welcome in our chat!</h2>
          <button className="link-btn" type="button" onClick={() => {
            history.push('/history');
          }}>Chat History</button>
        </div>
        {!connected ? (
          <div className="connecting">Connecting...</div>
        ) : (
            <ul id="messageArea">
              {messages.map(m => getMessage(m))}
            </ul>
          )}

        <form
          id="messageForm"
          name="messageForm"
          onSubmit={event => {
            clientRef.sendMessage(
              "/app/all",
              MessageGenerator.message(message)
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
          topics={[ "/topic/all" ]}
          onMessage={onMessageReceive}
          ref={client => setClientRef(client)}
          onConnect={() => {
            setConnected(true);
            clientRef.sendMessage(
              "/app/all",
              MessageGenerator.connect()
            );
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
