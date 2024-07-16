import axios from "axios";

export async function existNotificationAPI({token, tokenDetail}) {
  const url = `http://localhost:8080/api/v1/notifications/${tokenDetail.sub}/exists-not-checked`;

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

export async function getNotificationAPI({token, tokenDetail}) {
  const url = `http://localhost:8080/api/v1/notifications/${tokenDetail.sub}?pageNo=0&pageSize=10`;

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
