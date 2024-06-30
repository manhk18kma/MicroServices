import { CustomTooltip } from "../../../../pages/Home/Post";
import Thumb from "../../../../pages/Home/Thumb";

function SuggestItem() {
  return (
    <div className="flex items-center gap-x-[12px]">
      <div className="cursor-pointer">
        <CustomTooltip title={<Thumb />} placement="bottom">
          <div>
            <img
              src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              alt="asd"
              className="w-[44px] h-[44px] rounded-[50%] object-cover"
            />
          </div>
        </CustomTooltip>
      </div>
      <div className="flex flex-col">
        <CustomTooltip title={<Thumb />} placement="bottom-end">
          <div>
            <span className="text-[14px] font-semibold cursor-pointer">
              _Pbat
            </span>
          </div>
        </CustomTooltip>
        <span className="text-[14px] text-[#737373]">Suggested for you</span>
      </div>
      <button className="ml-auto text-[12px] text-[#0095f6] font-medium">
        Follow
      </button>
    </div>
  );
}

export default SuggestItem;
