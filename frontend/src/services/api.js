import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api",
});

// Attach JWT token automatically to every request
api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    config.headers["X-API-KEY"] = "abc123";

    return config;
});

export default api;