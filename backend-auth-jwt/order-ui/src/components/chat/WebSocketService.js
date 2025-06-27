// // components/chat/WebSocketService.js
// import SockJS from "sockjs-client";
// import { Stomp } from "@stomp/stompjs";

// const WebSocketService = {
//   stompClient: null,

//   connect(onMessageReceived) {
//     const socket = new SockJS("http://localhost:8080/ws");
//     this.stompClient = Stomp.over(socket);
    
//     this.stompClient.connect({}, () => {
//       this.stompClient.subscribe("/topic/messages", onMessageReceived);
//     });
//   },

//   sendMessage(msg) {
//     if (this.stompClient && this.stompClient.connected) {
//       this.stompClient.send("/app/chat", {}, msg);
//     }
//   }
// };

// export default WebSocketService;
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const WebSocketService = {
  stompClient: null,

  connect(onMessageReceived) {
    const socket = new SockJS("http://localhost:8080/ws");
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      this.stompClient.subscribe("/chatroom/public", onMessageReceived);
    });
  },

  sendMessage(msg) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send("/app/message", {}, JSON.stringify(msg));
    }
  }
};

export default WebSocketService;
