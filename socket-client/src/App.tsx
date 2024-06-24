import React, { useEffect, useState } from 'react';
import { initializeApp } from 'firebase/app';
import { getAnalytics } from 'firebase/analytics';
import { getMessaging, getToken } from 'firebase/messaging';
import { firebaseConfig } from './Firebase/firebase-config';
import NotificationComponent from './Firebase/NotificationComponents';
import Mess from './MyComponents/Mess';

function App() {
  const [idChat , setIdChat] = useState('');

  return (
    <div className="App">
      <Mess idChat={idChat} setIdChat={setIdChat} />
    </div>
  );
}

export default App;













// import React, { useEffect, useState } from 'react';
// import { initializeApp } from 'firebase/app';
// import { getAnalytics } from 'firebase/analytics';
// import { getMessaging, getToken, onMessage } from 'firebase/messaging';
// import { firebaseConfig } from './Firebase/firebase-config';

// interface Notification {
//   title: string;
//   body: string;
// }

// const App: React.FC = () => {
//   const [notifications, setNotifications] = useState<Notification[]>([]);
//   const [currentToken, setCurrentToken] = useState<string | null>(null);

//   useEffect(() => {
//     const initializeFirebaseApp = async () => {
//       try {
//         const app = initializeApp(firebaseConfig);
//         getAnalytics(app);
//         const messaging = getMessaging(app);

//         // Lấy token của thiết bị
//         //public key
//         const token = await getToken(messaging, {
//           vapidKey: 'BNS3T9KrSq2f8xi8XQDkmwvQ1rLkPRkgwwhYQKaXnCgvpz5fHKYAemxu96gtiv6In6ZdKW6qv0fGDKNvlihO_vM' // Thay thế bằng khóa VAPID của bạn
//         });

//         if (token) {
//           console.log('Token thiết bị:', token);
//           setCurrentToken(token);
//         } else {
//           console.log('Không thể lấy được token.');
//         }

//         // Lắng nghe các thông báo đến khi ứng dụng đang mở
//         onMessage(messaging, (payload) => {
//           console.log('Thông báo đến khi ứng dụng đang mở:', payload);
//           if (payload.notification) {
//             setNotifications((prevNotifications) => [
//               ...prevNotifications,
//               {
//                 title: payload.notification?.title || 'Không có tiêu đề',
//                 body: payload.notification?.body || 'Không có nội dung',
//               },
//             ]);
//           }
//         });

//         console.log('Đã khởi tạo Firebase và lắng nghe thông báo.');
//       } catch (error) {
//         console.error('Lỗi khi khởi tạo Firebase và lắng nghe thông báo:', error);
//       }
//     };

//     initializeFirebaseApp();
//   }, []);

//   return (
//     <div className="App">
//       <h1>Ứng dụng React Firebase Messaging</h1>
//       <p>Token thiết bị: {currentToken}</p>
//       <ul>
//         {notifications.map((notification, index) => (
//           <li key={index}>
//             <strong>{notification.title}</strong>: {notification.body}
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default App;
