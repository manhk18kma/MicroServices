import Chat from "../pages/Chat/Chat.index";
import Home from "../pages/Home/Home.index";
import Login from "../pages/Login/Login.index";
import Profile from "../pages/Profile/Profile.index";
import Register from "../pages/Register/register.index";
import Story from "../pages/Story/Story.index";

export const publicRoutes = [
    { path: "/", component: Home, layout: null },
    { path: "/story/:storyId", component: Story, layout: "" },
    { path: "/chat", component: Chat, layout: "Chat" },
    { path: "/profile", component: Profile, layout: "Profile" },
    { path: "/register", component: Register, layout: "" },
    { path: "/login", component: Login, layout: "" },

];
  
export const privateRoute = [];