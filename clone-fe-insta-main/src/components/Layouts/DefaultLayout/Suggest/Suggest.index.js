import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import { MyContext } from "..";
import { CustomTooltip } from "../../../../pages/Home/Post";
import Thumb from "../../../../pages/Home/Thumb";
import SuggestItem from "./SuggestItem";

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

  return (
    <div ref={targetRef} className="col-start-8 col-span-3 ml-auto mr-3">
      <div className="container w-[300px] h-[500px] px-[16px] mt-[40px]">
        <div className="flex items-center gap-x-[12px]">
          <div className="cursor-pointer">
            <img
              src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              alt="asd"
              className="w-[44px] h-[44px] rounded-[50%] object-cover"
            />
          </div>
          <div className="flex flex-col">
            <span className="text-[14px] font-semibold cursor-pointer">
              _Pbat
            </span>
            <span className="text-[14px] text-[#737373]">The</span>
          </div>
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
        <div className="flex mt-[24px] justify-between">
          <p className="text-[14px] text-[#737373]">Suggested for you</p>
          <span className="text-[12px] font-medium cursor-pointer hover:text-[#b5b5b5]">
            See All
          </span>
        </div>
        <div className="my-[15px] flex flex-col gap-y-[14px]">
          <SuggestItem />
          <SuggestItem />
          <SuggestItem />
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
              <form action="#">
                <div className="mt-6 flex flex-col items-center gap-y-[8px]">
                  <input
                    type="text"
                    className="h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#f5f5f5] p-[8px] pb-[10px] text-[12px] placeholder:text-[12px] placeholder:text-[#737373]"
                    placeholder="Phone number, username or email"
                  />
                  <input
                    type="text"
                    className="h-[38px] w-[260px] border-[1px] border-[#dbdbdb] bg-[#f5f5f5] p-[8px] pb-[10px] text-[12px] placeholder:text-[12px] placeholder:text-[#737373]"
                    placeholder="Password"
                  />
                </div>
                <span
                  onSubmit="#"
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
    </div>
  );
}

export default Suggest;
