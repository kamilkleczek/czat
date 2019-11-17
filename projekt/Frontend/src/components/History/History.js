import React, {useState, useEffect} from "react";
import {Redirect} from 'react-router-dom';
import {MessageType} from "../Model/Message";
import {User} from "../Model/User";
import {MainService} from "../../Service/http";

const History = ({isChatHandler}) => {
  const [ msgHistory, setMsgHistory ] = useState([]);
  const [ loading, setLoading ] = useState(true);

  useEffect(() => {
    MainService.history().then(({data}) => {
      setMsgHistory(data);
      setLoading(false);
    })
  }, [])


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
          <h2>This is our chat history!</h2>
          <button className="link-btn" type="button" onClick={() =>
            isChatHandler(true)}>Go Back to Chat</button>
        </div>
        {loading ? (
          <div className="connecting">Loading...</div>
        ) : (
            <ul id="messageArea">
              {msgHistory.map(m => getMessage(m))}
            </ul>
          )}
      </div>
    </div >
  );
};

export default History;
