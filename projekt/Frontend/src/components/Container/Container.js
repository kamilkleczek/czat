import React, {useState} from 'react'
import {Redirect, useHistory} from 'react-router-dom';
import SockJsClient from "react-stomp";
import {User} from '../Model/User';
import {BACKEND_URL} from "../../Service/settings";
import {MessageGenerator} from "../Model/Message";
import Chat from '../Chat/Chat';
import History from '../History/History';

const Contaier = () => {
  let history = useHistory();
  const [ isChat, setIsChat ] = useState(true);
  const [ clientRef, setClientRef ] = useState(null);
  const [ messages, setMessages ] = useState([]);
  const onMessageReceive = (msg, topic) => {
    setMessages([ ...messages, msg ]);
  };
  const sendMessageHanlder = (message) => {
    clientRef.sendMessage(
      "/app/all",
      MessageGenerator.message(message)
    );
  }
  const isChatHandler = (flag) => {
    setIsChat(flag)
  }
  const clearUser = () => {
    User.id = null;
    User.name = null;
    User.password = null;
    history.push('/')
  }
  if (User.id == null) {
    return <Redirect to="/" />
  }


  return (<div id="chat-page">
    <div id="logged-user">
      Hello {User.name}
      <button
        className="link-btn"
        type="button"
        onClick={() => clearUser()}>Logout</button>
    </div>
    {isChat ?
      <Chat
        messages={messages}
        isChatHandler={isChatHandler}
        sendMessageHanlder={sendMessageHanlder} /> :
      <History
        isChatHandler={isChatHandler}
      />};
    <SockJsClient
      url={`${BACKEND_URL}/handler`}
      topics={[ "/topic/all" ]}
      onMessage={onMessageReceive}
      ref={client => setClientRef(client)}
      onConnect={() => {
        clientRef.sendMessage(
          "/app/all",
          MessageGenerator.connect()
        );
      }}
      onDisconnect={() => {
        setTimeout(() => {
          User.id = null;
          User.name = null;
          User.password = null;
          history.push("/")
        }, 3000)
      }}
      debug={false}
    />

  </div>
  );
}

export default Contaier;