import {User} from "./User";

export const MessageType = {
  Chat: "CHAT",
  Join: "JOIN",
  Leave: "LEAVE",
}
export const MessageGenerator = {
  message: (text) => {
    return JSON.stringify({
      content: text,
      sender: User.name,
      type: MessageType.Chat
    })
  },
  connect: () => {
    return JSON.stringify({
      content: `${User.name} joined!`,
      sender: User.name,
      type: MessageType.Join
    })
  }
}