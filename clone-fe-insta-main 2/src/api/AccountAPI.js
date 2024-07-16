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

export async function searchUser({ token, name }) {
  const url = `http://localhost:8080/api/v1/profiles/search?name=${name}&pageNo=0&pageSize=10`;

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

export async function getProfile({ token, idProfile }) {
  console.log("token: ", token);
  console.log("id profile: ", idProfile);

  const url = `http://localhost:8080/api/v1/profiles/${idProfile}`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axios.get(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching profile:", error);
  }
}

export async function getFollower({ token, idProfile }) {
  const url = `http://localhost:8080/api/v1/profiles/${idProfile}/followers?pageNo=0&pageSize=10`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axios.get(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching profile:", error);
  }
}

export async function getFollowing({ token, idProfile }) {
  const url = `http://localhost:8080/api/v1/profiles/${idProfile}/followings?pageNo=0&pageSize=10`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axios.get(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching profile:", error);
  }
}

// export async function generateOtp({ token, idAccount }) {
//   const url = `http://localhost:8080/api/v1/accounts/${idAccount}/otp`;

//   const config = {
//     headers: {
//       Authorization: `Bearer ${token}`,
//     },
//   };

//   try {
//     const response = await axios.get(url, config);
//     return response.data;
//   } catch (error) {
//     console.error("Error fetching profile:", error);
//   }
// }

export async function generateOtpForgotPassword({ email }) {
  const url = `http://localhost:8080/api/v1/accounts/${email}/otp`;

  const response = await axios.get(url);
  return response.data;
}

export async function activation({ otp, email }) {
  const url = `http://localhost:8080/api/v1/accounts/${email}/activation`;
  return await axios.post(url, {
    otp: otp,
  });
}

export async function updateProfile({
  token,
  idProfile,
  fullName,
  gender,
  urlProfilePicture,
  biography,
  dateOfBirth,
}) {
  const url = `http://localhost:8080/api/v1/profiles/${idProfile}`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {
    fullName: fullName,
    gender: gender,
    idProfile: idProfile,
    urlProfilePicture: urlProfilePicture,
    biography: biography,
    dateOfBirth: dateOfBirth,
  };

  const response = await axios.put(url, data, config);
  return response.data;
}

export async function getAllFriend({ token, idChatProfile }) {
  const url = `http://localhost:8080/api/v1/messages/${idChatProfile}/contacts?pageNo=0&pageSize=5`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axios.get(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching profile:", error);
  }
}

export async function changePassword({
  token,
  idAccount,
  newPassword,
  oldPassword,
  otp,
}) {
  const url = `http://localhost:8080/api/v1/accounts/${idAccount}/password`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {
    idAccount: idAccount,
    newPassword: newPassword,
    oldPassword: oldPassword,
    otp: otp,
  };

  const response = await axios.put(url, data, config);
  return response.data;
}

export async function forgotPassword({
  otp,
  email,
  newPassword,
  newPassword2,
}) {
  const url = `http://localhost:8080/api/v1/accounts/${email}/resetPassword`;

  const data = {
    otp: otp,
    newPassword: newPassword,
    newPassword2: newPassword2,
  };

  const response = await axios.put(url, data);
  return response.data;
}

export async function logout({ token }) {
  const url = `http://localhost:8080/api/v1/auth/logout`;

  return await axios.post(url, {
    token: token,
  });
}
