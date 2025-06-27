// // components/chat/ChatRoom.js
// import React, { useEffect, useState } from "react";
// import WebSocketService from "./WebSocketService";

// const ChatRoom = () => {
//   const [messages, setMessages] = useState([]);
//   const [text, setText] = useState("");

//   useEffect(() => {
//     WebSocketService.connect((msg) => {
//       setMessages(prev => [...prev, msg.body]);
//     });
//   }, []);

//   const sendMessage = () => {
//     WebSocketService.sendMessage(text);
//     setText("");
//   };

//   return (
//     <div>
//       <h2>Chat Room</h2>
//       <div style={{ border: "1px solid #ccc", height: "200px", overflowY: "scroll" }}>
//         {messages.map((msg, i) => (
//           <p key={i}>{msg}</p>
//         ))}
//       </div>
//       <input value={text} onChange={e => setText(e.target.value)} placeholder="Type a message" />
//       <button onClick={sendMessage}>Send</button>
//     </div>
//   );
// };

// export default ChatRoom;
import React, { useEffect, useState } from "react";
import WebSocketService from "./WebSocketService";

// Optionally get the username from localStorage or auth context
const senderName = localStorage.getItem("username") || "Anonymous";

const ChatRoom = () => {
  const [messages, setMessages] = useState([]);
  const [text, setText] = useState("");

  useEffect(() => {
    WebSocketService.connect((msg) => {
      const parsed = JSON.parse(msg.body);
      setMessages(prev => [...prev, parsed]);
    });
  }, []);

  const sendMessage = () => {
    const messagePayload = {
      senderName: senderName,
      receiverName: "Everyone",       // or allow selecting a user
      message: text,
      media: null,
      mediaType: null,
      status: "MESSAGE"
    };

    WebSocketService.sendMessage(messagePayload);
    setText("");
  };

  return (
    <div>
      <h2>Chat Room</h2>
      <div style={{ border: "1px solid #ccc", height: "200px", overflowY: "scroll", padding: "10px" }}>
        {messages.map((msg, i) => (
          <p key={i}><strong>{msg.senderName}:</strong> {msg.message}</p>
        ))}
      </div>
      <input value={text} onChange={e => setText(e.target.value)} placeholder="Type a message" />
      <button onClick={sendMessage}>Send</button>
    </div>
  );
};

export default ChatRoom;
