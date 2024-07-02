import axios from "axios";

export async function getAllPost({token}) {
  const url = `http://localhost:8080/api/v1/posts?pageNo=0&pageSize=10`;

  
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

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,

    },
  };

  const data = {
    caption: caption,
    base64: selectedImage,
  };

  try {
    const response = await axios.post(url, data, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}



export async function createFollow({ idProfileTarget, token, tokenDetail }) {
  console.log(tokenDetail.sub)
  const url = `http://localhost:8080/api/v1/profiles/${tokenDetail.sub}/follows`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,

    },
  };

  const data = {
    idProfileTarget: idProfileTarget
  };

  try {
    const response = await axios.post(url, data, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}


export async function unFollow({ idProfileTarget, token, tokenDetail }) {
  console.log(tokenDetail.sub)
  const url = `http://localhost:8080/api/v1/profiles/${tokenDetail.sub}/follows`;

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,

    },
    data: {
      idProfileTarget: idProfileTarget
    }
  }

  try {
    const response = await axios.delete(url, config);
    return response.data;
  } catch (error) {
    console.error("Error fetching notification:", error);
  }
}


