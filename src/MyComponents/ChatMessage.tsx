import React, { useEffect, useState, ChangeEvent, MouseEvent } from 'react'
import { over, Client } from 'stompjs'
import SockJS from 'sockjs-client'

let stompClient: Client | null = null

interface ChatMessage {
    senderName: string
    receiverName?: string
    message: string
    status: string
}

interface UserData {
    username: string
    receivername: string
    connected: boolean
    message: string
}

const ChatRoom: React.FC = () => {
    const [privateChats, setPrivateChats] = useState<Map<string, ChatMessage[]>>(new Map())
    const [publicChats, setPublicChats] = useState<ChatMessage[]>([])
    const [tab, setTab] = useState<string>('CHATROOM')
    const [userData, setUserData] = useState<UserData>({
        username: '',
        receivername: '',
        connected: false,
        message: '',
    })

    useEffect(() => {
        console.log(userData)
    }, [userData])

    const connect = () => {
        let Sock = new SockJS('http://localhost:8083/ws')
        stompClient = over(Sock)
        stompClient.connect({}, onConnected, onError)
    }

    const onConnected = () => {
        setUserData({ ...userData, connected: true })
        if (stompClient) {
            stompClient.subscribe('/chatroom/public', onMessageReceived)
            stompClient.subscribe(`/user/${userData.username}/private`, onPrivateMessage)
            userJoin()
        }
    }

    const userJoin = () => {
        const chatMessage: ChatMessage = {
            senderName: userData.username,
            status: 'JOIN',
            message: '',
        }
        if (stompClient) {
            stompClient.send('/app/message', {}, JSON.stringify(chatMessage))
        }
    }

    const onMessageReceived = (payload: any) => {
        const payloadData: ChatMessage = JSON.parse(payload.body)
        switch (payloadData.status) {
            case 'JOIN':
                if (!privateChats.get(payloadData.senderName)) {
                    privateChats.set(payloadData.senderName, [])
                    setPrivateChats(new Map(privateChats))
                }
                break
            case 'MESSAGE':
                publicChats.push(payloadData)
                setPublicChats([...publicChats])
                break
        }
    }

    const onPrivateMessage = (payload: any) => {
        const payloadData: ChatMessage = JSON.parse(payload.body)
        if (privateChats.get(payloadData.senderName)) {
            privateChats.get(payloadData.senderName)?.push(payloadData)
            setPrivateChats(new Map(privateChats))
        } else {
            privateChats.set(payloadData.senderName, [payloadData])
            setPrivateChats(new Map(privateChats))
        }
    }

    const onError = (err: any) => {
        console.log(err)
    }

    const handleMessage = (event: ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target
        setUserData({ ...userData, message: value })
    }

    const sendValue = () => {
        if (stompClient) {
            const chatMessage: ChatMessage = {
                senderName: userData.username,
                message: userData.message,
                status: 'MESSAGE',
            }
            stompClient.send('/app/message', {}, JSON.stringify(chatMessage))
            setUserData({ ...userData, message: '' })
        }
    }

    const sendPrivateValue = () => {
        if (stompClient) {
            const chatMessage: ChatMessage = {
                senderName: userData.username,
                receiverName: tab,
                message: userData.message,
                status: 'MESSAGE',
            }

            if (userData.username !== tab) {
                privateChats.get(tab)?.push(chatMessage)
                setPrivateChats(new Map(privateChats))
            }
            stompClient.send('/app/private-message', {}, JSON.stringify(chatMessage))
            setUserData({ ...userData, message: '' })
        }
    }

    const handleUsername = (event: ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target
        setUserData({ ...userData, username: value })
    }

    const registerUser = () => {
        connect()
    }

    return (
        <div className="container">
            {userData.connected ? (
                <div className="chat-box">
                    <div className="member-list">
                        <ul>
                            <li onClick={() => setTab('CHATROOM')} className={`member ${tab === 'CHATROOM' && 'active'}`}>
                                Chatroom
                            </li>
                            {/* {[...privateChats.keys()].map((name, index) => (
                <li
                  onClick={() => setTab(name)}
                  className={`member ${tab === name && "active"}`}
                  key={index}
                >
                  {name}
                </li>
              ))} */}
                        </ul>
                    </div>
                    {tab === 'CHATROOM' ? (
                        <div className="chat-content">
                            <ul className="chat-messages">
                                {publicChats.map((chat, index) => (
                                    <li className={`message ${chat.senderName === userData.username && 'self'}`} key={index}>
                                        {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                                        <div className="message-data">{chat.message}</div>
                                        {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                                    </li>
                                ))}
                            </ul>

                            <div className="send-message">
                                <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
                                <button type="button" className="send-button" onClick={sendValue}>
                                    send
                                </button>
                            </div>
                        </div>
                    ) : (
                        <div className="chat-content">
                            <ul className="chat-messages">
                                {privateChats.get(tab)?.map((chat, index) => (
                                    <li className={`message ${chat.senderName === userData.username && 'self'}`} key={index}>
                                        {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
                                        <div className="message-data">{chat.message}</div>
                                        {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
                                    </li>
                                ))}
                            </ul>

                            <div className="send-message">
                                <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
                                <button type="button" className="send-button" onClick={sendPrivateValue}>
                                    send
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            ) : (
                <div className="register">
                    <input id="user-name" placeholder="Enter your name" name="userName" value={userData.username} onChange={handleUsername} />
                    <button type="button" onClick={registerUser}>
                        connect
                    </button>
                </div>
            )}
        </div>
    )
}

export default ChatRoom

// import React, { useEffect, useState, ChangeEvent, MouseEvent } from 'react'
// import { over, Client } from 'stompjs'
// import SockJS from 'sockjs-client'

// let stompClient: Client | null = null

// interface ChatMessage {
//     senderName: string
//     receiverName?: string
//     message: string
//     status: string
// }

// interface UserData {
//     username: string
//     receivername: string
//     connected: boolean
//     message: string
// }

// const ChatRoom: React.FC = () => {
//     const [privateChats, setPrivateChats] = useState<Map<string, ChatMessage[]>>(new Map())
//     const [publicChats, setPublicChats] = useState<ChatMessage[]>([])
//     const [tab, setTab] = useState<string>('CHATROOM')
//     const [userData, setUserData] = useState<UserData>({
//         username: '',
//         receivername: '',
//         connected: false,
//         message: '',
//     })

//     useEffect(() => {
//         console.log(userData)
//     }, [userData])

//     const connect = () => {
//         let Sock = new SockJS('http://localhost:8080/ws')
//         stompClient = over(Sock)
//         stompClient.connect({}, onConnected, onError)
//     }

//     const onConnected = () => {
//         setUserData({ ...userData, connected: true })
//         if (stompClient) {
//             stompClient.subscribe('/chatroom/public', onMessageReceived)
//             stompClient.subscribe(`/user/${userData.username}/private`, onPrivateMessage)
//             userJoin()
//         }
//     }

//     const userJoin = () => {
//         const chatMessage: ChatMessage = {
//             senderName: userData.username,
//             status: 'JOIN',
//             message: '',
//         }
//         if (stompClient) {
//             stompClient.send('/app/message', {}, JSON.stringify(chatMessage))
//         }
//     }

//     const onMessageReceived = (payload: any) => {
//         const payloadData: ChatMessage = JSON.parse(payload.body)
//         switch (payloadData.status) {
//             case 'JOIN':
//                 if (!privateChats.get(payloadData.senderName)) {
//                     privateChats.set(payloadData.senderName, [])
//                     setPrivateChats(new Map(privateChats))
//                 }
//                 break
//             case 'MESSAGE':
//                 publicChats.push(payloadData)
//                 setPublicChats([...publicChats])
//                 break
//         }
//     }

//     const onPrivateMessage = (payload: any) => {
//         const payloadData: ChatMessage = JSON.parse(payload.body)
//         if (privateChats.get(payloadData.senderName)) {
//             privateChats.get(payloadData.senderName)?.push(payloadData)
//             setPrivateChats(new Map(privateChats))
//         } else {
//             privateChats.set(payloadData.senderName, [payloadData])
//             setPrivateChats(new Map(privateChats))
//         }
//     }

//     const onError = (err: any) => {
//         console.log(err)
//     }

//     const handleMessage = (event: ChangeEvent<HTMLInputElement>) => {
//         const { value } = event.target
//         setUserData({ ...userData, message: value })
//     }

//     const sendValue = () => {
//         if (stompClient) {
//             const chatMessage: ChatMessage = {
//                 senderName: userData.username,
//                 message: userData.message,
//                 status: 'MESSAGE',
//             }
//             stompClient.send('/app/message', {}, JSON.stringify(chatMessage))
//             setUserData({ ...userData, message: '' })
//         }
//     }

//     const sendPrivateValue = () => {
//         if (stompClient) {
//             const chatMessage: ChatMessage = {
//                 senderName: userData.username,
//                 receiverName: tab,
//                 message: userData.message,
//                 status: 'MESSAGE',
//             }

//             if (userData.username !== tab) {
//                 privateChats.get(tab)?.push(chatMessage)
//                 setPrivateChats(new Map(privateChats))
//             }
//             stompClient.send('/app/private-message', {}, JSON.stringify(chatMessage))
//             setUserData({ ...userData, message: '' })
//         }
//     }

//     const handleUsername = (event: ChangeEvent<HTMLInputElement>) => {
//         const { value } = event.target
//         setUserData({ ...userData, username: value })
//     }

//     const registerUser = () => {
//         connect()
//     }

//     return (
//         <div className="container">
//             {userData.connected ? (
//                 <div className="chat-box">
//                     <div className="member-list">
//                         <ul>
//                             <li onClick={() => setTab('CHATROOM')} className={`member ${tab === 'CHATROOM' && 'active'}`}>
//                                 Chatroom
//                             </li>
//                             {/* {[...privateChats.keys()].map((name, index) => (
//                 <li
//                   onClick={() => setTab(name)}
//                   className={`member ${tab === name && "active"}`}
//                   key={index}
//                 >
//                   {name}
//                 </li>
//               ))} */}
//                         </ul>
//                     </div>
//                     {tab === 'CHATROOM' ? (
//                         <div className="chat-content">
//                             <ul className="chat-messages">
//                                 {publicChats.map((chat, index) => (
//                                     <li className={`message ${chat.senderName === userData.username && 'self'}`} key={index}>
//                                         {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
//                                         <div className="message-data">{chat.message}</div>
//                                         {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
//                                     </li>
//                                 ))}
//                             </ul>

//                             <div className="send-message">
//                                 <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
//                                 <button type="button" className="send-button" onClick={sendValue}>
//                                     send
//                                 </button>
//                             </div>
//                         </div>
//                     ) : (
//                         <div className="chat-content">
//                             <ul className="chat-messages">
//                                 {privateChats.get(tab)?.map((chat, index) => (
//                                     <li className={`message ${chat.senderName === userData.username && 'self'}`} key={index}>
//                                         {chat.senderName !== userData.username && <div className="avatar">{chat.senderName}</div>}
//                                         <div className="message-data">{chat.message}</div>
//                                         {chat.senderName === userData.username && <div className="avatar self">{chat.senderName}</div>}
//                                     </li>
//                                 ))}
//                             </ul>

//                             <div className="send-message">
//                                 <input type="text" className="input-message" placeholder="enter the message" value={userData.message} onChange={handleMessage} />
//                                 <button type="button" className="send-button" onClick={sendPrivateValue}>
//                                     send
//                                 </button>
//                             </div>
//                         </div>
//                     )}
//                 </div>
//             ) : (
//                 <div className="register">
//                     <input id="user-name" placeholder="Enter your name" name="userName" value={userData.username} onChange={handleUsername} />
//                     <button type="button" onClick={registerUser}>
//                         connect
//                     </button>
//                 </div>
//             )}
//         </div>
//     )
// }

// export default ChatRoom
