import { formatDistanceToNow } from 'date-fns';

function Following({ itemNotification }) {
  const notificationDate = new Date(itemNotification.timestamp); // Thay 'timestamp' bằng tên thuộc tính thực tế của bạn
  const timeAgo = formatDistanceToNow(notificationDate, { addSuffix: true });

  console.log("tung notification", itemNotification)
  return (
    <div className="grid grid-cols-12 cursor-pointer hover:bg-slate-100 py-2">
      <div className="col-start-1 col-span-2 w-12 h-12">
        <img
          className="w-12 h-12 rounded-[50%] object-cover"
          src={itemNotification.urlAvtSender}
        />
      </div>
      <div className="col-start-3 col-span-10">
        <span className="text-sm font-medium">  
          {itemNotification.message}
        </span>
        <span className="block mt-1 text-xs font-semibold">{timeAgo}</span>
      </div>
    </div>
  );
}

export default Following;
