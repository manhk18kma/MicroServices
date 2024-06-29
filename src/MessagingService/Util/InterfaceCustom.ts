export interface ChatItem {
    idChat: string
    chatName: string
    lastUsed: string
    isChecked: boolean
    urlImageChatRoom: string
    countMessages: number
}

export interface Data {
    size: number
    totalElements: number
    totalPages: number
    number: number
    items: ChatItem[] | UserProfile[] | ChatMessage[]
}

export default interface ApiResponse {
    status: number
    message: string
    data: Data
}
export interface ChatMessage {
    idChatMessage: string
    content: string
    idChatProfileSender: string
    fullNameSender: string
    urlAvtSender: string
    timeStamp: string
}

export interface UserProfile {
    idProfile: string
    idChatProfile: string
    fullName: string
    urlProfilePicture: string
    status: string
}

