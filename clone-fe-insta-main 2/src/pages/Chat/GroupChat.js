import { Link } from "react-router-dom";
import ChatItem from "./ChatItem";
import * as React from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { getAllMessages } from "../../api/ChatAPI";
import { jwtDecode } from "jwt-decode";
import { MyContextMessage } from "../../components/Layouts/MessageLayout";

export function GroupChat({
  idDefault,
  tempIdChat,
  tempFriend,
  tempIdChatProfileParams,
}) {
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
  const [isSending, setIsSending] = React.useState(false); // Track message sending state

  React.useEffect(() => {
    const socket = new SockJS("http://localhost:8083/ws");
    const client = Stomp.over(socket);

    if (stompClient) {
      stompClient.disconnect(() => {
        console.log("Disconnected from WebSocket");
      });
    }

    console.log("sub lai", tempIdChat);
    setIsSubscribed(false);

    const connectClient = () => {
      client.connect(
        {},
        () => {
          console.log("Connected to WebSocket :D", tempIdChat);
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
    setMessages([]);

    return () => {
      if (stompClient) {
        stompClient.disconnect(() => {
          console.log("Disconnected from WebSocket");
        });
      }
    };
  }, [tempIdChat]);

  React.useEffect(() => {
    if (tempIdChat !== null) {
      const getData = async () => {
        console.log("re-render");
        if (idDefault.trim().length > 0) {
          try {
            const messages = await getAllMessages({
              idChat: tempIdChat,
              pageNo: 0,
              pageSize: 20,
              token: token,
            });
            console.log(messages);

            setMessages(messages.data.data.items.reverse());
          } catch (error) {
            console.error(error);
          }
        }
      };
      getData();
    }
  }, [tempIdChat]);

  const { setIsNewMessage } = React.useContext(MyContextMessage);

  React.useEffect(() => {
    if (tempIdChat) {
      console.log("idChat exists-------stompClient");
      subscribeToUserTopicListChat(tokenDetail.idChatProfile);
      if (isSubscribed === false) {
        subscribeToUserTopic(tempIdChat);
      }
    }
  }, [stompClient]);

  const sendMessage = async () => {
    if (isSending || !newMessage) return;
    setIsSending(true);
    console.log("id la null", tempIdChat);
    console.log("id profile receiver", tempIdChatProfileParams);
    if (stompClient && stompClient.connected) {
      // existed chat
      const message = {
        idChat: tempIdChat,
        content: newMessage,
        idChatProfileSender: idDefault,
        idChatProfileReceiver: tempIdChatProfileParams,
      };
      console.log("000000", message);

      try {
        await stompClient.send("/app/messages", {}, JSON.stringify(message));
        setIsNewMessage(message); // Update context for new message indicator
        setNewMessage("");
      } catch (error) {
        console.error("Error sending message:", error);
      } finally {
        setIsSending(false); // Reset sending flag after sending or error
      }

      // stompClient.send("/app/messages", {}, JSON.stringify(message));
      // console.log("tin nhan vua gui", message);
      // setIsNewMessage(message);
      // setNewMessage("");
    }
  };

  const [isSubscribed, setIsSubscribed] = React.useState(false);
  const subscribeToUserTopic = (topic) => {
    if (isSubscribed) {
      console.log("Already subscribed to ", topic);
    } else {
      console.log("subscribe ---- ", topic);
      if (stompClient && stompClient.connected) {
        stompClient.subscribe(`/user/${topic}/private`, (message) => {
          const receivedMessage = JSON.parse(message.body);
          console.log("tin nhan nhan duoc", receivedMessage);
          setMessages((prevMessages) => [...prevMessages, receivedMessage]);

          // setMessages((prevMessages) => {
          //   // Check if the message already exists in the array

          //   const messageExists = prevMessages.some(
          //     (msg) => msg.idChatMessage === receivedMessage.idChatMessage
          //   );
          //   console.log("receive mes", receivedMessage);
          //   // If it doesn't exist, add the new message
          //   if (!messageExists) {
          //     return [...prevMessages, receivedMessage];
          //   }

          //   // If it exists, return the previous messages array unchanged
          //   return prevMessages;
          // });

          // setIsNewMessage(receivedMessage);
          // setMessages((prevMessages) => [receivedMessage, ...prevMessages]);
        });
        setIsSubscribed(true);
      }
    }
  };

  const subscribeToUserTopicListChat = (topic) => {
    if (stompClient && stompClient.connected) {
      stompClient.subscribe(`/user/${topic}/chats`, (message) => {
        console.log("subscribe ---- ", topic);

        setIsNewMessage(message);
      });
    }
  };

  // const handleKeyPress = (event) => {
  //   if (event.key === "Enter") {
  //     sendMessage();
  //   }
  // };

  // get token
  const token = localStorage.getItem("token");
  const tokenDetail = jwtDecode(token);
  console.log("day la id chat thay doi khi click friend khac", tempIdChat);


  const messageEndRef = React.useRef(null)

  React.useEffect(() => {
    messageEndRef.current?.scrollIntoView()
  }, [messages])

  return (
    <div className={`col-start-5 col-span-8 h-[${checkHeight}px]`}>
      <div className="flex items-center gap-4 p-4 border-b">
        <div className="cursor-pointer">
          <img
            className="w-[44px] h-[44px] rounded-[50%] object-cover"
            src={tempFriend ? tempFriend.urlProfilePicture : ""}
          />
        </div>
        <div className="flex flex-col cursor-pointer">
          <span className="text-[16px] font-medium">
            {tempFriend ? tempFriend.fullName : ""}
          </span>
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
            src={tempFriend ? tempFriend.urlProfilePicture : ""}
          />
          <span className="text-[20px] font-medium mt-3">
            {tempFriend ? tempFriend.fullName : ""}
          </span>
          <div className="flex items-center gap-4">
            <span className="text-[#737373] text-[14px]">Instagram</span>
          </div>
          <Link
            to={`/profile/${tempFriend ? tempFriend.idProfile : ""}`}
            className="mt-4 cursor-pointer "
          >
            <span className="block py-1 px-2 rounded-[6px] bg-[#efefef] hover:bg-[#dbdbdb]">
              View Profile
            </span>
          </Link>
        </div>

        {/* <div className="text-center">
          <span className="text-[12px] text-[#656770] font-medium">12:08</span>
        </div> */}
        <div className="mb-6">
          {/* 1 */}
          {console.log("messages: ", messages)}
          {messages.map((message, index) => {
            return (
              <ChatItem
                key={index}
                left={message.idChatProfileSender !== idDefault}
                messageItem={message}
              />
            );
          })}
          <div ref={messageEndRef}/>
        </div>
      </div>
      <div className="relative mt-auto">
        <input
          // onKeyDown={handleKeyPress}
          value={newMessage}
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
