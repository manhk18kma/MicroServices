// import React, { useState, useEffect } from 'react'
// import Stomp, { Client, Frame } from 'stompjs'
// import SockJS from 'sockjs-client'
// // 122
// const Mess: React.FC = () => {
//     const [stompClient, setStompClient] = useState<Client | null>(null)
//         // const [idDefault, setIdDefault] = useState('0a6d4e12-fb79-46ba-ac6e-d42150b17fd0')

//     const [idDefault, setIdDefault] = useState('0a6d4e12-fb79-46ba-ac6e-d42150b17fd0')
//     useEffect(() => {
//         const socket = new SockJS('http://localhost:8083/ws')
//         const client = Stomp.over(socket)

//         const connectClient = () => {
//             client.connect(
//                 {},
//                 () => {
//                     console.log('Connected to WebSocket')
//                     setStompClient(client)
//                     sendConnectRequest()
//                     subscribeToGeneralTopic('/topic/messages')
//                     subscribeToUserTopic('46d5a927-d848-4c21-861f-9a1aa43cde41')

//                 },
//                 (error) => {
//                     console.error('Error connecting to WebSocket:', error)
//                     setTimeout(connectClient, 500)
//                 },
//             )
//         }

//         connectClient()

//         return () => {
//             if (stompClient) {
//                 sendDisconnectRequest()
//                 stompClient.disconnect(() => {
//                     console.log('Disconnected from WebSocket')
//                 })
//             }
//         }
//     }, [])
//     // 1234522332223
//     const sendConnectRequest = () => {
//         if (stompClient && stompClient.connected) {
//             const message = {
//                 idProfile: idDefault,
//             }
//             stompClient.send('/app/connections', {}, JSON.stringify(message))
//         }
//     }
//     // 1
//     const sendDisconnectRequest = () => { 
//         if (stompClient && stompClient.connected) {
//             const message = {
//                 idProfile: idDefault,
//             }
//             stompClient.send('/app/disconnections', {}, JSON.stringify(message))
//         }
//     }
//     const subscribeToUserTopic = (topic: string) => {
//         if (stompClient && stompClient.connected) {
//             stompClient.subscribe(`/user/${topic}/private`, (message) => {
//                 console.log('Received private message:', message.body);
//             });
//         }
//     };
    
//     const subscribeToGeneralTopic = (topic: string) => {
//         if (stompClient && stompClient.connected) {
//             stompClient.subscribe(topic, (message: Frame) => {
//                 console.log('Received general message:', message.body)
//             })
//         }
//     };
    

//     // 112
//     return (
//         <div>
//             <h1>WebSocket Example</h1>
//             {/* Your UI elements */}
//         </div>
//     )
// }

// export default Mess


import React, { useState, useEffect } from 'react';
import Stomp, { Client, Frame } from 'stompjs';
import SockJS from 'sockjs-client';
import { getAllMessages } from '../API/Messenger/Messenger';
import { ChatMessage } from '../MessagingService/Util/InterfaceCustom';
interface MessInterface {
    idChat: string
    setIdChat: any
}

const Mess: React.FC<MessInterface> = ({idChat , setIdChat}) => {
    const [stompClient, setStompClient] = useState<Client | null>(null);
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [newMessage, setNewMessage] = useState('');
    const [idDefault, setIdDefault] = useState('')


    useEffect(() => {
        const socket = new SockJS('http://localhost:8083/ws');
        const client = Stomp.over(socket);

        const connectClient = () => {
            client.connect(
                {},
                () => {
                    console.log('Connected to WebSocket :D');
                    setStompClient(client);
                    subscribeToUserTopic(idChat);
                },
                (error) => {
                    console.error('Error connecting to WebSocket:', error);
                    setTimeout(connectClient, 500);
                },
            );
        };

        connectClient();

        return () => {
            if (stompClient) {
                stompClient.disconnect(() => {
                    console.log('Disconnected from WebSocket');
                });
            }
        };
    }, [idChat]);
    useEffect(() => {
        const getData = async () => {
            console.log('re-render')
            if (idDefault.trim().length>0) {
                try {
                    const messages = await getAllMessages({
                        idChat: idChat,
                        pageNo: 0,
                        pageSize: 20,
                    })
                    setMessages((prevMessages) => [...messages.data.items as ChatMessage[], ...prevMessages]);
                } catch (error) {
                    console.error(error)
                }
            }
        }
        getData()
    }, [idDefault]);

    const sendMessage = () => {
        if (stompClient && stompClient.connected) {
            // existed chat
            const message = {
                idChat: idChat,
                content: newMessage,
                idChatProfileSender : idDefault,
                idChatProfileReceiver : null
            };
                        //not existed chat

            // const message = {
            //     idChat: null,
            //     content: newMessage,
            //     idChatProfileSender : '3dafe79f-387f-47c9-81c6-92a3812d15f4',
            //     idChatProfileReceiver : 'd49f4403-c886-4475-b735-41eaef854ce7'
            // };
            stompClient.send('/app/messages', {}, JSON.stringify(message));
            setNewMessage('');
        }
    };

    const subscribeToUserTopic = (topic: string) => {
        if (stompClient && stompClient.connected) {
            stompClient.subscribe(`/user/${topic}/private`, (message) => {
            console.log('Received private message:', message.body);
                const receivedMessage = JSON.parse(message.body);
                        setMessages((prevMessages) => [receivedMessage  as ChatMessage, ...prevMessages]);
            });
        }
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNewMessage(event.target.value);
    };

    const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            sendMessage();
        }
    };
// 
    return (
        <div>
            <h1>WebSocket Chat</h1>
            <input onChange={(event) => setIdDefault(event.target.value)} placeholder="Enter ID" />
            <input onChange={(event) => setIdChat(event.target.value)} placeholder="Enter ID Chat" />

            <div>
                <input
                    type="text"
                    value={newMessage}
                    onChange={handleInputChange}
                    onKeyPress={handleKeyPress}
                    placeholder="Type a message and press Enter"
                />
                <button onClick={sendMessage}>Send</button>
            </div>
            <div>
            {messages.map((message, index) => (
                <div 
                    key={index} 
                    style={{ 
                        display: 'flex', 
                        justifyContent: message.idChatProfileSender === idDefault ? 'flex-end' : 'flex-start',
                        margin: '10px 0'
                    }}
                >
                    <div 
                        style={{ 
                            backgroundColor: message.idChatProfileSender === idDefault ? '#DCF8C6' : '#FFFFFF', 
                            borderRadius: '10px', 
                            padding: '10px', 
                            maxWidth: '60%',
                            boxShadow: '0 1px 3px rgba(0,0,0,0.1)'
                        }}
                    >
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                            <img 
                                src={message.urlAvtSender} 
                                alt={message.fullNameSender} 
                                style={{ 
                                    width: '30px', 
                                    height: '30px', 
                                    borderRadius: '50%', 
                                    marginRight: '10px' 
                                }} 
                            />
                            <strong>{message.fullNameSender}</strong>
                        </div>
                        <div>{message.content}</div>
                        <div style={{ fontSize: '0.8em', color: '#888' }}>
                            {new Date(message.timeStamp).toLocaleString()}
                        </div>
                    </div>
                </div>
            ))}
        </div>
        </div>
    );
};

export default Mess;
