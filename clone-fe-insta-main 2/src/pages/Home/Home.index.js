import { Carousel } from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import * as React from "react";
import { Button, Dialog, DialogContent, Tooltip } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { MyContext } from "../../components/Layouts/DefaultLayout";
import Post from "./Post";
import Story from "./Story";
import { checkToken } from "../../api/AccountAPI";
import DecodeToken, { decodeToken } from "../../components/DecodeJWT/decode";
import { jwtDecode } from "jwt-decode";
import { getAllPost } from "../../api/PostAPI";

function Home() {
  // check width of the screen
  const { setShareWidth } = React.useContext(MyContext);
  const targetRef = React.useRef(null);
  const checkDivWidth = () => {
    const targetDiv = targetRef.current;
    if (targetDiv) {
      const newWidth = targetDiv.offsetWidth;
      setShareWidth(newWidth);
    }
  };

  React.useEffect(() => {
    // Kiểm tra khi tải trang
    checkDivWidth();

    // Đăng ký sự kiện resize
    window.addEventListener("resize", checkDivWidth);

    // Cleanup: Hủy đăng ký sự kiện khi component bị unmount
    return () => {
      window.removeEventListener("resize", checkDivWidth);
    };
  }, []);

  // get all post
  const [posts, setPosts] = React.useState([])
  React.useEffect(() => {
    getAllPost({token}).then((res) => {
      console.log("cac bai post: ", res.data.items)
      setPosts(res.data.items)
      console.log("token: ", token)
    })
  }, [])

  const token = jwtDecode(localStorage.getItem('token'))
  
  return (
    <div ref={targetRef} class="container col-start-3 col-span-5 mt-4">
      {/* <div class="flex items-center gap-x-3">
        {/* <!-- 1 --> */}
       
        
       {/*</div> */}

      {/* Post */}
      {posts.map(post => {
        return <Post post={post} />
      })}
    </div>
  );
}

export default Home;
