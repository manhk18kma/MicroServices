import { Alert, Button, Dialog, DialogContent, Snackbar } from "@mui/material";
import * as React from "react";
import CommentDetail from "../Home/CommentDetail";
import { Link, useNavigate, useParams } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import {
  changePassword,
  generateOtpForgotPassword,
  getFollower,
  getFollowing,
  getProfile,
  updateProfile,
} from "../../api/AccountAPI";
import {
  countLike,
  createFollow,
  createNewComment,
  getAllComment,
  likePost,
  unFollow,
  unFriend,
  unlikePost,
  getListImage,
  getPostById,
  deletePost,
  updatePost,
} from "../../api/PostAPI";
import { Carousel } from "react-responsive-carousel";
import { formatDistanceToNow } from "date-fns";

function Profile() {
  // dialog edit profile
  const [openEditProfile, setOpenEditProfile] = React.useState(false);

  const handleClickOpenEditProfile = () => {
    setOpenEditProfile(true);
  };

  const handleCloseEditProfile = () => {
    setOpenEditProfile(false);
  };

  // dialog change password
  const [openChangePassword, setOpenChangePassword] = React.useState(false);

  const handleClickOpenChangePassword = () => {
    generateOtpForgotPassword({ email: tokenDetail.email }).then((res) => {
      console.log("Send Otp thanh cong", res);
    });
    setOpenChangePassword(true);
  };

  const handleCloseChangePassword = () => {
    setOpenChangePassword(false);
  };

  // Click image to view all comments
  // Comment Dialog
  const [openComment, setOpenComment] = React.useState(false);
  const [listComment, setListComment] = React.useState([]);
  const [postAfterClick, setPostAfterClick] = React.useState();
  const [notification, setNotification] = React.useState(new Date())

  const handleClickOpenComment = (idPost) => {
    setOpenComment(true);
    getAllComment({ idPost: idPost, token: token }).then((res) => {
      console.log("danh sach comment: ", res);
      setListComment(res.data.items);
    });
    getPostById({ token: token, idPost: idPost }).then((res) => {
      console.log("bai post sau khi click: ", res.data);
      setPostAfterClick(res.data);
      setLiked(res.data.liked);
      setCurrentLike(res.data.countLikes);
      console.log("type: ", res.data.relationshipType);
      setTypeRelationship(res.data.relationshipType);
      setCaption(res.data.caption);
      setNotification(res.data.updateAt)
    });
  };

  const handleCloseComment = () => {
    setOpenComment(false);
  };

  // type relationship
  const [typeRelationship, setTypeRelationship] = React.useState("YOU");
  const [isEditProfile, setIsEditProfile] = React.useState("YOU");

  // Send Comment
  const [inputContentComment, setInputContentComment] = React.useState("");

  const handleInputCommentChange = (e) => {
    setInputContentComment(e.target.value);
  };

  const handlePostComment = () => {
    createNewComment({
      idPost: postAfterClick.idPost,
      content: inputContentComment,
      token: token,
    }).then((res) => {
      console.log("return sau khi comment: ", res);
      console.log("list comment: ", listComment);
      setListComment([...listComment, res.data]);
    });
    setInputContentComment("");
  };

  // like
  const [liked, setLiked] = React.useState(false);
  const [currentLike, setCurrentLike] = React.useState(0);
  const toggleLike = () => {
    setLiked(!liked);
    setCurrentLike(liked ? currentLike - 1 : currentLike + 1);
    if (liked) {
      unlikePost({ idPost: postAfterClick.idPost, token: token }).then(
        (res) => {
          console.log("unlike success");
        }
      );
    } else {
      likePost({ idPost: postAfterClick.idPost, token: token }).then((res) => {
        console.log("unlike success");
      });
    }
  };

  // file
  const fileInputRef = React.useRef(null);

  const handleClickFile = () => {
    fileInputRef.current.click();
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        setAvt(event.target.result);
      };
      reader.readAsDataURL(file);
    }
  };

  // get token
  const token = localStorage.getItem("token");
  const tokenDetail = jwtDecode(token);

  // get profile
  const params = useParams();
  const [tempParams, setTempParams] = React.useState(params);
  const [profile, setProfile] = React.useState([]);
  const [listImage, setListImage] = React.useState([]);

  if (tempParams.idProfile !== params.idProfile) {
    setTempParams(params);
    window.location.reload();
  }

  React.useEffect(() => {
    getProfile({ token: token, idProfile: params.idProfile }).then((res) => {
      console.log("return sau khi get profile: ", res.data);
      setProfile(res.data);
      setFullName(res.data.fullName);
      setSelectedGender(res.data.gender);
      setBio(res.data.biography);
      setAvt(res.data.urlProfilePicture);
      setDateValue(res.data.dateOfBirth);
      setIsEditProfile(res.data.typeRelationship);
      setFollowed(
        res.data.typeRelationship === "FOLLOWING" ||
          res.data.typeRelationship === "FRIEND" ||
          res.data.typeRelationship === "YOU"
          ? true
          : false
      );
      setIsFriend(res.data.typeRelationship === "FRIEND" ? true : false);
    });
  }, []);

  React.useEffect(() => {
    getListImage({
      token: token,
      idProfile: params.idProfile,
      pageNo: 0,
      pageSize: 10,
    }).then((res) => {
      console.log("return sau khi get image: ", res.data.items);
      setListImage(res.data.items);
    });
  }, []);

  console.log("profile: ", profile);

  // edit profile
  const [fullName, setFullName] = React.useState("");
  const [bio, setBio] = React.useState("");
  // gender
  const [selectedGender, setSelectedGender] = React.useState("");

  const [avt, setAvt] = React.useState();

  const handleGenderChange = (event) => {
    console.log(event.target.value); // M or F
    setSelectedGender(event.target.value);
  };

  // date
  const [dateValue, setDateValue] = React.useState();

  const handleDateChange = (e) => {
    console.log(new Date(e.target.value));
    setDateValue(e.target.value);
  };

  // show follower dialog
  const [openFollower, setOpenFollower] = React.useState(false);
  const [listFollower, setListFollower] = React.useState([]);
  const [listFollowing, setListFollowing] = React.useState([]);

  const handleClickOpenFollower = () => {
    setOpenFollower(true);
    getFollower({ token: token, idProfile: params.idProfile }).then((res) => {
      console.log("list folower: ", res.data.items);
      setListFollower(res.data.items);
    });
  };

  const handleCloseFollower = () => {
    setOpenFollower(false);
  };

  // show following dialog
  const [openFollowing, setOpenFollowing] = React.useState(false);

  const handleClickOpenFollowing = () => {
    setOpenFollowing(true);
    getFollowing({ token: token, idProfile: params.idProfile }).then((res) => {
      console.log("list folowing: ", res.data.items);
      setListFollowing(res.data.items);
    });
  };

  const handleCloseFollowing = () => {
    setOpenFollowing(false);
  };

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
    deletePost({ token: token, idPost: postAfterClick.idPost })
      .then((res) => {
        setPopupStatus("success");
        setPopupContent("Post deleted successfully");
        handleClickPopup();
        setTimeout(() => {
          window.location.reload();
        }, 2000);
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("Failed to delete post");
        handleClickPopup();
      });
  };

  // show edit caption dialog
  const [openEditCaption, setOpenEditCaption] = React.useState(false);
  const [caption, setCaption] = React.useState();

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
      idPost: postAfterClick.idPost,
      caption: caption,
      base64OrUrl: postAfterClick.images,
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

  const navigate = useNavigate();

  // update profile
  const handleUpdateProfile = () => {
    setLoadingUpdateProfile(true);
    updateProfile({
      token: token,
      idProfile: tokenDetail.sub,
      fullName: fullName,
      gender: selectedGender,
      urlProfilePicture: avt,
      biography: bio,
      dateOfBirth: dateValue,
    })
      .then((res) => {
        console.log("return sau khi update", res.data);
        setPopupStatus("success");
        setPopupContent("Profile updated successfully");
        handleClickPopup();
        setTimeout(() => {
          window.location.reload();
        }, 2000);
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("Failed to update profile");
        handleClickPopup();
      });
  };

  // update password
  const [currentPassword, setCurrentPassword] = React.useState("");
  const [newPassword, setNewPassword] = React.useState("");
  const [repeatPassword, setRepeatPassword] = React.useState("");
  const [otp, setOtp] = React.useState("");

  const handleChangePassword = () => {
    changePassword({
      token: token,
      idAccount: tokenDetail.idAccount,
      oldPassword: currentPassword,
      newPassword: newPassword,
      otp: otp,
    })
      .then((res) => {
        setPopupStatus("success");
        setPopupContent("Password changed successfully");
        handleClickPopup();
        setTimeout(() => {
          window.location.reload();
        }, 2000);
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("Failed to change password");
        handleClickPopup();
      });
  };

  // check = null
  if (profile.idChat === null) {
    profile.idChat = "0e9d4087-37a6-4f33-9ba3-cb58ffe424f2";
  }

  // Follow

  // dialog unfollow
  const [openUnfollow, setOpenUnfollow] = React.useState(false);
  const handleClickOpenUnfollow = () => {
    setOpenUnfollow(true);
    isFriend
      ? unFriend({
          idProfileTarget: profile.idProfile,
          token: token,
          tokenDetail: tokenDetail,
        }).then((res) => {
          console.log("return sau khi unfriend: ", res);
        })
      : unFollow({
          idProfileTarget: profile.idProfile,
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
    profile.typeRelationship === "FOLLOWING" ||
      profile.typeRelationship === "FRIEND" ||
      profile.typeRelationship === "YOU"
      ? true
      : false
  );
  const [isFriend, setIsFriend] = React.useState(
    profile.typeRelationship === "FRIEND" ? true : false
  );
  const toggleFollow = () => {
    if (followed) {
      handleClickOpenUnfollow();
    } else {
      setFollowed(true);
      createFollow({
        idProfileTarget: profile.idProfile,
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

  //   an hien pass
  const [isOpenCurrentPass, setIsOpenCurrentPass] = React.useState(false);
  const [isOpenNewPass, setIsOpenNewPass] = React.useState(false);
  const [isOpenRepeatPass, setIsOpenRepeatPass] = React.useState(false);

  const targetCurrentPass = document.getElementById("currentPass");
  const targetNewPass = document.getElementById("newPass");
  const targetRepeatPass = document.getElementById("repeatPass");

  const handleShowCurrentPass = () => {
    setIsOpenCurrentPass(!isOpenCurrentPass);
    if (isOpenCurrentPass === false) {
      targetCurrentPass.type = "text";
    } else {
      targetCurrentPass.type = "password";
    }
  };

  const handleShowNewPass = () => {
    setIsOpenNewPass(!isOpenNewPass);
    if (isOpenNewPass === false) {
      targetNewPass.type = "text";
    } else {
      targetNewPass.type = "password";
    }
  };

  const handleShowRepeatPass = () => {
    setIsOpenRepeatPass(!isOpenRepeatPass);
    if (isOpenRepeatPass === false) {
      targetRepeatPass.type = "text";
    } else {
      targetRepeatPass.type = "password";
    }
  };

  const [loadingUpdateProfile, setLoadingUpdateProfile] = React.useState(false);

    // format date
    const timeAgo = formatDistanceToNow(notification, { addSuffix: true });

  return (
    <div className="col-start-3 col-span-8 p-8 flex justify-center">
      <div className="w-[930px]">
        <div className="flex items-center gap-x-20 pb-12 border-b">
          <div>
            <img
              className="w-[150px] h-[150px] object-cover rounded-[50%]"
              src={avt}
            />
          </div>
          <div className="flex flex-col justify-center">
            <div className="flex items-center gap-x-4">
              <span className="text-[20px]">{fullName}</span>
              {isEditProfile === "YOU" ? (
                <Button
                  variant="outlined"
                  onClick={handleClickOpenEditProfile}
                  sx={{
                    fontSize: "14px",
                    color: "black",
                    fontWeight: "500",
                    border: "none",
                    padding: "14px 12px",
                    lineHeight: "0",
                    letterSpacing: "0px",
                    textTransform: "none",
                    minWidth: "0",
                    backgroundColor: "#efefef",
                    borderRadius: "8px",
                    ":hover": {
                      border: "none",
                      color: "black",
                      backgroundColor: "#efefef",
                    },
                  }}
                >
                  Edit profile
                </Button>
              ) : (
                ""
              )}
              {isEditProfile === "YOU" ? (
                <div
                  onClick={handleClickOpenChangePassword}
                  className="px-2 py-1 bg-slate-100 text-sm rounded-lg cursor-pointer"
                >
                  <span className="block ">Change Password</span>
                </div>
              ) : (
                ""
              )}

              {isEditProfile === "YOU" ? (
                ""
              ) : (
                <Link to={`/chat/${profile.idProfile}/${profile.idChat}`}>
                  <span className="text-sm px-4 py-2 bg-slate-200 font-medium cursor-pointer rounded-md">
                    Message
                  </span>
                </Link>
              )}
              {console.log("day la relationship", typeRelationship)}
              {isEditProfile !== "YOU" ? (
                <span
                  onClick={toggleFollow}
                  className="text-sm px-4 py-2 bg-slate-200 font-medium cursor-pointer rounded-md"
                >
                  {console.log("followed", followed)}
                  {followed ? "Following" : "Follow"}
                </span>
              ) : (
                ""
              )}
            </div>
            <div className="flex items-center gap-x-10 mt-10">
              <span className="text-[16px]">
                <span className="font-medium">{listImage.length}</span> posts
              </span>
              <span onClick={handleClickOpenFollower}>
                <span className="font-medium cursor-pointer mr-1">
                  {profile.countFollowers}
                </span>
                follower
              </span>
              <span onClick={handleClickOpenFollowing}>
                <span className="font-medium cursor-pointer mr-1">
                  {profile.countFollowings}
                </span>
                following
              </span>
            </div>
            <div className="mt-4">
              <span className="font-medium">
                {profile.biography === null ? "" : profile.biography}
              </span>
            </div>
          </div>
        </div>

        <div>
          <div className="relative cursor-pointer flex items-center justify-center gap-x-1 py-4 after:block after:content-[''] after:w-[3rem] after:h-[1px] after:bg-black after:absolute after:right-auto after:left-auto after:top-0 after:cursor-default">
            <div>
              <svg
                aria-label=""
                class="x1lliihq x1n2onr6 x5n08af"
                fill="currentColor"
                height="12"
                role="img"
                viewBox="0 0 24 24"
                width="12"
              >
                <title></title>
                <rect
                  fill="none"
                  height="18"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  width="18"
                  x="3"
                  y="3"
                ></rect>
                <line
                  fill="none"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  x1="9.015"
                  x2="9.015"
                  y1="3"
                  y2="21"
                ></line>
                <line
                  fill="none"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  x1="14.985"
                  x2="14.985"
                  y1="3"
                  y2="21"
                ></line>
                <line
                  fill="none"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  x1="21"
                  x2="3"
                  y1="9.015"
                  y2="9.015"
                ></line>
                <line
                  fill="none"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  x1="21"
                  x2="3"
                  y1="14.985"
                  y2="14.985"
                ></line>
              </svg>
            </div>
            <div>
              <span>POSTS</span>
            </div>
          </div>
          <div className="grid grid-cols-3 gap-2">
            {console.log("list image: ", listImage)}
            {listImage.map((item, index) => {
              return (
                <div>
                  <div
                    key={index}
                    className="aspect"
                    onClick={() => handleClickOpenComment(item.idPost)}
                  >
                    <img
                      className="object-cover aspect-square"
                      src={
                        item.images[0] ||
                        "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
                      }
                    />
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>

      {/* Edit profile dialog */}
      <Dialog
        open={openEditProfile}
        onClose={handleCloseEditProfile}
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
          <div className="w-[700px] h-[600px] p-8">
            {loadingUpdateProfile ? (
              <div
                className="flex items-center justify-center h-full"
                role="status"
              >
                <svg
                  aria-hidden="true"
                  class="w-14 h-14 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600"
                  viewBox="0 0 100 101"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                    fill="currentColor"
                  />
                  <path
                    d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                    fill="currentFill"
                  />
                </svg>
                <span class="sr-only">Loading...</span>
              </div>
            ) : (
              <React.Fragment>
                <div className="flex items-center p-4 border-[1px] rounded-xl">
                  <div>
                    <img
                      className="w-[56px] h-[56px] rounded-[50%] object-cover"
                      src={avt}
                    />
                  </div>
                  <div className="flex flex-col ml-4">
                    <span className="text-base font-medium">
                      {profile.fullName}
                    </span>
                  </div>
                  <div className="ml-auto">
                    <span
                      onClick={handleClickFile}
                      className="p-2 bg-blue-500 rounded-md text-white font-medium cursor-pointer"
                    >
                      Change Photo
                    </span>
                  </div>
                  <input
                    type="file"
                    accept="image/*"
                    ref={fileInputRef}
                    className="hidden"
                    onChange={handleFileChange}
                  />
                </div>
                <div className="mt-6 flex flex-col gap-y-4">
                  <div className="flex flex-col gap-y-2">
                    <span className="text-base font-semibold">Full Name</span>
                    <input
                      type="text"
                      value={fullName}
                      className="focus:outline-none rounded-xl h-[38px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                      placeholder="Full Name"
                      onChange={(e) => setFullName(e.target.value)}
                    />
                  </div>

                  <div className="flex flex-col gap-y-2">
                    <span className="text-base font-semibold">Bio</span>
                    <input
                      type="text"
                      value={bio}
                      className="focus:outline-none rounded-xl h-[38px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                      placeholder="Bio"
                      onChange={(e) => setBio(e.target.value)}
                    />
                  </div>

                  <div className="flex flex-col gap-y-2">
                    <span className="text-base font-semibold">Gender</span>
                    <select
                      name="gender"
                      id="gender"
                      className="outline-none h-[38px] border-[1px] rounded-xl bg-[#fffefe] p-[8px] pb-[10px] text-sm"
                      value={selectedGender} // selected MALE or FEMALE
                      onChange={handleGenderChange}
                    >
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                    </select>
                  </div>

                  <div className="flex flex-col gap-y-2">
                    <span className="text-base font-semibold">
                      Date Of Birth
                    </span>
                    {console.log("date value", dateValue)}
                    <input
                      type="date"
                      className="w-full border-[1px] h-[38px]"
                      value={dateValue ? dateValue.split("T")[0] : ""} //"2021-12-14"
                      onChange={handleDateChange}
                    />
                  </div>
                </div>
                <div
                  onClick={handleUpdateProfile}
                  className="flex items-center justify-center mt-6"
                >
                  <span className="px-28 py-3 bg-blue-500 rounded-xl cursor-pointer text-white font-semibold">
                    Submit
                  </span>
                </div>
              </React.Fragment>
            )}
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
            <div className="w-[45%] h-full">
              <Carousel showThumbs={false} showStatus={false}>
                {postAfterClick
                  ? postAfterClick.images.map((image, index) => {
                      return (
                        <div
                          key={index}
                          className="w-full h-full flex items-center"
                        >
                          <div>
                            <img
                              className="w-full h-full object-cover"
                              src={image}
                              alt="asd"
                            />
                          </div>
                        </div>
                      );
                    })
                  : ""}
              </Carousel>
            </div>

            <div className="w-[55%] grid grid-rows-10 grid-cols-1">
              <div className="row-start-1 row-span-1 border-b">
                <div className="flex items-center p-2">
                  <div class="w-[42px] h-[42px] flex items-center justify-center mr-2 cursor-pointer">
                    <img
                      class="w-[32px] h-[32px] rounded-[50%] object-cover"
                      src={postAfterClick ? postAfterClick.urlAvt : ""}
                      alt="asd"
                    />
                  </div>

                  <div class="relative text-[14px] font-medium mr-4 cursor-pointer">
                    {postAfterClick ? postAfterClick.fullName : ""}
                  </div>
                  <span class="relative text-[14px] font-semibold text-[#0095f6]  cursor-pointer">
                    {typeRelationship === "YOU" ? "" : "Follow"}
                  </span>
                  {typeRelationship === "YOU" ? (
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
                {console.log("tat ca comment:", listComment)}
                {postAfterClick ? (
                  <CommentDetail comment={postAfterClick} isTrue={false} />
                ) : (
                  ""
                )}
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
                    value={inputContentComment}
                    onChange={handleInputCommentChange}
                    className="w-full outline-none break-words whitespace-normal"
                  />
                  <span
                    onClick={handlePostComment}
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

      {/* dialog follower */}
      <Dialog
        open={openFollower}
        onClose={handleCloseFollower}
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
          <div className="w-[384px] h-[400px] py-3">
            <div className="flex items-center px-3 border-b pb-2">
              <div className="ml-[50%] translate-x-[-50%]">
                <span className="text-base font-medium">Followers</span>
              </div>
              <div
                onClick={handleCloseFollower}
                className="ml-auto cursor-pointer"
              >
                <svg
                  aria-label="Close"
                  class="x1lliihq x1n2onr6 x5n08af"
                  fill="currentColor"
                  height="18"
                  role="img"
                  viewBox="0 0 24 24"
                  width="18"
                >
                  <title>Close</title>
                  <polyline
                    fill="none"
                    points="20.643 3.357 12 12 3.353 20.647"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="3"
                  ></polyline>
                  <line
                    fill="none"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="3"
                    x1="20.649"
                    x2="3.354"
                    y1="20.649"
                    y2="3.354"
                  ></line>
                </svg>
              </div>
            </div>
            <div className="overflow-auto w-full h-[350px] px-3 pb-1">
              {listFollower.map((itemFollow, index) => {
                return (
                  <div key={index} className="flex items-center gap-x-3 mt-3">
                    <div>
                      <img
                        className="w-12 h-12 rounded-[50%] object-cover"
                        src={itemFollow.urlProfilePictureFollower}
                      />
                    </div>
                    <div>
                      <span className="text-sm">
                        {itemFollow.fullNameFollower}
                      </span>
                    </div>
                  </div>
                );
              })}
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
                    {postAfterClick
                      ? postAfterClick.images.map((image, index) => {
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
                      src={postAfterClick ? postAfterClick.urlAvt : ""}
                    />
                  </div>
                  <div>
                    <span className="text-sm font-medium">
                      {postAfterClick ? postAfterClick.fullName : ""}
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

      {/* dialog following */}
      <Dialog
        open={openFollowing}
        onClose={handleCloseFollowing}
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
          <div className="w-[384px] h-[400px] py-3">
            <div className="flex items-center px-3 border-b pb-2">
              <div className="ml-[50%] translate-x-[-50%]">
                <span className="text-base font-medium">Following</span>
              </div>
              <div
                onClick={handleCloseFollowing}
                className="ml-auto cursor-pointer"
              >
                <svg
                  aria-label="Close"
                  class="x1lliihq x1n2onr6 x5n08af"
                  fill="currentColor"
                  height="18"
                  role="img"
                  viewBox="0 0 24 24"
                  width="18"
                >
                  <title>Close</title>
                  <polyline
                    fill="none"
                    points="20.643 3.357 12 12 3.353 20.647"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="3"
                  ></polyline>
                  <line
                    fill="none"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="3"
                    x1="20.649"
                    x2="3.354"
                    y1="20.649"
                    y2="3.354"
                  ></line>
                </svg>
              </div>
            </div>
            <div className="overflow-auto w-full h-[350px] px-3 pb-1">
              {listFollowing.map((itemFollowing, index) => {
                return (
                  <div className="flex items-center gap-x-3 mt-3">
                    <div>
                      <img
                        className="w-12 h-12 rounded-[50%] object-cover"
                        src={itemFollowing.urlProfilePicture}
                      />
                    </div>
                    <div>
                      <span className="text-sm">
                        {itemFollowing.fullNameProfileFollowing}
                      </span>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        </DialogContent>
      </Dialog>

      {/* dialog change password */}
      <Dialog
        open={openChangePassword}
        onClose={handleCloseChangePassword}
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
          <div className="w-80 h-80">
            <div className="w-full h-16 flex items-center justify-center">
              <span className="text-xl font-medium">Change password</span>
            </div>
            <div className="flex flex-col gap-y-2 items-center justify-center">
              <div className="relative">
                <input
                  id="currentPass"
                  type="password"
                  className="outline-none w-64 h-10 border-b placeholder:text-sm pl-2"
                  placeholder="Current password"
                  onChange={(e) => setCurrentPassword(e.target.value)}
                />

                <div
                  onClick={handleShowCurrentPass}
                  className="absolute right-0 top-2 cursor-pointer"
                >
                  {!isOpenCurrentPass ? (
                    <svg
                      height="48"
                      viewBox="0 0 48 48"
                      width="48"
                      xmlns="http://www.w3.org/2000/svg"
                      className="w-10 h-6"
                    >
                      <path d="M0 0h48v48h-48z" fill="none" />
                      <path d="M24 9c-10 0-18.54 6.22-22 15 3.46 8.78 12 15 22 15 10.01 0 18.54-6.22 22-15-3.46-8.78-11.99-15-22-15zm0 25c-5.52 0-10-4.48-10-10s4.48-10 10-10 10 4.48 10 10-4.48 10-10 10zm0-16c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6z" />
                    </svg>
                  ) : (
                    <svg
                      height="48"
                      viewBox="0 0 48 48"
                      width="48"
                      xmlns="http://www.w3.org/2000/svg"
                      className="w-10 h-6"
                    >
                      <path
                        d="M0 0h48v48h-48zm0 0h48v48h-48zm0 0h48v48h-48zm0 0h48v48h-48z"
                        fill="none"
                      />
                      <path d="M24 14c5.52 0 10 4.48 10 10 0 1.29-.26 2.52-.71 3.65l5.85 5.85c3.02-2.52 5.4-5.78 6.87-9.5-3.47-8.78-12-15-22.01-15-2.8 0-5.48.5-7.97 1.4l4.32 4.31c1.13-.44 2.36-.71 3.65-.71zm-20-5.45l4.56 4.56.91.91c-3.3 2.58-5.91 6.01-7.47 9.98 3.46 8.78 12 15 22 15 3.1 0 6.06-.6 8.77-1.69l.85.85 5.83 5.84 2.55-2.54-35.45-35.46-2.55 2.55zm11.06 11.05l3.09 3.09c-.09.43-.15.86-.15 1.31 0 3.31 2.69 6 6 6 .45 0 .88-.06 1.3-.15l3.09 3.09c-1.33.66-2.81 1.06-4.39 1.06-5.52 0-10-4.48-10-10 0-1.58.4-3.06 1.06-4.4zm8.61-1.57l6.3 6.3.03-.33c0-3.31-2.69-6-6-6l-.33.03z" />
                    </svg>
                  )}
                </div>
              </div>

              <div className="relative">
                <input
                  id="newPass"
                  type="password"
                  className=" outline-none w-64 h-10 border-b placeholder:text-sm pl-2"
                  placeholder="New password"
                  onChange={(e) => setNewPassword(e.target.value)}
                />

                <div
                  onClick={handleShowNewPass}
                  className="absolute right-0 top-2 cursor-pointer"
                >
                  {!isOpenNewPass ? (
                    <svg
                      height="48"
                      viewBox="0 0 48 48"
                      width="48"
                      xmlns="http://www.w3.org/2000/svg"
                      className="w-10 h-6"
                    >
                      <path d="M0 0h48v48h-48z" fill="none" />
                      <path d="M24 9c-10 0-18.54 6.22-22 15 3.46 8.78 12 15 22 15 10.01 0 18.54-6.22 22-15-3.46-8.78-11.99-15-22-15zm0 25c-5.52 0-10-4.48-10-10s4.48-10 10-10 10 4.48 10 10-4.48 10-10 10zm0-16c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6z" />
                    </svg>
                  ) : (
                    <svg
                      height="48"
                      viewBox="0 0 48 48"
                      width="48"
                      xmlns="http://www.w3.org/2000/svg"
                      className="w-10 h-6"
                    >
                      <path
                        d="M0 0h48v48h-48zm0 0h48v48h-48zm0 0h48v48h-48zm0 0h48v48h-48z"
                        fill="none"
                      />
                      <path d="M24 14c5.52 0 10 4.48 10 10 0 1.29-.26 2.52-.71 3.65l5.85 5.85c3.02-2.52 5.4-5.78 6.87-9.5-3.47-8.78-12-15-22.01-15-2.8 0-5.48.5-7.97 1.4l4.32 4.31c1.13-.44 2.36-.71 3.65-.71zm-20-5.45l4.56 4.56.91.91c-3.3 2.58-5.91 6.01-7.47 9.98 3.46 8.78 12 15 22 15 3.1 0 6.06-.6 8.77-1.69l.85.85 5.83 5.84 2.55-2.54-35.45-35.46-2.55 2.55zm11.06 11.05l3.09 3.09c-.09.43-.15.86-.15 1.31 0 3.31 2.69 6 6 6 .45 0 .88-.06 1.3-.15l3.09 3.09c-1.33.66-2.81 1.06-4.39 1.06-5.52 0-10-4.48-10-10 0-1.58.4-3.06 1.06-4.4zm8.61-1.57l6.3 6.3.03-.33c0-3.31-2.69-6-6-6l-.33.03z" />
                    </svg>
                  )}
                </div>
              </div>

              <div className="relative">
                <input
                  id="repeatPass"
                  type="password"
                  className="outline-none w-64 h-10 border-b placeholder:text-sm pl-2"
                  placeholder="Repeat New password"
                  onChange={(e) => setRepeatPassword(e.target.value)}
                />
                <div
                  onClick={handleShowRepeatPass}
                  className="absolute right-0 top-2 cursor-pointer"
                >
                  {!isOpenRepeatPass ? (
                    <svg
                      height="48"
                      viewBox="0 0 48 48"
                      width="48"
                      xmlns="http://www.w3.org/2000/svg"
                      className="w-10 h-6"
                    >
                      <path d="M0 0h48v48h-48z" fill="none" />
                      <path d="M24 9c-10 0-18.54 6.22-22 15 3.46 8.78 12 15 22 15 10.01 0 18.54-6.22 22-15-3.46-8.78-11.99-15-22-15zm0 25c-5.52 0-10-4.48-10-10s4.48-10 10-10 10 4.48 10 10-4.48 10-10 10zm0-16c-3.31 0-6 2.69-6 6s2.69 6 6 6 6-2.69 6-6-2.69-6-6-6z" />
                    </svg>
                  ) : (
                    <svg
                      height="48"
                      viewBox="0 0 48 48"
                      width="48"
                      xmlns="http://www.w3.org/2000/svg"
                      className="w-10 h-6"
                    >
                      <path
                        d="M0 0h48v48h-48zm0 0h48v48h-48zm0 0h48v48h-48zm0 0h48v48h-48z"
                        fill="none"
                      />
                      <path d="M24 14c5.52 0 10 4.48 10 10 0 1.29-.26 2.52-.71 3.65l5.85 5.85c3.02-2.52 5.4-5.78 6.87-9.5-3.47-8.78-12-15-22.01-15-2.8 0-5.48.5-7.97 1.4l4.32 4.31c1.13-.44 2.36-.71 3.65-.71zm-20-5.45l4.56 4.56.91.91c-3.3 2.58-5.91 6.01-7.47 9.98 3.46 8.78 12 15 22 15 3.1 0 6.06-.6 8.77-1.69l.85.85 5.83 5.84 2.55-2.54-35.45-35.46-2.55 2.55zm11.06 11.05l3.09 3.09c-.09.43-.15.86-.15 1.31 0 3.31 2.69 6 6 6 .45 0 .88-.06 1.3-.15l3.09 3.09c-1.33.66-2.81 1.06-4.39 1.06-5.52 0-10-4.48-10-10 0-1.58.4-3.06 1.06-4.4zm8.61-1.57l6.3 6.3.03-.33c0-3.31-2.69-6-6-6l-.33.03z" />
                    </svg>
                  )}
                </div>
              </div>

              <input
                type="text"
                className="outline-none w-64 h-10 border-b placeholder:text-sm pl-2"
                placeholder="OTP"
                onChange={(e) => setOtp(e.target.value)}
              />
            </div>
            <div
              onClick={handleChangePassword}
              className="text-center cursor-pointer mt-6"
            >
              <span className="text-base text-white px-3 py-2 rounded-md bg-blue-500 font-medium">
                Update
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
                  src={profile.urlProfilePicture}
                />
              </div>
              <div>
                <span className="text-[14px]">{profile.fullName}</span>
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

export default Profile;
