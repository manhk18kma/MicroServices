import axios from 'axios';

export async function existNotificationAPI() {
    // const url = `http://localhost:8080/api/v1/notifications/${idProfile}/exists-not-checked`;
    const url = `http://localhost:8080/api/v1/notifications/96b23f49-b1dc-4071-b0cb-c951b986f437/exists-not-checked`;

    
    const token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5NmIyM2Y0OS1iMWRjLTQwNzEtYjBjYi1jOTUxYjk4NmY0MzciLCJpZEFjY291bnQiOiIwMDQwNjI0Mi00ODUyLTQ5MDctYTkwNS00YWM2ZTAyYmMwMmYiLCJpZENoYXRQcm9maWxlIjoiOTZhNzE4MmEtNjI4Ni00YWQxLTg4MTYtZDgyYjk3NGQyZTkyIiwic2NvcGUiOiJST0xFX1VTRVIiLCJpc3MiOiJLTUEtQUNUVk4iLCJleHAiOjE3MTk3NDEyNDEsImlhdCI6MTcxOTcyMTI0MSwianRpIjoiNDI5YTNiMzItYmUyNS00YmQyLWIxMGQtMjMxMTc2OTA3MDVjIn0.XquzM_2dAZaSYnbZn3LMeCJSZhesbeba9kdNpnrj6YbFTnCBLY151trgcGNrSMabm-PWQl5bx2_g2v0_9ZqIuA'; // Thay thế 'your_token_here' bằng token thực tế của bạn
    
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };
    
    try {
        const response = await axios.get(url, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching notification:', error);
        throw error;
    }
}

export async function getNotificationAPI() {
    // const url = `http://localhost:8080/api/v1/notifications/${idProfile}/exists-not-checked`;
    const url = `http://localhost:8080/api/v1/notifications/96b23f49-b1dc-4071-b0cb-c951b986f437?pageNo=0&pageSize=10`;

    
    const token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5NmIyM2Y0OS1iMWRjLTQwNzEtYjBjYi1jOTUxYjk4NmY0MzciLCJpZEFjY291bnQiOiIwMDQwNjI0Mi00ODUyLTQ5MDctYTkwNS00YWM2ZTAyYmMwMmYiLCJpZENoYXRQcm9maWxlIjoiOTZhNzE4MmEtNjI4Ni00YWQxLTg4MTYtZDgyYjk3NGQyZTkyIiwic2NvcGUiOiJST0xFX1VTRVIiLCJpc3MiOiJLTUEtQUNUVk4iLCJleHAiOjE3MTk3NDEyNDEsImlhdCI6MTcxOTcyMTI0MSwianRpIjoiNDI5YTNiMzItYmUyNS00YmQyLWIxMGQtMjMxMTc2OTA3MDVjIn0.XquzM_2dAZaSYnbZn3LMeCJSZhesbeba9kdNpnrj6YbFTnCBLY151trgcGNrSMabm-PWQl5bx2_g2v0_9ZqIuA'; // Thay thế 'your_token_here' bằng token thực tế của bạn
    
    const config = {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };
    
    try {
        const response = await axios.get(url, config);
        return response.data;
    } catch (error) {
        console.error('Error fetching notification:', error);
        throw error;
    }
}


