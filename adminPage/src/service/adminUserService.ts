import api from "../utils/api";

const adminUserService = {
  async login(email: string, password: string) {
    const response = await api.post("admin/user/login", { email, password });
    const { data } = response;

    if (data.data.token) {
      localStorage.setItem("token", data.data.token);
      localStorage.setItem("user", JSON.stringify(data.data.user));
    }

    return data;
  },

  async signup(name: string, email: string, password: string) {
    const response = await api.post("admin/user/signup", {
      name,
      email,
      password,
    });
    const { data } = response;

    return data;
  },

  async getAllUser() {
    const response = await api.get("admin/user");
    const { data } = response;

    return data;
  },

  async getUserById(id: number) {
    const response = await api.get(`admin/user/${id}`);
    const { data } = response;

    return data;
  },

  async edit(id: number, name: string, email: string, password: string) {
    const response = await api.put(`admin/user/${id}`, {
      name,
      email,
      password,
    });
    const { data } = response;

    return data;
  },

  async delete(id: number) {
    const response = await api.delete(`admin/user/${id}`);
    const { data } = response;

    return data;
  },

  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  },

  getCurrentUser() {
    const currentUser = localStorage.getItem("user");
    return currentUser ? JSON.parse(currentUser) : null;
  },

  isAuthenticated() {
    return !!localStorage.getItem("user");
  },
};

export default adminUserService;
