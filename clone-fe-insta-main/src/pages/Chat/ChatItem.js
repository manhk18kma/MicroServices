function ChatItem({ left, content }) {
  return (
    <div className="pt-4 flex items-center gap-x-2">
      <div className={left === true ? "" : "hidden"}>
        <img
          className="w-[28px] h-[28px] rounded-[50%] object-cover"
          src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          alt=""
        />
      </div>
      <div className={left === true ? "" : "ml-auto"}>
        <span className="px-3 py-1 bg-[#efefef] rounded-[999px]">{content}</span>
      </div>
    </div>
  );
}

export default ChatItem;
