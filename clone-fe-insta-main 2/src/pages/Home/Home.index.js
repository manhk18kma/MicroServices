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
import InfiniteScroll from "react-infinite-scroll-component";

function Home() {
  // check width of the screen
  const { setShareWidth } = React.useContext(MyContext);
  const targetRef = React.useRef(null);

  const debounce = (func, wait) => {
    let timeout;
    return (...args) => {
      clearTimeout(timeout);
      timeout = setTimeout(() => func.apply(this, args), wait);
    };
  };

  const checkDivWidth = () => {
    const targetDiv = targetRef.current;
    if (targetDiv) {
      const newWidth = targetDiv.offsetWidth;
      setShareWidth(newWidth);
      console.log("day la new width", newWidth);
      if (newWidth < 540) {
        if (!targetDiv.classList.contains("col-span-7")) {
          targetDiv.classList.remove("col-span-5");
          targetDiv.classList.add("col-span-7");
        }
      } else {
        if (!targetDiv.classList.contains("col-span-5")) {
          targetDiv.classList.remove("col-span-7");
          targetDiv.classList.add("col-span-5");
        }
      }
    }
  };

  React.useEffect(() => {
    // Kiểm tra khi tải trang
    checkDivWidth();

    // Sử dụng debounce cho sự kiện resize
    const handleResize = debounce(checkDivWidth, 50);

    // Đăng ký sự kiện resize
    window.addEventListener("resize", handleResize);

    // Cleanup: Hủy đăng ký sự kiện khi component bị unmount
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  // get all post
  const [posts, setPosts] = React.useState([]);
  const token = localStorage.getItem("token");

  // React.useEffect(() => {
  //   getAllPost({ token }).then((res) => {
  //     console.log("cac bai post: ", res.data.items);
  //     setPosts(res.data.items);
  //     console.log("token: ", token);
  //   });
  // }, []);

  const [hasMore, setHasMore] = React.useState(true);
  const [pageNo, setPageNo] = React.useState(0);
  const fetchMoreData = () => {
    getAllPost({ token: token, pageNo: pageNo })
      .then((res) => {
        console.log("cac bai post: ", res.data.items);
        console.log("token: ", token);
        if (res.data.items.length === 0) {
          setHasMore(false);
        } else {
          setTimeout(() => {
            setPosts((prevPosts) => [...prevPosts, ...res.data.items]);
            setPageNo((prevPageNo) => prevPageNo + 1);
          }, 2000);
        }
      })
      .catch((error) => {
        console.error("Error fetching posts:", error);
      });
  };

  React.useEffect(() => {
    fetchMoreData(); // Lấy dữ liệu ban đầu
  }, []);

  return (
    <div ref={targetRef} className="container col-start-3 col-span-5 mt-4">
      <div className="min-w-28">
        <InfiniteScroll
          dataLength={posts.length}
          next={fetchMoreData}
          hasMore={hasMore}
          loader={
            <div className="text-center">
              <span className="text-gray-400">Loading ...</span>
            </div>
          }
        >
          {/* Post */}
          {console.log("day la post:", posts)}
          {posts.map((post) => {
            return <Post key={post.id} post={post} />;
          })}
        </InfiniteScroll>
      </div>
    </div>
  );
}

export default Home;
