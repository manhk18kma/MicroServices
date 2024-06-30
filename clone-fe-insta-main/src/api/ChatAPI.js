import axios from "axios";

export async function getAllMessages({ idChat, pageNo, pageSize }) {
  const url = `http://localhost:8080/api/v1/messages/${idChat}/messages?pageNo=${pageNo}&pageSize=${pageSize}`;
  return await axios.get(url);
}

export async function getAllFriends({ myId, pageNo, pageSize }) {
  const url = `http://localhost:8080/api/v1/messages/${myId}/chats?pageNo=${pageNo}&pageSize=${pageSize}`;
  return await axios.get(url);
}
