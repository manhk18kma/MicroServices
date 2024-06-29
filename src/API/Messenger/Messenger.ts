import ApiResponse from '../../MessagingService/Util/InterfaceCustom'

const API_URL = ''

export const getAllContacts = async ({ idProfile, pageNo, pageSize }: { idProfile: string; pageNo: number; pageSize: number }): Promise<ApiResponse> => {
    const url = `http://localhost:8083/messagingServiceQuery/getAllContacts?idChatProfile=${idProfile}&pageNo=${pageNo}&pageSize=${pageSize}`;
    
    try {
        const token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZjA5NzZjZi1hZDM0LTRjMDEtODJkZS1hZGY4ZGE0YTVmYTIiLCJpZEFjY291bnQiOiI1M2E1NTZlMi04M2U1LTQ3MzgtOWRmNS03OWYwY2I3OGZjZGYiLCJpZENoYXRQcm9maWxlIjoiM2RhZmU3OWYtMzg3Zi00N2M5LTgxYzYtOTJhMzgxMmQxNWY0Iiwic2NvcGUiOiJST0xFX1VTRVIiLCJpc3MiOiJLTUEtQUNUVk4iLCJleHAiOjE3MTg0OTYyNDUsImlhdCI6MTcxODQ3NjI0NSwianRpIjoiMjg3YzRkYTMtNzNlNi00NGZmLTgwMzAtYmFlYjdmZTgwNGExIn0.SB-b-5SYKUOJ8VAjq8YGBJP1uVaEpUYA-POszbau9HHSfC57avN1A9P3FK9-FctgeKtli8N4vSrXhN-KnmE4Sg';         const response = await fetch(url, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        return data as ApiResponse;
    } catch (error) {
        console.error('Error fetching contacts:', error);
        throw error;
    }
}


export const getAllFriends = async ({ idProfile, pageNo, pageSize }: { idProfile: string; pageNo: number; pageSize: number }): Promise<ApiResponse> => {
    const url = `http://localhost:8083/messagingServiceQuery/getAllFriends?idChatProfile=${idProfile}&pageNo=${pageNo}&pageSize=${pageSize}`
    try {
        const response = await fetch(url)
        if (!response.ok) {
            throw new Error('Network response was not ok')
        }
        const data = await response.json()
        return data as ApiResponse
    } catch (error) {
        console.error('Error fetching contacts:', error)
        throw error
    }
}

// export const getAllMessages = async ({ idChat, pageNo, pageSize }: { idChat: string; pageNo: number; pageSize: number }): Promise<ApiResponse> => {
//     const url = `http://localhost:8083/api/v1/messages/${idChat}/messages?pageNo=${pageNo}&pageSize=${pageSize}`
//     try {
//         const response = await fetch(url)
//         if (!response.ok) {
//             throw new Error('Network response was not ok')
//         }
//         const data = await response.json()
//         return data as ApiResponse
//     } catch (error) {
//         console.error('Error fetching contacts:', error)
//         throw error
//     }
// }

export const getAllMessages = async ({ idChat, pageNo, pageSize }: { idChat: string; pageNo: number; pageSize: number }): Promise<ApiResponse> => {
    const url = `http://localhost:8080/api/v1/messages/${idChat}/messages?pageNo=${pageNo}&pageSize=${pageSize}`
    
    try {
        const token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlNzdkNTMwZi0wNTY0LTQ4ZDEtYWZhYi0yZDUzZGQ1MzYyY2QiLCJpZEFjY291bnQiOiI0M2MwNDJhMy1jMTEwLTRlYzQtYWM5OC1mY2Y5ZDFjOWU0ODciLCJpZENoYXRQcm9maWxlIjoiY2IyODFiYTAtNTViNi00YzdhLTlmYzEtOWEwM2I5MGU3N2MxIiwic2NvcGUiOiJST0xFX1VTRVIiLCJpc3MiOiJLTUEtQUNUVk4iLCJleHAiOjE3MTk2NjI5NzcsImlhdCI6MTcxOTY0Mjk3NywianRpIjoiMmVjZmU4ZmQtMWRlZC00OTIyLTkyMDAtYmVhYmViYTFlM2FjIn0.b9MXisatMiw7ZMrV_EDM9fBVQgTBDgpxjDzjtj8xtSa0k570aD7iPqHUkAmm8z7s-le-gOWLwlELefo9ro0FlA'; 
        const response = await fetch(url, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        return data as ApiResponse;
    } catch (error) {
        console.error('Error fetching messages:', error);
        throw error;
    }
}
