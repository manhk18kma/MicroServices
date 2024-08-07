import { useNavigate } from "react-router-dom";
import { checkToken } from "../../../api/AccountAPI";
import * as React from "react";
import SideBar from "../DefaultLayout/SideBar/SideBar.index";

export const MyContextMessage = React.createContext();

function MessageLayout({ children }) {
  // account exist
  const [accountExist, setAccountExist] = React.useState(false);
  const navigate = useNavigate();
  React.useEffect(() => {
    const token = localStorage.getItem("token");
    checkToken({ token })
      .then((res) => {
        if (res.data.valid === true) {
          setAccountExist(true);
        } else {
          // navigate("/login");
          console.log(res);
        }
      })
      .catch((error) => {
        console.log(error);
        // navigate("/login");
      });
  }, []);

  // check new message
  const [isNewMessage, setIsNewMessage] = React.useState(false);

  return (
    <MyContextMessage.Provider value={{ isNewMessage, setIsNewMessage }}>
      {accountExist === false ? (
        <div className="grid grid-cols-12 gap-x-2">
          <SideBar />
          {children}
        </div>
      ) : (
        <div className="flex items-center justify-center w-screen h-screen">
          <div>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 16 16"
              id="instagram"
              className="w-20 h-20 object-cover"
            >
              <linearGradient
                id="a"
                x1="1.464"
                x2="14.536"
                y1="14.536"
                y2="1.464"
                gradientUnits="userSpaceOnUse"
              >
                <stop offset="0" stop-color="#FFC107"></stop>
                <stop offset=".507" stop-color="#F44336"></stop>
                <stop offset=".99" stop-color="#9C27B0"></stop>
              </linearGradient>
              <path
                fill="url(#a)"
                d="M11 0H5a5 5 0 0 0-5 5v6a5 5 0 0 0 5 5h6a5 5 0 0 0 5-5V5a5 5 0 0 0-5-5zm3.5 11c0 1.93-1.57 3.5-3.5 3.5H5c-1.93 0-3.5-1.57-3.5-3.5V5c0-1.93 1.57-3.5 3.5-3.5h6c1.93 0 3.5 1.57 3.5 3.5v6z"
              ></path>
              <linearGradient
                id="b"
                x1="5.172"
                x2="10.828"
                y1="10.828"
                y2="5.172"
                gradientUnits="userSpaceOnUse"
              >
                <stop offset="0" stop-color="#FFC107"></stop>
                <stop offset=".507" stop-color="#F44336"></stop>
                <stop offset=".99" stop-color="#9C27B0"></stop>
              </linearGradient>
              <path
                fill="url(#b)"
                d="M8 4a4 4 0 1 0 0 8 4 4 0 0 0 0-8zm0 6.5A2.503 2.503 0 0 1 5.5 8c0-1.379 1.122-2.5 2.5-2.5s2.5 1.121 2.5 2.5c0 1.378-1.122 2.5-2.5 2.5z"
              ></path>
              <linearGradient
                id="c"
                x1="11.923"
                x2="12.677"
                y1="4.077"
                y2="3.323"
                gradientUnits="userSpaceOnUse"
              >
                <stop offset="0" stop-color="#FFC107"></stop>
                <stop offset=".507" stop-color="#F44336"></stop>
                <stop offset=".99" stop-color="#9C27B0"></stop>
              </linearGradient>
              <circle cx="12.3" cy="3.7" r=".533" fill="url(#c)"></circle>
            </svg>
          </div>
        </div>
      )}
    </MyContextMessage.Provider>
  );
}

export default MessageLayout;
