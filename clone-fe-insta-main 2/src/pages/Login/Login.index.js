import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../../api/AccountAPI";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate()
  const handleLogin = () => {
    login({ username, password })
      .then((response) => {
        console.log(response)
        console.log("Login success");
        localStorage.setItem("token", response.data.data.token);
        navigate("/")
      })
      .catch((error) => {
        console.log("Login failed");
        console.error(error);
        navigate("/login")
      });
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
                placeholder="Phone number, username or email"
              />
              <input
                type="text"
                onChange={(e) => setPassword(e.target.value)}
                className="h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#f5f5f5] p-[8px] pb-[10px] text-[12px] placeholder:text-[12px] placeholder:text-[#737373] focus:outline-none focus:border"
                placeholder="Password"
              />
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
          <div className="flex items-center justify-center gap-x-2 text-[14px] my-4">
            <span>Don't have an account?</span>
            <Link to="/register" className="font-medium text-[#0095f6]">
              Sign up
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
