import axios from "axios";

export async function createAccount({
  username,
  password,
  email,
  fullName,
  selectedGender,
  dateValue,
}) {
  const url = `http://localhost:8080/api/v1/accounts`;
  console.log({
    username,
    password,
    email,
    fullName,
    selectedGender,
    dateValue,
  });
  return await axios.post(url, {
    dateOfBirth: dateValue,
    email: email,
    fullName: fullName,
    gender: selectedGender,
    password: password,
    username: username,
  });
}

export async function login({ username, password }) {
  const url = `http://localhost:8080/api/v1/auth/login`;
  return await axios.post(url, {
    password: password,
    username: username,
  });
}

export async function checkToken({ token }) {
  const url = `http://localhost:8080/api/v1/auth/token/introspect`;
  return await axios.post(url, {
    token: token,
  });
}

export async function searchUser({ name }) {
  const url = `http://localhost:8080/api/v1/profiles/search?name=${name}&pageNo=0&pageSize=10`;

  const token =
    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5NmIyM2Y0OS1iMWRjLTQwNzEtYjBjYi1jOTUxYjk4NmY0MzciLCJpZEFjY291bnQiOiIwMDQwNjI0Mi00ODUyLTQ5MDctYTkwNS00YWM2ZTAyYmMwMmYiLCJpZENoYXRQcm9maWxlIjoiOTZhNzE4MmEtNjI4Ni00YWQxLTg4MTYtZDgyYjk3NGQyZTkyIiwic2NvcGUiOiJST0xFX1VTRVIiLCJpc3MiOiJLTUEtQUNUVk4iLCJleHAiOjE3MTk3NDEyNDEsImlhdCI6MTcxOTcyMTI0MSwianRpIjoiNDI5YTNiMzItYmUyNS00YmQyLWIxMGQtMjMxMTc2OTA3MDVjIn0.XquzM_2dAZaSYnbZn3LMeCJSZhesbeba9kdNpnrj6YbFTnCBLY151trgcGNrSMabm-PWQl5bx2_g2v0_9ZqIuA"; // Thay thế 'your_token_here' bằng token thực tế của bạn

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axios.get(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}
