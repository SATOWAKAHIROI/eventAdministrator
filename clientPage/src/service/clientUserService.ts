import api from "../utils/api";

const clientUserService = {
  async login(email: string, password: string) {
    const response = await api.post("client/user/login", { email, password });
    const { data } = response;

    if (data.data.token) {
      localStorage.setItem("clientToken", data.data.token);
      localStorage.setItem("clientUser", JSON.stringify(data.data.user));
    }

    return data;
  },

  async signup(name: string, email: string, password: string) {
    const response = await api.post("client/user/signup", {
      name,
      email,
      password,
    });
    const { data } = response;

    return data;
  },

  async getUserById(id: number) {
    const response = await api.get(`client/user/${id}`);
    const { data } = response;

    return data;
  },

  async edit(id: number, name: string, email: string, password: string) {
    const response = await api.put(`client/user/${id}`, {
      name,
      email,
      password,
    });

    const { data } = response;

    return data;
  },

  async delete(id: number) {
    const response = await api.delete(`client/user/${id}`);
    const { data } = response;

    return data;
  },

  logout() {
    localStorage.removeItem("clientToken");
    localStorage.removeItem("clientUser");
  },

  getCurrentUser() {
    const currentUser = localStorage.getItem("clientUser");
    return currentUser ? JSON.parse(currentUser) : null;
  },

  isAuthenticated() {
    return !!localStorage.getItem("clientUser");
  },
};

export default clientUserService;
