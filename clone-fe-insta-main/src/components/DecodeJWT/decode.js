import { jwtDecode } from "jwt-decode";

const DecodeToken = () => {
    if(localStorage.getItem('token') !== null){
        return jwtDecode(localStorage.getItem('token'))
    }
}

export default DecodeToken