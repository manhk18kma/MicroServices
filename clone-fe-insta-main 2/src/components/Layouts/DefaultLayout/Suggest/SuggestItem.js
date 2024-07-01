import { CustomTooltip } from "../../../../pages/Home/Post";
import Thumb from "../../../../pages/Home/Thumb";

function SuggestItem() {
  return (
    <CustomTooltip title={<Thumb />} placement="left-end">
      <div className="flex items-center gap-x-[12px] cursor-pointer hover:bg-slate-100">
        <div className="cursor-pointer">
          <div className="relative">
            <img
              src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
              alt="asd"
              className="w-[44px] h-[44px] rounded-[50%] object-cover"
            />
            <div className="w-3 h-3 bg-green-600 rounded-[50%] absolute bottom-0 right-0"></div>
          </div>
        </div>
        <div className="flex flex-col">
          <div>
            <span className="text-base font-semibold cursor-pointer">
              _Pbat
            </span>
          </div>
        </div>
      </div>
    </CustomTooltip>
  );
}

export default SuggestItem;
