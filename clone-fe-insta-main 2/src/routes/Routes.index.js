import Chat from "../pages/Chat/Chat.index";
import ForgotPassword from "../pages/ForgotPassword/ForgotPassword.index";
import Home from "../pages/Home/Home.index";
import Login from "../pages/Login/Login.index";
import ToPost from "../pages/Post/ToPost.index";
import Profile from "../pages/Profile/Profile.index";
import Register from "../pages/Register/register.index";
import Story from "../pages/Story/Story.index";

export const publicRoutes = [
    { path: "/", component: Home, layout: null },
    { path: "/story/:storyId", component: Story, layout: "" },
    { path: "/chat/:idProfile/:idChat", component: Chat, layout: "Chat" },
    { path: "/profile/:idProfile", component: Profile, layout: "Profile" },
    { path: "/register", component: Register, layout: "" },
    { path: "/login", component: Login, layout: "" },
    { path: "/forgotPassword", component: ForgotPassword, layout: "" },
    { path: "/post/:idPost", component: ToPost, layout: "Profile" },

];
  
export const privateRoute = [];