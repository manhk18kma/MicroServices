import { Button, Dialog, DialogContent } from "@mui/material";
import * as React from "react";
import CommentDetail from "../Home/CommentDetail";
import DatePicker from "react-date-picker";
import styles from "./index.css";

function Profile() {
  const [openEditProfile, setOpenEditProfile] = React.useState(false);

  const handleClickOpenEditProfile = () => {
    setOpenEditProfile(true);
  };

  const handleCloseEditProfile = () => {
    setOpenEditProfile(false);
  };

  // Click image to view all comments
  // Comment Dialog
  const [openComment, setOpenComment] = React.useState(false);

  const handleClickOpenComment = () => {
    setOpenComment(true);
  };

  const handleCloseComment = () => {
    setOpenComment(false);
  };

  // like
  const [liked, setLiked] = React.useState(false);
  const [currentLike, setCurrentLike] = React.useState(0);
  const toggleLike = () => {
    setLiked(!liked);
    setCurrentLike(liked ? currentLike - 1 : currentLike + 1);
  };

  // Post
  // post
  const [isPost, setIsPost] = React.useState(false);
  const [inputContent, setInputContent] = React.useState("");
  const [tempInputContent, setTempInputContent] = React.useState("");

  const handleInputChange = (e) => {
    setIsPost(false);
    setInputContent(e.target.value);
    setTempInputContent(e.target.value);
  };

  const handlePost = () => {
    setIsPost(true);
    setTempInputContent("");
  };

  // edit profile
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [fullName, setFullName] = React.useState("");

  // gender
  const [selectedGender, setSelectedGender] = React.useState("");

  const handleGenderChange = (event) => {
    console.log(event.target.value); // M or F
    setSelectedGender(event.target.value);
  };

  // date
  const [dateValue, setDateValue] = React.useState(new Date());

  const handleDateChange = (e) => {
    console.log(new Date(e.target.value));
    setDateValue(e.target.value);
  };

  // file
  const fileInputRef = React.useRef(null);

  const handleClickFile = () => {
    fileInputRef.current.click();
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      console.log("Selected file:", file);
      // Thực hiện các xử lý khác với file ảnh ở đây
    }
  };

  return (
    <div className="col-start-3 col-span-8 p-8 flex justify-center">
      <div className="w-[930px]">
        <div className="flex items-center gap-x-20 pb-12 border-b">
          <div>
            <img
              className="w-[150px] h-[150px] object-cover rounded-[50%]"
              src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
            />
          </div>
          <div className="flex flex-col">
            <div className="flex items-center gap-x-4">
              <span className="text-[20px]">_Pbat</span>

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

              <div className="cursor-pointer">
                <svg
                  aria-label="Options"
                  class="x1lliihq x1n2onr6 x5n08af"
                  fill="currentColor"
                  height="24"
                  role="img"
                  viewBox="0 0 24 24"
                  width="24"
                >
                  <title>Options</title>
                  <circle
                    cx="12"
                    cy="12"
                    fill="none"
                    r="8.635"
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                  ></circle>
                  <path
                    d="M14.232 3.656a1.269 1.269 0 0 1-.796-.66L12.93 2h-1.86l-.505.996a1.269 1.269 0 0 1-.796.66m-.001 16.688a1.269 1.269 0 0 1 .796.66l.505.996h1.862l.505-.996a1.269 1.269 0 0 1 .796-.66M3.656 9.768a1.269 1.269 0 0 1-.66.796L2 11.07v1.862l.996.505a1.269 1.269 0 0 1 .66.796m16.688-.001a1.269 1.269 0 0 1 .66-.796L22 12.93v-1.86l-.996-.505a1.269 1.269 0 0 1-.66-.796M7.678 4.522a1.269 1.269 0 0 1-1.03.096l-1.06-.348L4.27 5.587l.348 1.062a1.269 1.269 0 0 1-.096 1.03m11.8 11.799a1.269 1.269 0 0 1 1.03-.096l1.06.348 1.318-1.317-.348-1.062a1.269 1.269 0 0 1 .096-1.03m-14.956.001a1.269 1.269 0 0 1 .096 1.03l-.348 1.06 1.317 1.318 1.062-.348a1.269 1.269 0 0 1 1.03.096m11.799-11.8a1.269 1.269 0 0 1-.096-1.03l.348-1.06-1.317-1.318-1.062.348a1.269 1.269 0 0 1-1.03-.096"
                    fill="none"
                    stroke="currentColor"
                    stroke-linejoin="round"
                    stroke-width="2"
                  ></path>
                </svg>
              </div>
            </div>
            <div className="flex items-center gap-x-10 mt-10">
              <span className="text-[16px]">
                <span className="font-medium">0</span> posts
              </span>
              <span>
                <span className="font-medium">19</span> follower
              </span>
              <span>
                <span className="font-medium">22</span> following
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
            {/* 1 */}
            <div className="aspect" onClick={handleClickOpenComment}>
              <img
                className="object-cover aspect-square"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>

            {/* 1 */}
            <div className="aspect" onClick={handleClickOpenComment}>
              <img
                className="object-cover aspect-square"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>

            {/* 1 */}
            <div className="aspect" onClick={handleClickOpenComment}>
              <img
                className="object-cover aspect-square"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>

            {/* 1 */}
            <div className="aspect">
              <img
                className="object-cover aspect-square"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>

            {/* 1 */}
            <div className="aspect">
              <img
                className="object-cover aspect-square"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>

            {/* 1 */}
            <div className="aspect">
              <img
                className="object-cover aspect-square"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>

            {/* 1 */}
            <div className="aspect">
              <img
                className="object-cover aspect-square"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>
          </div>
        </div>
      </div>

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
          <div className="w-[700px] h-[750px] p-8">
            <div className="flex items-center p-4 border-[1px] rounded-xl">
              <div>
                <img
                  className="w-[56px] h-[56px] rounded-[50%] object-cover"
                  src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
                />
              </div>
              <div className="flex flex-col ml-4">
                <span className="text-base font-medium">The</span>
                <span className="text-base">_Pbat</span>
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
                <span className="text-base font-semibold">Username</span>
                <input
                  type="text"
                  value={username}
                  className="focus:outline-none rounded-xl h-[38px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                  placeholder="Username"
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>

              <div className="flex flex-col gap-y-2">
                <span className="text-base font-semibold">Bio</span>
                <input
                  type="text"
                  value={"bio"}
                  className="focus:outline-none rounded-xl h-[38px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                  placeholder="Bio"
                  onChange={(e) => console.log(e.target.value)}
                />
              </div>

              <div className="flex flex-col gap-y-2">
                <span className="text-base font-semibold">Email</span>
                <input
                  type="text"
                  value={email}
                  className="focus:outline-none rounded-xl h-[38px] border-[1px] border-[#dbdbdb] bg-[#fffefe] p-[8px] pb-[10px] text-sm placeholder:text-sm placeholder:text-[#737373]"
                  placeholder="Email"
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>

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
                <span className="text-base font-semibold">Gender</span>
                <select
                  name="gender"
                  id="gender"
                  className="outline-none h-[38px] border-[1px] rounded-xl bg-[#fffefe] p-[8px] pb-[10px] text-sm"
                  value={"F"} // selected M or F
                  onChange={handleGenderChange}
                >
                  <option value="M">Male</option>
                  <option value="F">Female</option>
                </select>
              </div>

              <div className="flex flex-col gap-y-2">
                <span className="text-base font-semibold">Date Of Birth</span>
                <input
                  type="date"
                  className="w-full border-[1px] h-[38px]"
                  value={dateValue} //"2021-12-14"
                  onChange={handleDateChange}
                />
              </div>
            </div>
            <div className="flex items-center justify-center mt-6">
              <span className="px-28 py-3 bg-blue-500 rounded-xl cursor-pointer text-white font-semibold">
                Submit
              </span>
            </div>
          </div>
        </DialogContent>
      </Dialog>

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
              <img
                className="w-full h-full object-cover"
                src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              />
            </div>

            <div className="w-[55%] grid grid-rows-10 grid-cols-1">
              <div className="row-start-1 row-span-1 border-b">
                <div className="flex items-center p-2">
                  <div class="w-[42px] h-[42px] flex items-center justify-center mr-2 cursor-pointer">
                    <img
                      class="w-[32px] h-[32px] rounded-[50%] object-cover"
                      src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
                      alt="asd"
                    />
                  </div>
                  <div class="relative text-[14px] font-medium mr-4 cursor-pointer">
                    _Pbat
                  </div>
                  <span class="relative text-[14px] font-semibold text-[#0095f6] before:block before:content-[''] before:w-1 before:h-1 before:bg-slate-500 before:rounded-[50%] before:absolute before:left-[-10px] before:top-[45%] before:cursor-default cursor-pointer">
                    Follow
                  </span>
                  <div className="ml-auto cursor-pointer">
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
                </div>
              </div>

              {/* Comment */}
              <div className="p-2 row-start-2 row-span-6 w-full overflow-auto border-b">
                <CommentDetail
                  content={
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi placerat consectetur mi nec sagittis. Nam vulputate dui quis orci tempor pretium. Nullam turpis sem, lacinia non urna at"
                  }
                  isTrue={true}
                />
                <CommentDetail
                  content={"Sed sed tempus lorem"}
                  isTrue={false}
                />
                <CommentDetail content={"Sed sed tempus lorem"} isTrue={true} />
                <CommentDetail
                  content={"Sed sed tempus lorem"}
                  isTrue={false}
                />
                {isPost ? (
                  <CommentDetail content={inputContent} isTrue={false} />
                ) : (
                  ""
                )}
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
                    2 hours ago
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
                    value={tempInputContent}
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
    </div>
  );
}

export default Profile;
