function ReplyComment({handleShowReply}) {
  return (
    <div className="mt-4">
      <span onClick={handleShowReply} className="block relative text-[12px] ml-11 text-[#737373] font-medium before:block before:content-[''] before:w-10 before:h-[1px] before:bg-slate-500 before:absolute before:left-[-2.75rem] before:top-[50%] before:cursor-default cursor-pointer">
        Hide reples
      </span>
      <div className="flex items-center mr-2 cursor-pointer mt-3">
        <div className="col-start-1 col-span-1 w-[42px] h-[42px] flex items-center justify-center mr-2 cursor-pointer">
          <img
            class="w-[32px] h-[32px] rounded-[50%] object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          />
        </div>
        <div>
          <div className="mt-1">
            <span className="inline-block font-medium mr-2">_Pbat</span>
            <span>dhaskdhgsadha</span>
          </div>
          <div className="flex items-center gap-x-3 mt-1 text-[12px]">
            <span>2h</span>
            <span className="font-medium text-[#737373] cursor-pointer">
              164 likes
            </span>
            <span className="font-medium text-[#737373] cursor-pointer">
              Reply
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
export default ReplyComment;
