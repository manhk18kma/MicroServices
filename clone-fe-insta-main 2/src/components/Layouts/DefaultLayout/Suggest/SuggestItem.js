import { useEffect, useState } from "react";
import { CustomTooltip } from "../../../../pages/Home/Post";
import Thumb from "../../../../pages/Home/Thumb";
import { getProfile } from "../../../../api/AccountAPI";
import { getListImage } from "../../../../api/PostAPI";

function SuggestItem({ itemFriend, token }) {
  const [profile, setProfile] = useState();
  useEffect(() => {
    getProfile({ token: token, idProfile: itemFriend.idProfile }).then(
      (res) => {
        console.log("Profile trong suggest", res.data);
        setProfile(res.data);
      }
    );
  }, []);

  const [threeImages, setThreeImages] = useState()
  const [isImages, setIsImages] = useState(true)
  useEffect(() => {
    getListImage({
      token: token,
      idProfile: itemFriend.idProfile,
      pageNo: 0,
      pageSize: 10,
    }).then((res) => {
      console.log("danh sach image trong suggest", res.data.items)
      const images = res.data.items
      if (images.length === 0){
        setIsImages(false)
      }else if(images.length <= 3){
        setThreeImages(images)
      }else{
        setThreeImages(images.slice(0,3))
      }
    })
  }, []);

  return (
    <CustomTooltip
      title={<Thumb isImages={isImages} itemFriend={itemFriend} profile={profile} listImages={threeImages}/>}
      placement="left-end"
    >
      <div className="flex items-center gap-x-[12px] cursor-pointer hover:bg-slate-100">
        <div className="cursor-pointer">
          <div className="relative">
            <img
              src={itemFriend.urlProfilePicture}
              alt="asd"
              className="w-[44px] h-[44px] rounded-[50%] object-cover"
            />
            {itemFriend.status === "ONLINE" ? (
              <div className="w-3 h-3 bg-green-600 rounded-[50%] absolute bottom-0 right-0"></div>
            ) : (
              ""
            )}
          </div>
        </div>
        <div className="flex flex-col">
          <div>
            <span className="text-base font-semibold cursor-pointer">
              {itemFriend.fullName}
            </span>
          </div>
        </div>
      </div>
    </CustomTooltip>
  );
}

export default SuggestItem;
