import { Button, Dialog, DialogContent } from "@mui/material";
import * as React from "react";
import DefaultGroupChat from "./DefaultGroupChat";
import GroupChat from "./GroupChat";
import { getAllFriends, readMessage } from "../../api/ChatAPI";
import { jwtDecode } from "jwt-decode";
import { MyContextMessage } from "../../components/Layouts/MessageLayout";
import { useNavigate, useParams } from "react-router-dom";
import { getProfile, searchUser } from "../../api/AccountAPI";
import { formatDistanceToNow } from "date-fns";

function Chat() {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const [id, setId] = React.useState("");

  // check height of the screen
  const [checkHeight, setCheckHeight] = React.useState(window.innerHeight);
  const checkDivHeight = () => {
    const newHeight = window.innerHeight;
    setCheckHeight(newHeight);
    const divFriend = document.getElementById("divFriend");
    divFriend.style.height = newHeight - 144 + "px";
  };

  React.useEffect(() => {
    // Kiểm tra khi tải trang
    checkDivHeight();

    // Đăng ký sự kiện resize
    window.addEventListener("resize", checkDivHeight);

    // Cleanup: Hủy đăng ký sự kiện khi component bị unmount
    return () => {
      window.removeEventListener("resize", checkDivHeight);
    };
  }, [checkHeight]); // Phải có sharedWidth ở đây để useEffect lắng nghe sự thay đổi của nó

  // Get list friends

  const [friends, setFriends] = React.useState([]);
  const [tempFriend, setTempFriend] = React.useState(null);
  const [flag, setFlag] = React.useState();
  const { isNewMessage } = React.useContext(MyContextMessage);
  React.useEffect(() => {
    console.log("day la params sau khi chat", params);
    const getListFriends = async () => {
      if (tokenDetail.idChatProfile.trim().length > 0) {
        try {
          console.log("Re render---------");
          const friends = await getAllFriends({
            myId: tokenDetail.idChatProfile,
            pageNo: 0,
            pageSize: 20,
            token: token,
          });
          console.log("danh sach friend: ", friends.data.data.items);
          setFriends(friends.data.data.items);
          // setFriends((prevFriends) => [...friends.data.data.items, ...prevFriends]);
        } catch (error) {
          console.error(error);
        }
      }
    };
    getListFriends();
  }, [isNewMessage]);

  //token
  const token = localStorage.getItem("token");
  const tokenDetail = jwtDecode(token);
  console.log("token Detail: ", tokenDetail);

  // get params
  const params = useParams();
  // const [tempParams, setTempParams] = React.useState(params.idChat);
  // if (params !== tempParams) {
  //   setTempParams(params);
  //   window.location.reload();
  // }

  const [tempIdChatParams, setTempIdChatParams] = React.useState(
    params.idChat === "0e9d4087-37a6-4f33-9ba3-cb58ffe424f2"
      ? null
      : params.idChat
  );
  const [tempIdChatProfileParams, setTempIdChatProfileParams] =
    React.useState("");
  React.useEffect(() => {
    console.log("day la params", params);
    if (params.idChat === tokenDetail.idChatProfile) {
      console.log("chua chon friend de chat");
    } else if (params.idChat === "0e9d4087-37a6-4f33-9ba3-cb58ffe424f2") {
      setTempIdChatParams(null);
      getProfile({ token: token, idProfile: params.idProfile }).then((res) => {
        console.log("get profile cua friend khi chua co idChat", res.data);
        setTempFriend(res.data);
        setTempIdChatProfileParams(res.data.idChatProfile);
      });
    } else {
      setTempIdChatParams(params.idChat);
      getProfile({ token: token, idProfile: params.idProfile }).then((res) => {
        console.log("get profile cua friend da co Id chat", res.data);
        setTempFriend(res.data);
        setTempIdChatProfileParams(res.data.idChatProfile);
      });
    }
  }, [params]);

  const navigate = useNavigate();

  const handleClickFriend = (friend) => {
    setTempFriend(friend);

    readMessage({
      token: token,
      idChatProfile: tokenDetail.idChatProfile,
      idChat: friend.idChat,
    }).then((res) => {
      navigate(`/chat/${friend.idProfile}/${friend.idChat}`);
      window.location.reload();
    });
  };

  // handle search
  const [listUser, setListUser] = React.useState([]);
  const handleSeachUser = (e) => {
    searchUser({ token: token, name: e.target.value }).then((res) => {
      console.log("list user: ", res.data.items);
      setListUser(res.data.items);
    });
  };

  // test
  React.useEffect(() => {
    const friendToClick = friends.find(
      (friend) =>
        params.idChat === "0e9d4087-37a6-4f33-9ba3-cb58ffe424f2" &&
        params.idProfile === friend.idProfile
    );
    if (friendToClick) {
      handleClickFriend(friendToClick);
    }
  }, [friends, params, handleClickFriend]);

  return (
    <React.Fragment>
      <div
        className={`ml-6 col-span-3 col-start-2 border-r border-[#dbdbdb] h-[${checkHeight}px]`}
      >
        <div class="pr-10">
          <div className="h-36">
            <div class="flex items-center justify-between pb-[12px] pt-[36px]">
              <span class="text-[20px] font-medium">_Pbat</span>

              <Button
                variant="outlined"
                onClick={handleClickOpen}
                sx={{
                  fontWeight: "600",
                  border: "none",
                  lineHeight: "0",
                  letterSpacing: "0px",
                  textTransform: "none",
                  minWidth: "0",
                  padding: "0",
                  color: "black",
                  ":hover": {
                    border: "none",
                    padding: "0",
                  },
                }}
              >
                <svg
                  aria-label="New message"
                  class="x1lliihq x1n2onr6 x5n08af"
                  fill="currentColor"
                  height="24"
                  role="img"
                  viewBox="0 0 24 24"
                  width="24"
                >
                  <title>New message</title>
                  <path
                    d="M12.202 3.203H5.25a3 3 0 0 0-3 3V18.75a3 3 0 0 0 3 3h12.547a3 3 0 0 0 3-3v-6.952"
                    fill="none"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                  ></path>
                  <path
                    d="M10.002 17.226H6.774v-3.228L18.607 2.165a1.417 1.417 0 0 1 2.004 0l1.224 1.225a1.417 1.417 0 0 1 0 2.004Z"
                    fill="none"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                  ></path>
                  <line
                    fill="none"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    x1="16.848"
                    x2="20.076"
                    y1="3.924"
                    y2="7.153"
                  ></line>
                </svg>
              </Button>
            </div>
            <div class="flex items-center justify-between mb-4">
              <span class="text-[16px] font-medium">Messages</span>
              <span class="text-[14px]">Requests</span>
            </div>
          </div>

          <div id="divFriend" className={`w-full overflow-auto`}>
            {friends.map((friend, index) => {
              const notificationDate = new Date(friend.lastUsed); // Thay 'timestamp' bằng tên thuộc tính thực tế của bạn
              let timeAgo = formatDistanceToNow(notificationDate);
              if (timeAgo === "less than a minute") {
                timeAgo = "1 minute";
              } else if (timeAgo.startsWith("about ")) {
                timeAgo = timeAgo.replace("about ", "");
              }
              return (
                <div
                  key={index}
                  onClick={() => handleClickFriend(friend)}
                  class="cursor-pointer hover:bg-slate-50"
                >
                  <div class="flex items-center py-[8px] gap-x-4">
                    <div className="relative">
                      <img
                        class="w-[56px] h-[56px] object-cover rounded-[50%]"
                        src={friend.urlImageChatRoom}
                        alt=""
                      />
                      {friend.status === "ONLINE" ? (
                        <div className="w-3 h-3 bg-green-600 rounded-[50%] absolute bottom-1 right-1"></div>
                      ) : (
                        ""
                      )}
                    </div>
                    <div class="flex flex-col  w-[70%]">
                      <span
                        class={`text-[14px] ${
                          friend.isChecked ? "" : "font-semibold"
                        }`}
                      >
                        {friend.chatName}
                      </span>

                      <div className="flex items-center justify-between w-[100%]">
                        <span
                          className={`text-[12px] w-[60%] truncate ${
                            friend.isChecked ? "" : "font-semibold"
                          }`}
                        >
                          {friend.lastMessage}
                        </span>
                        <span className="text-xs">{timeAgo}</span>
                      </div>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        <Dialog
          open={open}
          onClose={handleClose}
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
            <div className="w-[548px] h-[418px] bg-[#fff]">
              <div className="relative py-4 border-b px-6">
                <div className="text-center">
                  <span className="text-[16px] font-bold">New message</span>
                </div>
                <div
                  onClick={handleClose}
                  className="absolute right-6 top-5 cursor-pointer"
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
              <div className="py-2 border-b px-6 flex items-center gap-x-4">
                <div>
                  <span className="text-[16px] font-medium">To:</span>
                </div>
                <form>
                  <input
                    type="text"
                    className="border-none outline-none placeholder:text-[14px]"
                    placeholder="Search..."
                    onChange={handleSeachUser}
                  ></input>
                </form>
              </div>
              <div className="mt-2">
                {console.log("return list user sau khi search", listUser)}
                {listUser.map((user, index) => {
                  return (
                    <div
                      key={index}
                      className="flex items-center gap-x-3 px-6 py-2 hover:bg-slate-100 cursor-pointer"
                    >
                      <div>
                        <img
                          className="w-[44px] h-[44px] rounded-[50%] object-cover"
                          src={user.urlAvt}
                        />
                      </div>
                      <div className="flex flex-col">
                        <span className="text-[14px]">{user.fullName}</span>
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          </DialogContent>
        </Dialog>
      </div>
      {params.idChat === tokenDetail.idChatProfile ? (
        <DefaultGroupChat />
      ) : (
        <GroupChat
          idDefault={tokenDetail.idChatProfile}
          tempIdChat={tempIdChatParams}
          tempFriend={tempFriend}
          tempIdChatProfileParams={tempIdChatProfileParams}
        />
      )}
    </React.Fragment>
  );
}

export default Chat;
