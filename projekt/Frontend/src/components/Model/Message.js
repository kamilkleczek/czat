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
      type: MessageType.Chat,
      senderId: User.id,
    })
  },
  connect: () => {
    return JSON.stringify({
      content: `${User.name} joined!`,
      senderId: User.id,
      sender: User.name,
      type: MessageType.Join
    })
  }
}