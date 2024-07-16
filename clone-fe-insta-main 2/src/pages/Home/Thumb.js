import { Link } from "react-router-dom";

function Thumb({ itemFriend, profile, listImages, isImages }) {
  if (itemFriend.idChat === null) {
    itemFriend.idChat = "0e9d4087-37a6-4f33-9ba3-cb58ffe424f2";
  }

  return (
    <div className="py-4 bg-white w-96 text-black rounded-lg shadow-2xl">
      <div className="flex items-center gap-x-4 px-3">
        <Link to="/profile">
          <img
            className="w-[60px] h-[60px] rounded-[50%] object-cover"
            src={itemFriend.urlProfilePicture}
          />
        </Link>
        <div className="flex flex-col">
          <Link to="/profile" className="text-[14px] font-medium">
            {itemFriend.fullName}
          </Link>
        </div>
      </div>
      <div className="flex items-center justify-between py-4 px-10">
        <div className="flex flex-col items-center ">
          <span className="text-sm font-semibold">91</span>
          <span className="text-xs font-normal">Posts</span>
        </div>
        <div className="flex flex-col items-center">
          <span className="text-sm font-semibold">
            {profile.countFollowers}
          </span>
          <span className="text-xs font-normal">Follower</span>
        </div>
        <div className="flex flex-col items-center">
          <span className="text-sm font-semibold">
            {profile.countFollowings}
          </span>
          <span className="text-xs font-normal">Following</span>
        </div>
      </div>
      <div>
        <div className="grid grid-cols-3 gap-x-1">
          {console.log("day la 3 thumb", listImages)}
          {isImages
            ? listImages.map((info, index) => {
                {
                  console.log("day la cac anh", info.images[0]);
                }

                return (
                  <img
                    key={index}
                    className="aspect-square object-cover"
                    src={info.images[0]}
                  />
                );
              })
            : ""}
        </div>
      </div>
      <div>
        <div className="pt-4 px-3 grid grid-cols-2 gap-x-2">
          <Link
            to={`/chat/${itemFriend.idProfile}/${itemFriend.idChat}`}
            className="flex items-cente justify-center gap-x-2 py-2 bg-[#3baef8] rounded-lg cursor-pointer"
          >
            <div className="text-white">
              <svg
                aria-label="Direct"
                class="x1lliihq x1n2onr6 x5n08af"
                fill="currentColor"
                height="20"
                role="img"
                viewBox="0 0 24 24"
                width="20"
              >
                <title>Direct</title>
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
            <div>
              <span className="text-white text-sm">Message</span>
            </div>
          </Link>

          <div className="flex items-cente justify-center gap-x-2 py-2 bg-[#dedede] rounded-lg cursor-pointer">
            <div>
              <span className="text-sm">Following</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Thumb;
