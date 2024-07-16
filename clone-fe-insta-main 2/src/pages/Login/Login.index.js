import { Link, useNavigate } from "react-router-dom";
import { login } from "../../api/AccountAPI";
import { Alert, Snackbar } from "@mui/material";
import * as React from "react";

function Login() {
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");

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

  const navigate = useNavigate();
  const handleLogin = () => {
    login({ username, password })
      .then((response) => {
        setPopupStatus("success");
        setPopupContent("Login successful");
        handleClickPopup();
        localStorage.setItem("token", response.data.data.token);
        setTimeout(() => {
          navigate("/");
        }, 1000);
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("The username or password is incorrect");
        handleClickPopup();
      });
  };

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
    <div className="w-screen h-screen flex items-center justify-center">
      <div className="flex items-center justify-center border-[1px] w-[400px] shadow-2xl">
        <div className=" bg-white p-6">
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
                type="text"
                onChange={(e) => setUsername(e.target.value)}
                className="h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#f5f5f5] p-[8px] pb-[10px] text-[12px] placeholder:text-[12px] placeholder:text-[#737373] focus:outline-none focus:border"
                placeholder="Username"
              />
              <div className="relative">
                <input
                  id="newPass"
                  type="password"
                  onChange={(e) => setPassword(e.target.value)}
                  className="h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#f5f5f5] p-[8px] pb-[10px] text-[12px] placeholder:text-[12px] placeholder:text-[#737373] focus:outline-none focus:border"
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
          <Link
            to={`/forgotPassword`}
            className="block text-[12px] text-[#00376b] mt-[20px] w-[260px] ml-[50%] translate-x-[-50%] text-center cursor-pointer"
          >
            Forgot password?
          </Link>
          <div className="flex items-center justify-center gap-x-2 text-[14px] my-4">
            <span>Don't have an account?</span>
            <Link to="/register" className="font-medium text-[#0095f6]">
              Sign up
            </Link>
          </div>
        </div>
      </div>

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

export default Login;
