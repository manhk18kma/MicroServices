import {
  Alert,
  Button,
  Dialog,
  DialogContent,
  Snackbar,
  Tooltip,
  styled,
} from "@mui/material";
import { Carousel } from "react-responsive-carousel";
import * as React from "react";
import Thumb from "./Thumb";
import { Link } from "react-router-dom";
import CommentDetail from "./CommentDetail";
import {
  countLike,
  createFollow,
  createNewComment,
  deletePost,
  getAllComment,
  likePost,
  unFollow,
  unFriend,
  unlikePost,
  updatePost,
} from "../../api/PostAPI";
import { formatDistanceToNow } from "date-fns";
import { jwtDecode } from "jwt-decode";

// Custom tooltip
export const CustomTooltip = styled(({ className, ...props }) => (
  <Tooltip {...props} classes={{ popper: className }} />
))({
  [`& .MuiTooltip-tooltip`]: {
    padding: 0, // Bỏ padding mặc định
    borderRadius: "8px", // Bo góc
  },
});

function Post({ post }) {
  // dialog more option
  const [openMoreOption, setOpenMoreOption] = React.useState(false);

  const handleClickOpenMoreOption = () => {
    setOpenMoreOption(true);
  };

  const handleCloseMoreOption = () => {
    setOpenMoreOption(false);
  };

  // dialog unfollow
  const [openUnfollow, setOpenUnfollow] = React.useState(false);
  const handleClickOpenUnfollow = () => {
    handleCloseMoreOption(true)
    setOpenUnfollow(true);
    isFriend
      ? unFriend({
          idProfileTarget: post.idProfile,
          token: token,
          tokenDetail: tokenDetail,
        }).then((res) => {
          console.log("return sau khi unfriend: ", res);
        })
      : unFollow({
          idProfileTarget: post.idProfile,
          token: token,
          tokenDetail: tokenDetail,
        }).then((res) => {
          console.log("return sau khi unfollow: ", res);
        });
  };

  const handleCloseUnfollow = () => {
    setOpenUnfollow(false);
  };

  // Follow
  const [followed, setFollowed] = React.useState(
    post.relationshipType === "FOLLOWING" ||
      post.relationshipType === "FRIEND" ||
      post.relationshipType === "YOU"
      ? true
      : false
  );
  const [isFriend, setIsFriend] = React.useState(
    post.relationshipType === "FRIEND" ? true : false
  );
  const toggleFollow = () => {
    if (followed) {
      handleClickOpenUnfollow();
    } else {
      setFollowed(true);
      createFollow({
        idProfileTarget: post.idProfile,
        token: token,
        tokenDetail: tokenDetail,
      }).then((res) => {
        console.log("return sau khi follow: ", res);
      });
    }
  };
  const handleUnfollow = () => {
    setFollowed(false);
    handleCloseUnfollow();
  };

  // Comment Dialog
  const [openComment, setOpenComment] = React.useState(false);
  const [listComment, setListComment] = React.useState([]);

  const handleClickOpenComment = () => {
    setOpenComment(true);
    getAllComment({ idPost: post.idPost, token: token }).then((res) => {
      console.log("danh sach comment: ", res);
      setListComment(res.data.items);
    });
  };

  const handleCloseComment = () => {
    setOpenComment(false);
  };

  // like
  const [liked, setLiked] = React.useState(post.liked);
  const [currentLike, setCurrentLike] = React.useState(0);
  const toggleLike = () => {
    setLiked(!liked);
    setCurrentLike(liked ? currentLike - 1 : currentLike + 1);
    if (liked) {
      unlikePost({ idPost: post.idPost, token: token }).then((res) => {
        console.log("unlike success");
      });
    } else {
      likePost({ idPost: post.idPost, token: token }).then((res) => {
        console.log("unlike success");
      });
    }
  };

  React.useEffect(() => {
    countLike({ idPost: post.idPost, token: token }).then((res) => {
      console.log("tong so like: ", res.data.count);
      setCurrentLike(res.data.count);
    });
  }, []);

  // post
  const [isPost, setIsPost] = React.useState(false);
  const [inputContent, setInputContent] = React.useState("");

  const handleInputChange = (e) => {
    setInputContent(e.target.value);
  };

  const handlePost = () => {
    createNewComment({
      idPost: post.idPost,
      content: inputContent,
      token: token,
    }).then((res) => {
      console.log("return sau khi comment: ", res);
      console.log("list comment: ", listComment);
      setListComment([...listComment, res.data]);
    });
    setInputContent("");
  };

  // format date
  const notificationDate = new Date(post.updateAt); // Thay 'timestamp' bằng tên thuộc tính thực tế của bạn
  const timeAgo = formatDistanceToNow(notificationDate, { addSuffix: true });

  //token
  const token = localStorage.getItem("token");
  const tokenDetail = jwtDecode(token);

  // show edit post dialog
  const [openEditPost, setOpenEditPost] = React.useState(false);

  const handleClickOpenEditPost = () => {
    setOpenEditPost(true);
  };

  const handleCloseEditPost = () => {
    setOpenEditPost(false);
  };

  // show delete dialog
  const [openDeletePost, setOpenDeletePost] = React.useState(false);

  const handleClickOpenDeletePost = () => {
    setOpenEditPost(false);
    setOpenDeletePost(true);
  };

  const handleCloseDeletePost = () => {
    setOpenDeletePost(false);
  };

  // poopup
  const [popupStatus, setPopupStatus] = React.useState("");
  const [popupContent, setPopupContent] = React.useState("");
  const [openPopup, setOpenPopup] = React.useState(false);
  const handleClickPopup = () => {
    setOpenPopup(true);
  };

  const handleClosePopup = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setOpenPopup(false);
  };

  // delete post api
  const handleDeletePostAPI = () => {
    deletePost({ token: token, idPost: post.idPost }).then((res) => {
      setPopupStatus("success");
      setPopupContent("Post deleted successfully");
      handleClickPopup();

      setTimeout(() => {
        window.location.reload();
      }, 2000);
    }).catch((error) => {
      setPopupStatus("warning");
      setPopupContent("Failed to delete post");
      handleClickPopup();
    })
  };

  // show edit caption dialog
  const [openEditCaption, setOpenEditCaption] = React.useState(false);
  const [caption, setCaption] = React.useState(post.caption);

  const handleClickOpenEditCaption = () => {
    setOpenEditPost(false);
    setOpenEditCaption(true);
  };

  const handleCloseEditCaption = () => {
    setOpenEditCaption(false);
  };

  const handleUpdatePostAPI = () => {
    updatePost({
      token: token,
      idProfile: tokenDetail.sub,
      idPost: post.idPost,
      caption: caption,
      base64OrUrl: post.images,
    })
      .then((res) => {
        setPopupStatus("success");
        setPopupContent("Post updated successfully");
        handleClickPopup();
        setTimeout(() => {
          window.location.reload();
        }, 2000);
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("Failed to update post");
        handleClickPopup();
      });
  };

  return (
    <div class="mt-12 px-[80px]">
      <div class="flex items-center mb-[12px]">
        <div class="w-[42px] h-[42px] flex items-center justify-center mr-2 cursor-pointer">
          <div>
            <img
              class="w-[32px] h-[32px] rounded-[50%] object-cover"
              src={post.urlAvt}
              alt="asd"
            />
          </div>
        </div>

        <Link
          to={`/profile/${post.idProfile}`}
          class="relative text-[14px] font-medium mr-4 after:block after:content-[''] after:w-[4px] after:h-[4px] after:bg-slate-500 after:rounded-[50%] after:absolute after:right-[-10px] after:top-[50%] after:cursor-default cursor-pointer"
        >
          {post.fullName}
        </Link>
        <span class="text-[14px] mr-4 cursor-pointer">{timeAgo}</span>
        <span
          onClick={toggleFollow}
          class="relative text-[14px] font-semibold text-[#0095f6] cursor-pointer"
        >
          {followed ? "" : "Follow"}
        </span>
        <Button
          variant="outlined"
          onClick={handleClickOpenMoreOption}
          sx={{
            marginLeft: "auto",
            fontSize: "12px",
            color: "black",
            fontWeight: "600",
            border: "none",
            padding: "0",
            lineHeight: "0",
            letterSpacing: "0px",
            textTransform: "none",
            minWidth: "0",
            ":hover": {
              border: "none",
              backgroundColor: "white",
            },
          }}
        >
          <svg
            aria-label="More options"
            class="x1lliihq x1n2onr6 x5n08af"
            fill="currentColor"
            height="24"
            role="img"
            viewBox="0 0 24 24"
            width="24"
          >
            <title>More options</title>
            <circle cx="12" cy="12" r="1.5"></circle>
            <circle cx="6" cy="12" r="1.5"></circle>
            <circle cx="18" cy="12" r="1.5"></circle>
          </svg>
        </Button>
      </div>

      {/* <!-- image --> */}
      <Carousel showThumbs={false} showStatus={false}>
        {post.images.map((image, index) => {
          return (
            <div key={index} class="flex select-none">
              <img
                class="w-full h-[585px] rounded-[4px] object-cover"
                src={image}
                alt=""
              />
            </div>
          );
        })}
      </Carousel>
      <div class="mt-5 flex gap-x-4 mb-2">
        {/* <!-- like --> */}
        <div onClick={toggleLike} className="cursor-pointer">
          {liked ? (
            <svg
              aria-label="Unlike"
              class="x1lliihq x1n2onr6 xxk16z8 text-red-600"
              fill="currentColor"
              height="24"
              role="img"
              viewBox="0 0 48 48"
              width="24"
            >
              <title>Unlike</title>
              <path d="M34.6 3.1c-4.5 0-7.9 1.8-10.6 5.6-2.7-3.7-6.1-5.5-10.6-5.5C6 3.1 0 9.6 0 17.6c0 7.3 5.4 12 10.6 16.5.6.5 1.3 1.1 1.9 1.7l2.3 2c4.4 3.9 6.6 5.9 7.6 6.5.5.3 1.1.5 1.6.5s1.1-.2 1.6-.5c1-.6 2.8-2.2 7.8-6.8l2-1.8c.7-.6 1.3-1.2 2-1.7C42.7 29.6 48 25 48 17.6c0-8-6-14.5-13.4-14.5z"></path>
            </svg>
          ) : (
            <svg
              aria-label="Like"
              class="x1lliihq x1n2onr6 xyb1xck"
              fill="currentColor"
              height="24"
              role="img"
              viewBox="0 0 24 24"
              width="24"
            >
              <title>Like</title>
              <path d="M16.792 3.904A4.989 4.989 0 0 1 21.5 9.122c0 3.072-2.652 4.959-5.197 7.222-2.512 2.243-3.865 3.469-4.303 3.752-.477-.309-2.143-1.823-4.303-3.752C5.141 14.072 2.5 12.167 2.5 9.122a4.989 4.989 0 0 1 4.708-5.218 4.21 4.21 0 0 1 3.675 1.941c.84 1.175.98 1.763 1.12 1.763s.278-.588 1.11-1.766a4.17 4.17 0 0 1 3.679-1.938m0-2a6.04 6.04 0 0 0-4.797 2.127 6.052 6.052 0 0 0-4.787-2.127A6.985 6.985 0 0 0 .5 9.122c0 3.61 2.55 5.827 5.015 7.97.283.246.569.494.853.747l1.027.918a44.998 44.998 0 0 0 3.518 3.018 2 2 0 0 0 2.174 0 45.263 45.263 0 0 0 3.626-3.115l.922-.824c.293-.26.59-.519.885-.774 2.334-2.025 4.98-4.32 4.98-7.94a6.985 6.985 0 0 0-6.708-7.218Z"></path>
            </svg>
          )}
        </div>

        {/* <!-- comment --> */}
        <div onClick={handleClickOpenComment} class="cursor-pointer">
          <div>
            <svg
              aria-label="Comment"
              class="x1lliihq x1n2onr6 x5n08af"
              fill="currentColor"
              height="24"
              role="img"
              viewBox="0 0 24 24"
              width="24"
            >
              <title>Comment</title>
              <path
                d="M20.656 17.008a9.993 9.993 0 1 0-3.59 3.615L22 22Z"
                fill="none"
                stroke="currentColor"
                stroke-linejoin="round"
                stroke-width="2"
              ></path>
            </svg>
          </div>
        </div>

        {/* <!-- share --> */}
        <div class="cursor-pointer">
          <div>
            <svg
              aria-label="Share Post"
              class="x1lliihq x1n2onr6 x1roi4f4"
              fill="currentColor"
              height="24"
              role="img"
              viewBox="0 0 24 24"
              width="24"
            >
              <title>Share Post</title>
              <line
                fill="none"
                stroke="currentColor"
                stroke-linejoin="round"
                stroke-width="2"
                x1="22"
                x2="9.218"
                y1="3"
                y2="10.083"
              ></line>
              <polygon
                fill="none"
                points="11.698 20.334 22 3.001 2 3.001 9.218 10.084 11.698 20.334"
                stroke="currentColor"
                stroke-linejoin="round"
                stroke-width="2"
              ></polygon>
            </svg>
          </div>
        </div>

        {/* <!-- save --> */}
        <div class="ml-auto cursor-pointer">
          <div>
            <svg
              aria-label="Save"
              class="x1lliihq x1n2onr6 x5n08af"
              fill="currentColor"
              height="24"
              role="img"
              viewBox="0 0 24 24"
              width="24"
            >
              <title>Save</title>
              <polygon
                fill="none"
                points="20 21 12 13.44 4 21 4 3 20 3 20 21"
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
              ></polygon>
            </svg>
          </div>
        </div>
      </div>
      <div class="">
        <span class="text-[14px] font-semibold">{currentLike} likes</span>
      </div>

      <Button
        variant="outlined"
        onClick={handleClickOpenComment}
        sx={{
          marginLeft: "auto",
          fontSize: "12px",
          color: "black",
          fontWeight: "600",
          border: "none",
          padding: "0",
          lineHeight: "0",
          letterSpacing: "0px",
          textTransform: "none",
          minWidth: "0",
          ":hover": {
            border: "none",
            backgroundColor: "white",
          },
        }}
      >
        <p class="text-[14px] text-[#737373] cursor-pointer">
          View all {post.countComment} comments
        </p>
      </Button>
      <div class="w-full h-[1px] bg-[#dbdbdb] my-6"></div>

      {/* Show dialog more option */}
      <Dialog
        open={openMoreOption}
        onClose={handleCloseMoreOption}
        sx={{
          "& .MuiDialog-paper": {
            borderRadius: "8px",
          },
        }}
      >
        <DialogContent
          sx={{
            padding: "0",
          }}
        >
          <div class="container">
            <div class="grid grid-cols-1 w-[384px] bg-[#fff]">
              {followed && post.relationshipType !== "YOU" ? (
                <span
                  onClick={handleClickOpenUnfollow}
                  class="cursor-pointer text-[14px] w-full h-[50px] flex items-center justify-center border-y-[1px] border-[#dbdbdb] text-[#ed4956] font-medium"
                >
                  Unfollow
                </span>
              ) : (
                ""
              )}
              <Link to={`/post/${post.idPost}`} class="cursor-pointer text-[14px] w-full h-[50px] flex items-center justify-center border-y-[1px] border-[#dbdbdb]">
                Go to post
              </Link>

              <span
                onClick={handleCloseMoreOption}
                class="cursor-pointer text-[14px] w-full h-[50px] flex items-center justify-center border-y-[1px] border-[#dbdbdb]"
              >
                Cancel
              </span>
            </div>
          </div>
        </DialogContent>
      </Dialog>

      {/* Show dialog unfollow */}
      <Dialog
        open={openUnfollow}
        onClose={handleCloseUnfollow}
        sx={{
          "& .MuiDialog-paper": {
            borderRadius: "8px",
          },
        }}
      >
        <DialogContent
          sx={{
            padding: "0",
          }}
        >
          <div className="w-[400px] ">
            <div className="p-[32px] flex flex-col items-center gap-y-6">
              <div className="flex justify-center items-center">
                <img
                  className="w-[90px] h-[90px] rounded-[50%] object-cover"
                  src={post.urlAvt}
                />
              </div>
              <div>
                <span className="text-[14px]">Unfollow {post.fullName}?</span>
              </div>
            </div>
            <div
              onClick={handleUnfollow}
              className="flex items-center justify-center py-3 border-y cursor-pointer"
            >
              <span className="text-[14px] font-medium text-red-600">
                Unfollow
              </span>
            </div>
            <div
              onClick={handleCloseUnfollow}
              className="flex items-center justify-center py-3 border-b cursor-pointer"
            >
              <span className="text-[14px]">Cancel</span>
            </div>
          </div>
        </DialogContent>
      </Dialog>

      {/* show dialog comment */}
      <Dialog
        open={openComment}
        onClose={handleCloseComment}
        sx={{
          "& .MuiDialog-paper": {
            borderRadius: "8px",
            maxWidth: "none",
            overflow: "hidden",
          },
        }}
      >
        <DialogContent
          sx={{
            padding: "0",
          }}
        >
          <div className="flex w-[1000px] h-[580px]">
            <div className="w-[45%]">
              <Carousel showThumbs={false} showStatus={false}>
                {post.images.map((image, index) => {
                  console.log("image: ", image);
                  return (
                    <img
                      key={index}
                      className="w-full h-[580px] object-cover"
                      src={image}
                    />
                  );
                })}
              </Carousel>
            </div>

            <div className="w-[55%] grid grid-rows-10 grid-cols-1">
              <div className="row-start-1 row-span-1 border-b">
                <div className="flex items-center p-2">
                  <Link
                    to={`/profile/${post ? post.idProfile : ""}`}
                    class="w-[42px] h-[42px] flex items-center justify-center mr-2 cursor-pointer"
                  >
                    <img
                      class="w-[32px] h-[32px] rounded-[50%] object-cover"
                      src={post.urlAvt}
                      alt="asd"
                    />
                  </Link>
                  <Link
                    to={`/profile/${post ? post.idProfile : ""}`}
                    class="relative text-[14px] font-medium mr-4 cursor-pointer"
                  >
                    {post.fullName}
                  </Link>
                  <span
                    onClick={toggleFollow}
                    class="relative text-[14px] font-semibold text-[#0095f6] cursor-pointer"
                  >
                    {followed ? "" : "Follow"}
                  </span>
                  {post.relationshipType === "YOU" ? (
                    <div
                      onClick={handleClickOpenEditPost}
                      className="ml-auto cursor-pointer"
                    >
                      <svg
                        aria-label="More options"
                        class="x1lliihq x1n2onr6 x5n08af"
                        fill="currentColor"
                        height="24"
                        role="img"
                        viewBox="0 0 24 24"
                        width="24"
                      >
                        <title>More options</title>
                        <circle cx="12" cy="12" r="1.5"></circle>
                        <circle cx="6" cy="12" r="1.5"></circle>
                        <circle cx="18" cy="12" r="1.5"></circle>
                      </svg>
                    </div>
                  ) : (
                    ""
                  )}
                </div>
              </div>

              {/* Comment */}
              <div className="p-2 row-start-2 row-span-6 w-full overflow-auto border-b">
                {post ? <CommentDetail comment={post} isTrue={false} /> : ""}
                {listComment.map((comment, index) => {
                  return <CommentDetail comment={comment} isTrue={false} />;
                })}
              </div>

              <div className="row-start-8 row-span-2 p-4 border-b">
                <div class=" flex gap-x-4 mb-2">
                  {/* <!-- like --> */}
                  <div onClick={toggleLike} className="cursor-pointer">
                    {liked ? (
                      <svg
                        aria-label="Unlike"
                        class="x1lliihq x1n2onr6 xxk16z8 text-red-600"
                        fill="currentColor"
                        height="24"
                        role="img"
                        viewBox="0 0 48 48"
                        width="24"
                      >
                        <title>Unlike</title>
                        <path d="M34.6 3.1c-4.5 0-7.9 1.8-10.6 5.6-2.7-3.7-6.1-5.5-10.6-5.5C6 3.1 0 9.6 0 17.6c0 7.3 5.4 12 10.6 16.5.6.5 1.3 1.1 1.9 1.7l2.3 2c4.4 3.9 6.6 5.9 7.6 6.5.5.3 1.1.5 1.6.5s1.1-.2 1.6-.5c1-.6 2.8-2.2 7.8-6.8l2-1.8c.7-.6 1.3-1.2 2-1.7C42.7 29.6 48 25 48 17.6c0-8-6-14.5-13.4-14.5z"></path>
                      </svg>
                    ) : (
                      <svg
                        aria-label="Like"
                        class="x1lliihq x1n2onr6 xyb1xck"
                        fill="currentColor"
                        height="24"
                        role="img"
                        viewBox="0 0 24 24"
                        width="24"
                      >
                        <title>Like</title>
                        <path d="M16.792 3.904A4.989 4.989 0 0 1 21.5 9.122c0 3.072-2.652 4.959-5.197 7.222-2.512 2.243-3.865 3.469-4.303 3.752-.477-.309-2.143-1.823-4.303-3.752C5.141 14.072 2.5 12.167 2.5 9.122a4.989 4.989 0 0 1 4.708-5.218 4.21 4.21 0 0 1 3.675 1.941c.84 1.175.98 1.763 1.12 1.763s.278-.588 1.11-1.766a4.17 4.17 0 0 1 3.679-1.938m0-2a6.04 6.04 0 0 0-4.797 2.127 6.052 6.052 0 0 0-4.787-2.127A6.985 6.985 0 0 0 .5 9.122c0 3.61 2.55 5.827 5.015 7.97.283.246.569.494.853.747l1.027.918a44.998 44.998 0 0 0 3.518 3.018 2 2 0 0 0 2.174 0 45.263 45.263 0 0 0 3.626-3.115l.922-.824c.293-.26.59-.519.885-.774 2.334-2.025 4.98-4.32 4.98-7.94a6.985 6.985 0 0 0-6.708-7.218Z"></path>
                      </svg>
                    )}
                  </div>

                  {/* <!-- comment --> */}
                  <div class="cursor-pointer">
                    <div>
                      <svg
                        aria-label="Comment"
                        class="x1lliihq x1n2onr6 x5n08af"
                        fill="currentColor"
                        height="24"
                        role="img"
                        viewBox="0 0 24 24"
                        width="24"
                      >
                        <title>Comment</title>
                        <path
                          d="M20.656 17.008a9.993 9.993 0 1 0-3.59 3.615L22 22Z"
                          fill="none"
                          stroke="currentColor"
                          stroke-linejoin="round"
                          stroke-width="2"
                        ></path>
                      </svg>
                    </div>
                  </div>

                  {/* <!-- share --> */}
                  <div class="cursor-pointer">
                    <div>
                      <svg
                        aria-label="Share Post"
                        class="x1lliihq x1n2onr6 x1roi4f4"
                        fill="currentColor"
                        height="24"
                        role="img"
                        viewBox="0 0 24 24"
                        width="24"
                      >
                        <title>Share Post</title>
                        <line
                          fill="none"
                          stroke="currentColor"
                          stroke-linejoin="round"
                          stroke-width="2"
                          x1="22"
                          x2="9.218"
                          y1="3"
                          y2="10.083"
                        ></line>
                        <polygon
                          fill="none"
                          points="11.698 20.334 22 3.001 2 3.001 9.218 10.084 11.698 20.334"
                          stroke="currentColor"
                          stroke-linejoin="round"
                          stroke-width="2"
                        ></polygon>
                      </svg>
                    </div>
                  </div>

                  {/* <!-- save --> */}
                  <div class="ml-auto cursor-pointer">
                    <div>
                      <svg
                        aria-label="Save"
                        class="x1lliihq x1n2onr6 x5n08af"
                        fill="currentColor"
                        height="24"
                        role="img"
                        viewBox="0 0 24 24"
                        width="24"
                      >
                        <title>Save</title>
                        <polygon
                          fill="none"
                          points="20 21 12 13.44 4 21 4 3 20 3 20 21"
                          stroke="currentColor"
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                        ></polygon>
                      </svg>
                    </div>
                  </div>
                </div>
                <div className="flex flex-col mt-4">
                  <span class="text-[14px] font-semibold">
                    {currentLike} likes
                  </span>
                  <span className="text-[12px] text-[#737373]">
                    {timeAgo}
                  </span>
                </div>
              </div>
              <div className="row-start-10 row-span-1 flex items-center justify-center p-4">
                <div className="w-full flex items-center gap-x-2">
                  <div>
                    <svg
                      aria-label="Emoji"
                      class="x1lliihq x1n2onr6 x5n08af"
                      fill="currentColor"
                      height="24"
                      role="img"
                      viewBox="0 0 24 24"
                      width="24"
                    >
                      <title>Emoji</title>
                      <path d="M15.83 10.997a1.167 1.167 0 1 0 1.167 1.167 1.167 1.167 0 0 0-1.167-1.167Zm-6.5 1.167a1.167 1.167 0 1 0-1.166 1.167 1.167 1.167 0 0 0 1.166-1.167Zm5.163 3.24a3.406 3.406 0 0 1-4.982.007 1 1 0 1 0-1.557 1.256 5.397 5.397 0 0 0 8.09 0 1 1 0 0 0-1.55-1.263ZM12 .503a11.5 11.5 0 1 0 11.5 11.5A11.513 11.513 0 0 0 12 .503Zm0 21a9.5 9.5 0 1 1 9.5-9.5 9.51 9.51 0 0 1-9.5 9.5Z"></path>
                    </svg>
                  </div>
                  <input
                    type="text"
                    placeholder="Add a comment..."
                    value={inputContent}
                    onChange={handleInputChange}
                    className="w-full outline-none break-words whitespace-normal"
                  />
                  <span
                    onClick={handlePost}
                    className="font-medium text-[#0095f6] cursor-pointer"
                  >
                    Post
                  </span>
                </div>
              </div>
            </div>
          </div>
        </DialogContent>
      </Dialog>

      {/* dialog edit post */}
      <Dialog
        open={openEditPost}
        onClose={handleCloseEditPost}
        sx={{
          "& .MuiDialog-paper": {
            borderRadius: "8px",
            maxWidth: "none",
          },
        }}
      >
        <DialogContent
          sx={{
            padding: "0",
          }}
        >
          <div className="w-80 h-38 flex flex-col justify-center items-center">
            <div
              onClick={handleClickOpenDeletePost}
              className="h-12 cursor-pointer w-full border-b flex items-center justify-center"
            >
              <span className="text-sm font-medium text-red-600">Delete</span>
            </div>
            <div
              onClick={handleClickOpenEditCaption}
              className="h-12 cursor-pointer w-full border-b flex items-center justify-center"
            >
              <span className="text-sm">Edit</span>
            </div>
            <div
              onClick={handleCloseEditPost}
              className="h-12 cursor-pointer w-full border-b flex items-center justify-center"
            >
              <span className="text-sm">Cancel</span>
            </div>
          </div>
        </DialogContent>
      </Dialog>

      {/* dialog delete post */}
      <Dialog
        open={openDeletePost}
        onClose={handleCloseDeletePost}
        sx={{
          "& .MuiDialog-paper": {
            borderRadius: "8px",
            maxWidth: "none",
          },
        }}
      >
        <DialogContent
          sx={{
            padding: "0",
          }}
        >
          <div className="w-96 h-48">
            <div className="w-full border-b h-24 flex flex-col justify-center items-center">
              <span className="text-xl">Delete Post?</span>
              <span className="text-sm text-gray-400">
                Are you sure you want to delete this post?
              </span>
            </div>
            <div
              onClick={handleDeletePostAPI}
              className="flex cursor-pointer font-medium text-red-600 border-b w-full h-12 items-center justify-center"
            >
              <span>Delete</span>
            </div>
            <div
              onClick={handleCloseDeletePost}
              className="flex cursor-pointer w-full h-12 items-center justify-center"
            >
              <span>Cancel</span>
            </div>
          </div>
        </DialogContent>
      </Dialog>

      {/* edit caption */}
      <Dialog
        open={openEditCaption}
        onClose={handleCloseEditCaption}
        sx={{
          "& .MuiDialog-paper": {
            borderRadius: "8px",
            maxWidth: "none",
          },
        }}
      >
        <DialogContent
          sx={{
            padding: "0",
          }}
        >
          <div className="w-[800px] h-[500px]">
            <div className="flex items-center py-2 px-4 border-b">
              <span className="text-base font-medium ml-[50%] translate-x-[-50%]">
                Edit Info
              </span>
              <span
                onClick={handleUpdatePostAPI}
                className="text-base font-medium ml-auto cursor-pointer text-[#0095f6]"
              >
                Done
              </span>
            </div>
            <div className="h-[450px] flex ">
              <div className="flex justify-center items-center w-[55%] h-full">
                <div className="px-2">
                  <Carousel showThumbs={false} showStatus={false}>
                    {post
                      ? post.images.map((image, index) => {
                          console.log(image);
                          return (
                            <div key={index} class="flex select-none">
                              <img
                                className="object-cover w-[335px] h-[430px]"
                                src={image}
                                alt="Selected"
                              />
                            </div>
                          );
                        })
                      : ""}
                  </Carousel>
                </div>
              </div>
              <div className="w-[45%] p-3 border-l">
                <div className="flex items-center gap-3">
                  <div>
                    <img
                      className="w-[28px] h-[28px] rounded-[50%] object-cover"
                      src={post ? post.urlAvt : ""}
                    />
                  </div>
                  <div>
                    <span className="text-sm font-medium">
                      {post ? post.fullName : ""}
                    </span>
                  </div>
                </div>
                <div className="mt-4 pt-2 border-t">
                  <textarea
                    placeholder="Write a caption"
                    className="w-full h-96 border-none outline-none focus:border-none focus:outline-none"
                    onChange={(e) => setCaption(e.target.value)}
                    value={caption}
                  />
                </div>
              </div>
            </div>
          </div>
        </DialogContent>
      </Dialog>

      {/* popup password */}
      <Snackbar
        open={openPopup}
        autoHideDuration={3000}
        onClose={handleClosePopup}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
      >
        <Alert
          onClose={handleClosePopup}
          severity={popupStatus}
          variant="filled"
          sx={{ width: "100%" }}
        >
          {popupContent}
        </Alert>
      </Snackbar>
    </div>
  );
}

export default Post;
