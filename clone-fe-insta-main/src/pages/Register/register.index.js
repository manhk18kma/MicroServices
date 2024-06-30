import { TextField } from "@mui/material";
import { useState } from "react";
import DatePicker from "react-date-picker";
import { Link, useNavigate } from "react-router-dom";
import { createAccount } from "../../api/AccountAPI";

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

  const handleDateChange = (date) => {
    setDateValue(date);
  };

  const navigate = useNavigate()

  // create user
  const handleCreateUser = () => {
    console.log("Gioi tinh", selectedGender)
    createAccount({
      username,
      password,
      email,
      fullName,
      selectedGender,
      dateValue,
    })
      .then((response) => {
        navigate("/login")
      })
      .catch((error) => {
        console.error(error);
      });
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
              <input
                type="text"
                className="focus:outline-none rounded-sm h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                placeholder="Password"
                onChange={(e) => setPassword(e.target.value)}
              />
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
                <DatePicker
                  dayPlaceholder="dd"
                  monthPlaceholder="mm"
                  yearPlaceholder="yyyy"
                  onChange={handleDateChange}
                  value={dateValue}
                />
              </div>
            </div>
            <span
              onClick={handleCreateUser}
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
    </div>
  );
}

export default Register;
