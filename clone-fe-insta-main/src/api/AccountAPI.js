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
  })
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
