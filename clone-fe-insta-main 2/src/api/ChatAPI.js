import axios from "axios";

export async function getAllMessages({ idChat, pageNo, pageSize, token }) {
  const url = `http://localhost:8080/api/v1/messages/${idChat}/messages?pageNo=${pageNo}&pageSize=${pageSize}`;
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axios.get(url, config);
    console.log("get all message: ", response.data);
    return response;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function getAllFriends({ myId, pageNo, pageSize, token }) {
  const url = `http://localhost:8080/api/v1/messages/${myId}/chats?pageNo=${pageNo}&pageSize=${pageSize}`;
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  try {
    const response = await axios.get(url, config);
    console.log("---- ", response.data);
    return response;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function readMessage({ idChatProfile, token, idChat }) {
  const url = `http://localhost:8080/api/v1/messages/${idChatProfile}/chats/${idChat}`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {

  }

  console.log("token", token)
  console.log(`http://localhost:8080/api/v1/messages/${idChatProfile}/chats/${idChat}`)

  const response = await axios.post(url, data, config);
  return response.data;
}
