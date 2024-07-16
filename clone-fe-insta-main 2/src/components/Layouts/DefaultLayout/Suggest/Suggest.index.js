import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import { MyContext } from "..";
import { CustomTooltip } from "../../../../pages/Home/Post";
import Thumb from "../../../../pages/Home/Thumb";
import SuggestItem from "./SuggestItem";
import { jwtDecode } from "jwt-decode";
import { getAllFriend, getProfile, login } from "../../../../api/AccountAPI";
import { Link, useNavigate } from "react-router-dom";
import { Alert, Snackbar } from "@mui/material";

function Suggest() {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  // checkDivWidth
  const targetRef = React.useRef(null);
  const { sharedWidth } = React.useContext(MyContext);
  const checkDivWidth = () => {
    const targetDiv = targetRef.current;
    console.log("targetDiv in Suggest:", targetDiv?.offsetWidth);
    console.log("sharedWidth in Suggest:", sharedWidth);
    if (targetDiv && sharedWidth < 540) {
      targetDiv.classList.add("hidden");
    } else if (targetDiv) {
      targetDiv.classList.remove("hidden");
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
  }, [sharedWidth]); // Phải có sharedWidth ở đây để useEffect lắng nghe sự thay đổi của nó

  ////

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

  // get token
  const token = localStorage.getItem("token");
  const tokenDetail = jwtDecode(token);
  console.log("token", tokenDetail);

  // get my profile
  const [myProfile, setMyProfile] = React.useState();
  React.useEffect(() => {
    getProfile({ token: token, idProfile: tokenDetail.sub }).then((res) => {
      console.log("sau khi get my profile: ", res.data);
      setMyProfile(res.data);
    });
  }, []);

  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");

  const navigate = useNavigate();

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

  const handleLogin = () => {
    login({ username: username, password: password })
      .then((res) => {
        console.log("return sau khi login", res.data.data.token);
        localStorage.setItem("token", res.data.data.token);
        handleClose();
        setPopupStatus("success");
        setPopupContent("Login successful");
        handleClickPopup();
        setTimeout(() => {
          window.location.reload();
        }, 2000);
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("Incorrect username or password");
        handleClickPopup();
      });
  };

  // get all friend
  const [listFriend, setListFriend] = React.useState([]);
  React.useEffect(() => {
    getAllFriend({
      token: token,
      idChatProfile: tokenDetail.idChatProfile,
    }).then((res) => {
      console.log("list friend", res.data.items);
      setListFriend(res.data.items);
    });
  }, []);

  //   an hien pass
  const [isOpenNewPass, setIsOpenNewPass] = React.useState(false);
  const targetNewPass = document.getElementById("newPass");

  const handleShowNewPass = () => {
    setIsOpenNewPass(!isOpenNewPass);
    if (isOpenNewPass === false) {
      targetNewPass.type = "text";
    } else {
      targetNewPass.type = "password";
    }
  };

  return (
    <div ref={targetRef} className="col-start-8 col-span-3 mr-3 fixed right-0">
      <div className="container w-[300px] h-[500px] pl-[16px] mt-[40px]">
        <div className="flex items-center gap-x-[12px] pr-[16px]">
          <Link
            to={`/profile/${myProfile ? myProfile.idProfile : ""}`}
            className="cursor-pointer"
          >
            <img
              src={myProfile ? myProfile.urlProfilePicture : ""}
              alt="asd"
              className="w-[44px] h-[44px] rounded-[50%] object-cover"
            />
          </Link>
          <Link
            to={`/profile/${myProfile ? myProfile.idProfile : ""}`}
            className="flex flex-col"
          >
            <span className="text-[14px] font-semibold cursor-pointer">
              {myProfile ? myProfile.fullName : ""}
            </span>
          </Link>
          <Button
            variant="outlined"
            onClick={handleClickOpen}
            sx={{
              marginLeft: "auto",
              fontSize: "12px",
              color: "#0095f6",
              fontWeight: "600",
              border: "none",
              padding: "0",
              lineHeight: "0",
              letterSpacing: "0px",
              textTransform: "none",
              minWidth: "0",
              ":hover": {
                border: "none",
                color: "#00376b",
              },
            }}
          >
            Switch
          </Button>
        </div>
        <div className="flex mt-[24px] justify-between pr-[16px]">
          <p className="text-[14px] text-[#737373]">Người liên hệ</p>
          <span className="text-[12px] font-medium cursor-pointer hover:text-[#b5b5b5]">
            See All
          </span>
        </div>
        <div
          id="divFriend"
          className="my-[15px] flex flex-col gap-y-[14px] overflow-auto pr-1"
        >
          {listFriend.map((itemFriend, index) => {
            return (
              <SuggestItem key={index} itemFriend={itemFriend} token={token} />
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
          <div className="flex items-center justify-center">
            <div className="h-[411px] w-[400px] bg-white p-[12px] ">
              <div className="flex justify-end">
                <svg
                  aria-label="Close"
                  className="x1lliihq x1n2onr6 x5n08af cursor-pointer"
                  onClick={handleClose}
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
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="3"
                  ></polyline>
                  <line
                    fill="none"
                    stroke="currentColor"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="3"
                    x1="20.649"
                    x2="3.354"
                    y1="20.649"
                    y2="3.354"
                  ></line>
                </svg>
              </div>
              <div className="mt-14 flex justify-center">
                <i
                  data-visualcompletion="css-img"
                  aria-label="Instagram"
                  className=""
                  role="img"
                  style={{
                    backgroundImage:
                      'url("https://static.cdninstagram.com/rsrc.php/v3/yM/r/8n91YnfPq0s.png")',
                    backgroundPosition: "0px -52px",
                    backgroundSize: "auto",
                    width: "175px",
                    height: "51px",
                    backgroundRepeat: "no-repeat",
                    display: "inline-block",
                  }}
                ></i>
              </div>
              <form>
                <div className="mt-6 flex flex-col items-center gap-y-[8px]">
                  <input
                    onChange={(e) => setUsername(e.target.value)}
                    type="text"
                    className="h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#f5f5f5] p-[8px] pb-[10px] text-[12px] placeholder:text-[12px] placeholder:text-[#737373]"
                    placeholder="Username"
                  />
                  <div className="relative">
                    <input
                      id="newPass"
                      onChange={(e) => setPassword(e.target.value)}
                      type="password"
                      className="h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#f5f5f5] p-[8px] pb-[10px] text-[12px] placeholder:text-[12px] placeholder:text-[#737373]"
                      placeholder="Password"
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
                </div>
                <span
                  onClick={handleLogin}
                  className="flex justify-center items-center mt-[35px] w-[260px] h-[32px] bg-[#4cb5f9] ml-[50%] translate-x-[-50%] cursor-pointer text-[#e2f3fe] font-medium hover:bg-[#1877f2] hover:text-[#f0f8f6] rounded-[8px]"
                >
                  Log in
                </span>
              </form>
              <span className="block text-[12px] text-[#00376b] mt-[20px] w-[260px] ml-[50%] translate-x-[-50%] text-center cursor-pointer">
                Forgot password?
              </span>
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

export default Suggest;
