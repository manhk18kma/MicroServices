function ChatItem({ left, messageItem }) {
  return (
    <div className="pt-4 flex items-center gap-x-2">
      <div className={left === true ? "" : "hidden"}>
        <img
          className="w-[28px] h-[28px] rounded-[50%] object-cover"
          src={messageItem.urlAvtSender}
          alt=""
        />
      </div>
      <div className={left === true ? "" : "ml-auto"}>
        <span className="px-3 py-1 bg-[#efefef] rounded-[999px]">{messageItem.content}</span>
      </div>
    </div>
  );
}

export default ChatItem;
