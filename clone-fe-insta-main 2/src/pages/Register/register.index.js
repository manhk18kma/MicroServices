import {
  Alert,
  Dialog,
  DialogContent,
  Snackbar,
  TextField,
} from "@mui/material";
import { useState } from "react";
import DatePicker from "react-date-picker";
import { Link, useNavigate } from "react-router-dom";
import { activation, createAccount } from "../../api/AccountAPI";
import * as React from "react";
import OtpInput from "react-otp-input";
import otpStyle from "./index.css";

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [fullName, setFullName] = useState("");

  // gender
  const [selectedGender, setSelectedGender] = useState("");

  const handleGenderChange = (event) => {
    console.log(event.target.value); // M or F
    setSelectedGender(event.target.value);
  };

  // date
  const [dateValue, setDateValue] = useState(new Date());

  const handleDateChange = (e) => {
    console.log("dateeee: ", e.target.value);
    setDateValue(e.target.value);
  };

  const navigate = useNavigate();

  // otp dialog
  const [openOtp, setOpenOtp] = React.useState(false);

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

  const handleClickOpenOtp = () => {
    setOpenOtp(true);
    createAccount({
      username,
      password,
      email,
      fullName,
      selectedGender,
      dateValue,
    })
      .then((response) => {
        setPopupStatus("success");
        setPopupContent(
          "Account registration successful, please verify the OTP"
        );
        handleClickPopup();
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("Account registration failed");
        handleClickPopup();
      });
  };

  const handleCloseOtp = () => {
    setOpenOtp(false);
  };

  // otp
  const [otp, setOtp] = useState("");

  const handleSendOtp = () => {
    activation({ otp: otp, email: email })
      .then((res) => {
        setPopupStatus("success");
        setPopupContent("OTP verification successful");
        handleClickPopup();
        setTimeout(() => {
          navigate("/login");
        }, 2000);
      })
      .catch((error) => {
        setPopupStatus("warning");
        setPopupContent("OTP does not match, please check again");
        handleClickPopup();
      });
  };

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
        <div className=" bg-white p-6 ">
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
                className="focus:outline-none rounded-sm h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                placeholder="Username"
                onChange={(e) => setUsername(e.target.value)}
              />
              <div className="relative">
                <input
                  type="password"
                  id="newPass"
                  className="focus:outline-none rounded-sm h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                  placeholder="Password"
                  onChange={(e) => setPassword(e.target.value)}
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
              <input
                type="text"
                className="focus:outline-none rounded-sm h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                placeholder="Email"
                onChange={(e) => setEmail(e.target.value)}
              />
              <input
                type="text"
                className="focus:outline-none rounded-sm h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                placeholder="Full Name"
                onChange={(e) => setFullName(e.target.value)}
              />
              <div className="h-[38px] w-[260px] bg-[#fffefe]">
                <select
                  id="gender"
                  value={selectedGender}
                  onChange={handleGenderChange}
                  class=" border border-gray-300 text-gray-900 text-sm rounded-sm focus:border-[#dbdbdb] block w-full h-full"
                >
                  <option selected>Gender</option>
                  <option value="male">Male</option>
                  <option value="female">Female</option>
                </select>
              </div>

              <div className="h-[38px] w-[260px] bg-[#fffefe]">
                <input
                  type="date"
                  className="w-full border-[1px] h-[38px]"
                  value={dateValue} //"2021-12-14"
                  onChange={handleDateChange}
                />
              </div>
            </div>
            <span
              onClick={handleClickOpenOtp}
              className="flex justify-center items-center mt-[35px] w-[260px] h-[36px] bg-[#4cb5f9] ml-[50%] translate-x-[-50%] cursor-pointer text-[#e2f3fe] font-medium hover:bg-[#1877f2] hover:text-[#f0f8f6] rounded-[8px]"
            >
              Sign up
            </span>
          </form>
          <div className="flex items-center justify-center gap-x-2 text-[14px] mt-4">
            <span>Have an account?</span>
            <Link to="/login" className="font-medium text-[#0095f6]">
              Login
            </Link>
          </div>
        </div>
      </div>

      {/* Otp dialog */}
      <Dialog
        open={openOtp}
        onClose={handleCloseOtp}
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
          <div className="w-80 h-50 p-6">
            <div className="text-center">
              <span>Enter Verification Code</span>
            </div>
            <div className="w-full mt-6 flex justify-center">
              <input
                className="border-[1px]"
                onChange={(e) => setOtp(e.target.value)}
                type="text"
              />
            </div>
            <div className="mt-6 flex justify-center cursor-pointer">
              <span
                onClick={handleSendOtp}
                className="text-base font-medium px-4 py-2 bg-blue-300"
              >
                Send
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

export default Register;
