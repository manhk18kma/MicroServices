import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getMessaging, getToken } from "firebase/messaging";

// Your web app's Firebase configuration
export const firebaseConfig = {
  apiKey: "AIzaSyB7AQYerl4_dsut7jmrc-6csbrIAaU2P9Q",
  authDomain: "notification-d53ec.firebaseapp.com",
  projectId: "notification-d53ec",
  storageBucket: "notification-d53ec.appspot.com",
  messagingSenderId: "63255710253",
  appId: "1:63255710253:web:c050842f8c067b3ff846a4",
  measurementId: "G-EPJZCBSS0N"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const analytics = getAnalytics(app);
export const messaging = getMessaging(app);