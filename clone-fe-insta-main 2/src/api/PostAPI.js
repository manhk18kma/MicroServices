import axios from "axios";

export async function getAllPost({ token, pageNo }) {
  const url = `http://localhost:8080/api/v1/posts?pageNo=${pageNo}&pageSize=10`;

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

export async function likePost({ idPost, token }) {
  const url = `http://localhost:8080/api/v1/likes`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {
    idPost: idPost,
  };

  try {
    const response = await axios.post(url, data, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function unlikePost({ idPost, token }) {
  const url = `http://localhost:8080/api/v1/likes`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    data: {
      idPost: idPost,
    },
  };

  try {
    const response = await axios.delete(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function countLike({ idPost, token }) {
  const url = `http://localhost:8080/api/v1/likes/count-like?idPost=${idPost}`;

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

export async function getAllComment({ idPost, token }) {
  const url = `http://localhost:8080/api/v1/comments?idPost=${idPost}&pageNo=0&pageSize=10`;

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

export async function createNewComment({ content, idPost, token }) {
  const url = `http://localhost:8080/api/v1/comments`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {
    idPost: idPost,
    content: content,
  };

  try {
    const response = await axios.post(url, data, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function createNewPost({ caption, selectedImage, token }) {
  const url = `http://localhost:8085/api/v1/posts`;

  console.log(selectedImage);

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {
    caption: caption,
    base64: selectedImage,
  };

  const response = await axios.post(url, data, config);
  return response.data;
}

export async function createFollow({ idProfileTarget, token, tokenDetail }) {
  console.log(tokenDetail.sub);
  const url = `http://localhost:8080/api/v1/profiles/${tokenDetail.sub}/follows`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {
    idProfileTarget: idProfileTarget,
  };

  try {
    const response = await axios.post(url, data, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function unFollow({ idProfileTarget, token, tokenDetail }) {
  console.log("id sau khi chon unfollow", idProfileTarget);
  const url = `http://localhost:8080/api/v1/profiles/${tokenDetail.sub}/follows`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    data: {
      idProfileTarget: idProfileTarget,
    },
  };

  try {
    const response = await axios.delete(url, config);
    console.log("return sau khi unfollow", response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function unFriend({ idProfileTarget, token, tokenDetail }) {
  const url = `http://localhost:8080/api/v1/profiles/${tokenDetail.sub}/friends`;
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    data: {
      idProfile2: idProfileTarget,
    },
  };

  try {
    const response = await axios.delete(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}

export async function getListImage({ token, idProfile, pageNo, pageSize }) {
  console.log("token: ", token);
  console.log("id profile: ", idProfile);

  const url = `http://localhost:8080/api/v1/posts/profile?pageNo=${pageNo}&pageSize=${pageSize}&idProfile=${idProfile}`;

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

export async function getPostById({ token, idPost }) {
  console.log("id post: ", idPost);

  const url = `http://localhost:8080/api/v1/posts/${idPost}`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.get(url, config);
  return response.data;
}

export async function deletePost({ idPost, token }) {
  const url = `http://localhost:8080/api/v1/posts/${idPost}`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    data: {
      idPost: idPost,
    },
  };

  const response = await axios.delete(url, config);
  return response.data;
}

export async function updatePost({
  token,
  idProfile,
  idPost,
  caption,
  base64OrUrl,
}) {
  const url = `http://localhost:8080/api/v1/posts/${idPost}`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const data = {
    caption: caption,
    base64OrUrl: base64OrUrl,
    idProfile: idProfile,
  };

  const response = await axios.put(url, data, config);
  return response.data;
}
