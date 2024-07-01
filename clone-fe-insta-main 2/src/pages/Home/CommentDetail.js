import { useEffect, useState } from "react";
import ReplyComment from "./ReplyComment";
import { formatDistanceToNow } from "date-fns";

function CommentDetail({ comment, isTrue }) {
  const [isReply, setIsReply] = useState(false);
  const [showReply, setShowReply] = useState(false);

  const handleShowReply = () => {
    setShowReply(!showReply);
  };

  useEffect(() => {
    setIsReply(isTrue);
  })

  const notificationDate = new Date(comment.updateAt); // Thay 'timestamp' bằng tên thuộc tính thực tế của bạn
  const timeAgo = formatDistanceToNow(notificationDate, { addSuffix: true });
  

  return (
    <div className="mb-4 grid grid-cols-12 text-[14px]">
      <div className="col-start-1 col-span-1 w-[42px] h-[42px] flex items-center justify-center mr-2 cursor-pointer">
        <img
          class="w-[32px] h-[32px] rounded-[50%] object-cover"
          src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
        />
      </div>
      <div className="col-start-2 col-span-11 ml-2">
        <div className="mt-1">
          <span className="inline-block font-medium mr-2">_Pbat</span>
          <span>{comment.content}</span>
        </div>
        <div className="flex items-center gap-x-2 mt-1 text-[12px]">
          <span>{timeAgo}</span>
          {/* <span className="font-medium text-[#737373] cursor-pointer">
            164 likes
          </span>
          <span className="font-medium text-[#737373] cursor-pointer">
            Reply
          </span> */}
        </div>
        {isReply & (showReply === false) ? (
          <div className="mt-4">
            <span
              onClick={handleShowReply}
              className="block relative text-[12px] ml-11 text-[#737373] font-medium before:block before:content-[''] before:w-10 before:h-[1px] before:bg-slate-500 before:absolute before:left-[-2.75rem] before:top-[50%] before:cursor-default cursor-pointer"
            >
              View replies (2)
            </span>
          </div>
        ) : (
          ""
        )}
        {showReply ? (
            <ReplyComment handleShowReply={handleShowReply}/>
        ) : (
          ""
        )}
      </div>
    </div>
  );
}

export default CommentDetail;
