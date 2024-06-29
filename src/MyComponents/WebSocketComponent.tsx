import React, { useEffect, useState } from 'react'
import Stomp from 'stompjs'
import SockJS from 'sockjs-client'

const WebSocketComponent: React.FC = () => {
    const [messages, setMessages] = useState<string[]>([])

    useEffect(() => {
        // Khởi tạo kết nối WebSocket
        const socket = new SockJS('http://localhost:8081/ws')
        const stompClient = Stomp.over(socket)
        // stompClient.debug = null;

        // Kết nối với server
        stompClient.connect({}, () => {
            // Đăng ký để nhận tin nhắn từ chủ đề '/topic/notification/1'
            stompClient.subscribe('/topic/notification/1', (message) => {
                // Xử lý tin nhắn được nhận
                // const newMessage = JSON.parse(message.body);
                const newMessage = message.body

                // setMessages((prevMessages) => [...prevMessages, newMessage.content]);
                setMessages((prevMessages) => [...prevMessages, newMessage])
            })
        })

        // Ngắt kết nối khi component unmount
        return () => {
            // stompClient.disconnect();
        }
    }, [])

    return (
        <div>
            <h1>Message Display</h1>
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>{message}</li>
                ))}
            </ul>
        </div>
    )
}

export default WebSocketComponent
