import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        "Content-Type": "application/json"
    }
});
api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    const publicRoutes = [
        "/signin/artist",
        "/signin/reviewer",
        "/signup/artist",
        "/signup/reviewer"
    ];

    const isPublicRoute = publicRoutes.some(route =>
        config.url.includes(route)
    );

    if (token && !isPublicRoute) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});
export default api;