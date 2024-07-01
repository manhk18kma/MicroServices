import { Button, Dialog, DialogContent } from "@mui/material";
import * as React from "react";

function DefaultGroupChat() {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <div className="col-start-5 col-span-8 flex items-center justify-center">
      <div className="flex flex-col items-center">
        <div className="mb-4">
          <svg
            aria-label=""
            class="x1lliihq x1n2onr6 x5n08af "
            fill="currentColor"
            height="96"
            role="img"
            viewBox="0 0 96 96"
            width="96"
          >
            <title></title>
            <path d="M48 0C21.532 0 0 21.533 0 48s21.532 48 48 48 48-21.532 48-48S74.468 0 48 0Zm0 94C22.636 94 2 73.364 2 48S22.636 2 48 2s46 20.636 46 46-20.636 46-46 46Zm12.227-53.284-7.257 5.507c-.49.37-1.166.375-1.661.005l-5.373-4.031a3.453 3.453 0 0 0-4.989.921l-6.756 10.718c-.653 1.027.615 2.189 1.582 1.453l7.257-5.507a1.382 1.382 0 0 1 1.661-.005l5.373 4.031a3.453 3.453 0 0 0 4.989-.92l6.756-10.719c.653-1.027-.615-2.189-1.582-1.453ZM48 25c-12.958 0-23 9.492-23 22.31 0 6.706 2.749 12.5 7.224 16.503.375.338.602.806.62 1.31l.125 4.091a1.845 1.845 0 0 0 2.582 1.629l4.563-2.013a1.844 1.844 0 0 1 1.227-.093c2.096.579 4.331.884 6.659.884 12.958 0 23-9.491 23-22.31S60.958 25 48 25Zm0 42.621c-2.114 0-4.175-.273-6.133-.813a3.834 3.834 0 0 0-2.56.192l-4.346 1.917-.118-3.867a3.833 3.833 0 0 0-1.286-2.727C29.33 58.54 27 53.209 27 47.31 27 35.73 36.028 27 48 27s21 8.73 21 20.31-9.028 20.31-21 20.31Z"></path>
          </svg>
        </div>
        <div className="flex flex-col items-center mb-4">
          <span className="text-[20px]">Your messages</span>
          <span className="text-[14px] text-[#737373]">
            Send a message to start a chat.
          </span>
        </div>
        <Button
          variant="outlined"
          onClick={handleClickOpen}
          sx={{
            textAlign: "center",
            fontSize: "14px",
            color: "#fff",
            fontWeight: "600",
            border: "none",
            padding: "16px 20px",
            lineHeight: "0",
            letterSpacing: "0px",
            textTransform: "none",
            minWidth: "0",
            backgroundColor: "#0095f6",
            borderRadius: "8px",
            ":hover": {
              border: "none",
              backgroundColor: "#1877f2",
            },
          }}
        >
          Send message
        </Button>
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
                ></input>
              </form>
            </div>
            <div className="mt-2">
              <div className="flex items-center gap-x-3 px-6 py-2 hover:bg-slate-100 cursor-pointer">
                <div>
                  <img
                    className="w-[44px] h-[44px] rounded-[50%] object-cover"
                    src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
                  />
                </div>
                <div className="flex flex-col">
                  <span className="text-[14px]">The</span>
                  <span className="text-[14px]">_Pbat</span>
                </div>
              </div>
            </div>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}

export default DefaultGroupChat;
