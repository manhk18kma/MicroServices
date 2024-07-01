// public/firebase-messaging-sw.js

importScripts('https://www.gstatic.com/firebasejs/9.6.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.6.0/firebase-messaging-compat.js');

firebase.initializeApp({
  apiKey: "AIzaSyB7AQYerl4_dsut7jmrc-6csbrIAaU2P9Q",
  authDomain: "notification-d53ec.firebaseapp.com",
  projectId: "notification-d53ec",
  storageBucket: "notification-d53ec.appspot.com",
  messagingSenderId: "63255710253",
  appId: "1:63255710253:web:c050842f8c067b3ff846a4",
  measurementId: "G-EPJZCBSS0N"
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log('Nhận thông báo background: ', payload);
  self.registration.showNotification(payload.notification.title, {
    body: payload.notification.body,
    icon: payload.notification.icon,
  });
});
