import api from "./axios";

export const updateProfile=(user)=>{
    return api.post("/api/artist/updateProfile",user);
}

export const selectProject=(id)=>{
    return api.get(`/api/artist/showProject/${id}`);
}

export const deleteProject=(id)=>{
    return api.post(`/api/artist/deleteProject/${id}`);
}

export const createOrder=(id)=>{
    return api.post(`api/payment/create-order/${id}`);
}

export const verify=(data)=>{
    return api.post("api/payment/verify",data);
}