import { Link } from "react-router-dom";

function Story({storyId}) {
  return (
    <Link to={`/story/${storyId}`} class="flex flex-col items-center gap-y-1 cursor-pointer">
      <div class="flex h-[70px] w-[70px] items-center justify-center rounded-[50%] bg-gradient-to-r from-[#f9ce34] via-[#e42a82] to-[#8128c3]">
        <div class="flex h-[66px] w-[66px] items-center justify-center bg-white rounded-[50%]">
          <img
            class="h-[62px] w-[62px] rounded-[50%] object-cover"
            src="https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg"
            alt=""
          />
        </div>
      </div>
      <span class="text-[12px]">_Pbat</span>
    </Link>
  );
}

export default Story;
