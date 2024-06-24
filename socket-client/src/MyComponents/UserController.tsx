// import React, { useState, useEffect } from 'react';
// import Stomp from 'stompjs';
// import SockJS from 'sockjs-client';

// const UserController: React.FC = () => {
//     const [fullName, setFullName] = useState<string>('');
//     const [nickName, setNickName] = useState<string>('');
//     const [receivedMessages, setReceivedMessages] = useState<string[]>([]);
//     const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);

//     useEffect(() => {
//         // Connect to WebSocket server when component is mounted
//         const socket = new SockJS('http://localhost:8083/ws');
//         const client = Stomp.over(socket);

//         client.connect({}, () => {
//             console.log('Connected to WebSocket');
//             setStompClient(client);

//             client.subscribe('/chatroom/public', (message) => {
//                 console.log(message)
//                 const newMessage: any = message.body
//                 setReceivedMessages((prevMessages) => [...prevMessages, newMessage]);

//             });
//         });
//         client.subscribe(`/user/manhat18a/private`, (message: any)=>{
//             console.log(message)
//         });

//         return () => {
//             if (stompClient) {
//                 // stompClient.disconnect();
//             }
//         };
//     }, []);

//     const addUser = () => {
//         if (stompClient && fullName.trim() !== '' && nickName.trim() !== '') {
//             // stompClient.send("addUser",
//             //     {},
//             //     JSON.stringify({nickName: nickName, fullName: fullName}) );
//             stompClient.send("/app/message", {},JSON.stringify({nickName: nickName, fullName: fullName}));

//             setFullName('');
//             setNickName('');
//         }
//     };

//     const test = () => {
//         if (stompClient) {
//             stompClient.send('/app/private-message', {}, JSON.stringify(
//                 {
//                     "senderName": "John Doe",
//                     "receiverName": "manhat18a",
//                     "message": "Hello, how are you?"
//                 }

//             ));
//         }
//     };

//     return (
//         // <div>
//         //     <h2>WebSocket Chat</h2>
//         //     <div>
//         //         <input
//         //             type="text"
//         //             value={fullName}
//         //             onChange={(e) => setFullName(e.target.value)}
//         //         />

//         //         <input
//         //             type="text"
//         //             value={nickName}
//         //             onChange={(e) => setNickName(e.target.value)}
//         //         />
//         //         <button onClick={addUser}>Send</button>
//         //     </div>
//         //     <div>
//         //         <h3>Received Messages:</h3>
//         //         <ul>
//         //             {receivedMessages.map((message, index) => (
//         //                 <li key={index}>{message}</li>
//         //             ))}
//         //         </ul>
//         //     </div>
//         // </div>
//         <div>
//             <button onClick={test}>Test</button>
//         </div>
//     );
// };

// export default UserController;

import React, { useState, useEffect } from 'react'
import Stomp from 'stompjs'
import SockJS from 'sockjs-client'

const UserController: React.FC = () => {
    const [fullName, setFullName] = useState<string>('')
    const [nickName, setNickName] = useState<string>('')
    const [receivedMessages, setReceivedMessages] = useState<string[]>([])
    const [stompClient, setStompClient] = useState<Stomp.Client | null>(null)

    const [id, setId] = useState('')
    const [idSender, setIdSender] = useState('1')

    useEffect(() => {
        // Connect to WebSocket server when component is mounted
        const socket = new SockJS('http://localhost:8084/ws')
        const client = Stomp.over(socket)

        client.connect({}, () => {
            console.log('Connected to WebSocket')
            setStompClient(client)

            // Subscribe to receive messages from the server
            client.subscribe('/chatroom/public', (message) => {
                console.log(message)
                const newMessage: any = message.body
                setReceivedMessages((prevMessages) => [...prevMessages, newMessage])
            })

            // client.subscribe(`/user/manhat18a/private`, (message: any) => {
            //     console.log('message recived from ')
            //     const newMessage: any = message.body;

            //     setReceivedMessages((prevMessages) => [...prevMessages, newMessage]);

            //     console.log(message);
            // });

            client.subscribe('/user/' + id + '/private', (message: any) => {
                console.log('message recived from ')
                console.log(message)
                setIdSender(message.body)
                // const newMessage: any = message.body;
            })
        })

        // Disconnect from WebSocket when component is unmounted
        return () => {
            if (stompClient) {
                stompClient.disconnect(() => {
                    console.log('Disconnected from WebSocket')
                })
            }
        }
    }, [id])

    const addUser = () => {
        if (stompClient) {
            // stompClient.send("/app/message", {}, JSON.stringify({ nickName: nickName, fullName: fullName }));
            stompClient.send(
                '/app/message1',
                {},
                JSON.stringify({
                    idProfile: 'cb6da341-e317-4c51-9bb4-822fcc117705',
                }),
            )

            setFullName('')
            setNickName('')
        }
    }

    // const test = () => {
    //     if (stompClient) {
    //         const chatMessage = {
    //             senderName: '1',
    //             receiverName: 'manhat18a',
    //             message: "Test message"
    //         };
    //         stompClient.send('/app/private-message', {}, JSON.stringify(chatMessage));
    //     }
    // };

    return (
        <div>
            <div>
                <h2>{idSender}</h2>
                <input type="text" value={id} onChange={(e) => setId(e.target.value)} />
            </div>
            <h2>WebSocket Chat</h2>
            <div>
                <input type="text" value={fullName} onChange={(e) => setFullName(e.target.value)} />

                <input type="text" value={nickName} onChange={(e) => setNickName(e.target.value)} />
                <button onClick={addUser}>Send</button>
            </div>
            <div>
                <h3>Received Messages:</h3>
                <ul>
                    {receivedMessages.map((message, index) => (
                        <li key={index}>{message}</li>
                    ))}
                </ul>
            </div>
        </div>
    )
}

export default UserController
