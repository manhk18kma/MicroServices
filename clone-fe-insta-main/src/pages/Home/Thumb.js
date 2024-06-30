import { Link } from "react-router-dom";

function Thumb() {
  return (
    <div className="py-4 bg-white w-96 text-black rounded-lg shadow-2xl">
      <div className="flex items-center gap-x-4 px-3">
        <Link to="/profile">
          <img
            className="w-[60px] h-[60px] rounded-[50%] object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          />
        </Link>
        <div className="flex flex-col">
          <Link to="/profile" className="text-[14px] font-medium">The</Link>
          <span className="text-gray-400">_Pbat</span>
        </div>
      </div>
      <div className="flex items-center justify-between py-4 px-10">
        <div className="flex flex-col items-center ">
          <span className="text-sm font-semibold">91</span>
          <span className="text-xs font-normal">Posts</span>
        </div>
        <div className="flex flex-col items-center">
          <span className="text-sm font-semibold">135</span>
          <span className="text-xs font-normal">Follower</span>
        </div>
        <div className="flex flex-col items-center">
          <span className="text-sm font-semibold">43</span>
          <span className="text-xs font-normal">Following</span>
        </div>
      </div>
      <div>
        <div className="grid grid-cols-3 gap-x-1">
          <img
            className="aspect-square object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          />
          <img
            className="aspect-square object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          />
          <img
            className="aspect-square object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
          />
        </div>
      </div>
      <div>
        <div className="pt-4 px-3 grid grid-cols-2 gap-x-2">
          <Link to="/chat" className="flex items-cente justify-center gap-x-2 py-2 bg-[#3baef8] rounded-lg cursor-pointer">
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
