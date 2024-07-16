import { Link } from "react-router-dom";

function Search({user}) {
  return (
    <Link to={`/profile/${user.idProfile}`} className="flex items-center gap-x-2 mt-4 hover:bg-slate-100">
      <div className="">
        <img
          className="w-12 h-12 rounded-[50%] object-cover"
          src={user.urlAvt}
        />
      </div>
      <div className="flex flex-col">
        <span className="text-sm font-medium">{user.fullName}</span>
        {/* <div className="flex items-center gap-x-4">
          <span className="text-sm block relative after:block after:content-[''] after:w-[3px] after:h-[3px] after:bg-[#737373] after:rounded-[50%] after:absolute after:right-[-10px] after:top-[49%] after:cursor-default">
            Pbat
          </span>
          <span className="text-sm">100M follower</span>
        </div> */}
      </div>
    </Link>
  );
}

export default Search;
