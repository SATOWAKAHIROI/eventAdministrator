import api from "../utils/api";

const adminEventService = {
  async getAllEvent() {
    const response = await api.get("/admin/event");
    return response.data;
  },

  async getEventById(id: number) {
    const response = await api.get(`admin/event/${id}`);

    return response.data;
  },

  async createEvent(
    name: string,
    overview: string,
    startDate: string,
    endDate: string,
    userId: number
  ) {
    const response = await api.post(`admin/event/${userId}`, {
      name,
      overview,
      startDate,
      endDate,
    });

    return response.data;
  },

  async editEventById(
    name: string,
    overview: string,
    startDate: string,
    endDate: string,
    id: number
  ) {
    const response = await api.put(`admin/event/${id}`, {
      name,
      overview,
      startDate,
      endDate,
    });

    return response.data;
  },

  async deleteEventById(id: number) {
    const response = await api.delete(`admin/event/${id}`);
    return response.data;
  },
};

export default adminEventService;
