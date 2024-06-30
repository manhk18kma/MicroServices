import { Link } from "react-router-dom";
import ChatItem from "./ChatItem";
import * as React from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { getAllMessages } from "../../api/ChatAPI";

export function GroupChat({ idDefault, idChat }) {
  // check height of the screen
  const [checkHeight, setCheckHeight] = React.useState(window.innerHeight);
  const checkDivHeight = () => {
    const newHeight = window.innerHeight;
    setCheckHeight(newHeight);
    const divChatDetail = document.getElementById("divChatDetail");
    divChatDetail.style.height = newHeight - 150 + "px";
    console.log(divChatDetail.offsetHeight);
  };

  React.useEffect(() => {
    // Kiểm tra khi tải trang
    checkDivHeight();

    // Đăng ký sự kiện resize
    window.addEventListener("resize", checkDivHeight);

    // Cleanup: Hủy đăng ký sự kiện khi component bị unmount
    return () => {
      window.removeEventListener("resize", checkDivHeight);
    };
  }, [checkHeight]); // Phải có sharedWidth ở đây để useEffect lắng nghe sự thay đổi của nó

  // message
  const [stompClient, setStompClient] = React.useState(null);
  const [messages, setMessages] = React.useState([]);
  const [newMessage, setNewMessage] = React.useState("");
  React.useEffect(() => {
    const socket = new SockJS("http://localhost:8083/ws");
    const client = Stomp.over(socket);

    const connectClient = () => {
      client.connect(
        {},
        () => {
          console.log("Connected to WebSocket :D");
          setStompClient(client);
          // if(idChat){
          //   console.log('idChat exists')
          //   subscribeToUserTopic(idChat);
          // }
        },
        (error) => {
          console.error("Error connecting to WebSocket:", error);
          setTimeout(connectClient, 500);
        }
      );
    };

    connectClient();

    return () => {
      if (stompClient) {
        stompClient.disconnect(() => {
          console.log("Disconnected from WebSocket");
        });
      }
    };
  }, [idChat]);
  
  

  React.useEffect(() => {
    const getData = async () => {
      console.log("re-render");
      if (idDefault.trim().length > 0) {
        try {
          const messages = await getAllMessages({
            idChat: idChat,
            pageNo: 0,
            pageSize: 20,
          });
          console.log(messages)

          setMessages((prevMessages) => [
            ...messages.data.data.items,
            ...prevMessages,
          ]);
        } catch (error) {
          console.error(error);
        }
      }
    };
    getData();
  }, []);

  React.useEffect(() => {
    if(idChat){
      console.log('idChat exists-------stompClient')
      subscribeToUserTopic(idChat);
    }
  }, [stompClient]);

  const sendMessage = () => {
    if (stompClient && stompClient.connected) {
      // existed chat
      const message = {
        idChat: idChat,
        content: newMessage,
        idChatProfileSender: idDefault,
        idChatProfileReceiver: null,
      };
      //not existed chat
      // const message = {
      //     idChat: null,
      //     content: newMessage,
      //     idChatProfileSender : '3dafe79f-387f-47c9-81c6-92a3812d15f4',
      //     idChatProfileReceiver : 'd49f4403-c886-4475-b735-41eaef854ce7'
      // };
      stompClient.send("/app/messages", {}, JSON.stringify(message));
      setNewMessage("");
    }
  };

  const subscribeToUserTopic = (topic) => {
    console.log("-------------")
    console.log(stompClient)
    if (stompClient && stompClient.connected) {
      stompClient.subscribe(`/user/${topic}/private`, (message) => {
        console.log("Subscribe")
        const receivedMessage = JSON.parse(message.body);
        console.log("Received private message:", message);
        console.log("Received private message:", receivedMessage);
        console.log("Received private message:", receivedMessage.content);

        setMessages((prevMessages) => [ ...prevMessages,receivedMessage]);


        // setMessages((prevMessages) => [receivedMessage, ...prevMessages]);
      });
    }
  };



  




  const handleKeyPress = (event) => {
    if (event.key === "Enter") {
      sendMessage();
    }
  };

  return (
    <div className={`col-start-5 col-span-8 h-[${checkHeight}px]`}>
      <div className="flex items-center gap-4 p-4 border-b">
        <div className="cursor-pointer">
          <img
            className="w-[44px] h-[44px] rounded-[50%] object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          />
        </div>
        <div className="flex flex-col cursor-pointer">
          <span className="text-[16px] font-medium">The</span>
          <span className="text-[12px] text-[#737373]">Active 1m ago</span>
        </div>
        <div className="ml-auto">
          <svg
            aria-label="Conversation information"
            class="x1lliihq x1n2onr6 x5n08af"
            fill="currentColor"
            height="24"
            role="img"
            viewBox="0 0 24 24"
            width="24"
          >
            <title>Conversation information</title>
            <circle
              cx="12.001"
              cy="12.005"
              fill="none"
              r="10.5"
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
            ></circle>
            <circle cx="11.819" cy="7.709" r="1.25"></circle>
            <line
              fill="none"
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              x1="10.569"
              x2="13.432"
              y1="16.777"
              y2="16.777"
            ></line>
            <polyline
              fill="none"
              points="10.569 11.05 12 11.05 12 16.777"
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
            ></polyline>
          </svg>
        </div>
      </div>

      <div id="divChatDetail" className="px-4 pt-10 pb-6 overflow-auto">
        <div className="flex flex-col justify-center items-center mb-14">
          <img
            className="w-[96px] h-[96px] rounded-[50%] object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          />
          <span className="text-[20px] font-medium mt-3">The</span>
          <div className="flex items-center gap-4">
            <span className="text-[#737373] text-[14px] relative after:block after:content-[''] after:w-[3px] after:h-[3px] after:bg-[#737373] after:rounded-[50%] after:absolute after:right-[-10px] after:top-[49%] after:cursor-default">
              _Pbat
            </span>
            <span className="text-[#737373] text-[14px]">Instagram</span>
          </div>
          <Link to="/profile" className="mt-4 cursor-pointer ">
            <span className="block py-1 px-2 rounded-[6px] bg-[#efefef] hover:bg-[#dbdbdb]">
              View Profile
            </span>
          </Link>
        </div>

        <div className="text-center">
          <span className="text-[12px] text-[#656770] font-medium">12:08</span>
        </div>
        <div className="mb-6">
          {/* 1 */}
          {messages.map((message, index) => (
            // console.log(message)
            <ChatItem
              key={index}
              left={message.idChatProfileSender !== idDefault}
              content={message.content}
            />
          ))}
        </div>
      </div>
      <div className="relative mt-auto">
        <input
          onKeyDown={handleKeyPress}
          onChange={(e) => setNewMessage(e.target.value)}
          type="text"
          placeholder="Message..."
          className="w-full border-[1px] px-4 py-2 rounded-[999px] outline-none"
        />
        <span
          onClick={sendMessage}
          className="absolute right-6 top-2 cursor-pointer text-[14px] text-[#0097f7] font-medium"
        >
          Send
        </span>
      </div>
    </div>
  );
}

export default GroupChat;
