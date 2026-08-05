import axios from "axios";


const API = axios.create({
    baseURL: import.meta.env.VITE_API_URL
});
console.log(import.meta.env.VITE_API_URL);

API.interceptors.request.use((config) => {

    // Don't send token for login & register
    if (
        config.url === "/authenticate" ||
        config.url === "/register"
    ) {
        return config;
    }

    const token = localStorage.getItem("token");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default API;