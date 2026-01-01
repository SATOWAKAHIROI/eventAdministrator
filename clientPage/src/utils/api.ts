import axios from "axios";

const API_BASE_URL =
  import.meta.env.VITE_API_URL || "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    const clientToken = localStorage.getItem("clientToken");
    if (clientToken) {
      config.headers.Authorization = `Bearer ${clientToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status == 401) {
      // ログインページにいる場合はリダイレクトしない
      if (window.location.pathname !== "/login") {
        localStorage.removeItem("clientToken");
        localStorage.removeItem("clientUser");
        window.location.href = "/login";
      }
    }
    return Promise.reject(error);
  }
);

export default api;
