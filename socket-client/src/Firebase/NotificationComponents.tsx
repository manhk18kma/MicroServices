import React, { useEffect, useState } from 'react';
import { initializeApp } from 'firebase/app';
import { getAnalytics } from 'firebase/analytics';
import { getMessaging, onMessage } from 'firebase/messaging';
import { firebaseConfig } from './firebase-config'; // Thay thế bằng cấu hình Firebase của bạn

const NotificationComponent: React.FC = () => {
  const [notifications, setNotifications] = useState<{ title: string, body: string }[]>([]);

  useEffect(() => {
    const initializeFirebaseApp = async () => {
      try {
        const app = initializeApp(firebaseConfig);
        getAnalytics(app);
        const messaging = getMessaging(app);

        // Lắng nghe thông báo khi ứng dụng đang hoạt động
        onMessage(messaging, (payload) => {
          console.log('Thông báo nhận được:', payload);
          const { title, body, image } = payload.notification || {};

          // Thêm thông báo mới vào mảng notifications
          setNotifications(prevNotifications => [
            ...prevNotifications,
            { title: title || 'No Title', body: body || 'No Body' }
          ]);

          // Hiển thị thông báo trên giao diện (tùy chỉnh theo yêu cầu của bạn)
          const notificationOptions = {
            body: body || '',
            icon: image || '',
          };
          new Notification(title || 'Notification', notificationOptions);
        });
      } catch (error) {
        console.error('Lỗi khi khởi tạo Firebase và lắng nghe thông báo:', error);
      }
    };

    initializeFirebaseApp();
  }, []);

  return (
    <div className="App">
      <h1>FCM Notifications</h1>
      {notifications.length > 0 && (
        <div>
          <h2>Danh sách thông báo mới</h2>
          {notifications.map((notification, index) => (
            <div key={index}>
              <p><strong>Tiêu đề:</strong> {notification.title}</p>
              <p><strong>Nội dung:</strong> {notification.body}</p>
              <hr />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default NotificationComponent;
